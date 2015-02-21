package roguelike.creature;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import roguelike.Pair;
import roguelike.item.EquipmentSlot;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.EquipableItemPropertyInterface;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;
import roguelike.stats.*;
import roguelike.worldobject.WorldObjectPropertyType;

/**
 * @author nik
 */
public class Body implements EquipmentInterface {//Жуткий класс вышел Т_Т

    private Creature owner;
    private LinkedList<BodyPart> bodyParts;
    private BodyPart mainBodyPart;
    private double blood = 1000;
    private double maxBlood = 1000;
    private double bloodRegeneration = 0.1;
    private EnumMap<BodyPartTag, Double> tagMap;
    private double cutOffConstant = 2;//Размер урона необходимый чтобы отрезать часть тела
    private double piercingDamageDistribution = 0.3;
    private double crushingDamagePartsCount = 4;//колличество частей тела по которым нанесется дробящий урон
    private double crushingDamageToSizeMultipler = 1;//зависимость поврежденной области от урона

    public Body(Creature owner) {
        this.owner = owner;
        bodyParts = new LinkedList<BodyPart>();
        tagMap = new EnumMap<BodyPartTag, Double>(BodyPartTag.class);
        for (BodyPartTag tag : BodyPartTag.values()) {
            tagMap.put(tag, new Double(0));
        }
    }

    public void update(int deltaTime) {
        //System.out.println(String.valueOf(bodyParts.size()));
        synchronized (bodyParts) {
            for (BodyPart bodyPart : bodyParts) {
                //calc bleeding ...
                //TODO кровезависимые органы
                //generateDamageEvents
                bodyPart.update(deltaTime);
            }
        }
    }

    public DamageValue damage(DamageValue damageValue) {
        synchronized (this) {
            //очищаем полученный урон на всякий случай
            for (BodyPart part : bodyParts) {//не нужно же синхронизировать?
                part.getRecivedDamage().clear();
            }
            for (DamageEntry damageEntry : damageValue.getDamages()) {
                switch (damageEntry.getType()) {
                    case PIERCING: {
                        int damage = damageEntry.getValue();
                        BodyPart damagedPart = getRandomBodyPartFromListBySize(getOuterBodyParts());//(!)(list)
                        if (damagedPart != null && !damagedPart.isImmune(damageEntry)) {
                            //спускаемся "в глубину" тела нанося урон
                            while (damagedPart != null && !damagedPart.hasTag(BodyPartTag.PIERCINGIMMUNE)) {
                                damagedPart.damage(new DamageEntry((int) ((double) damage * piercingDamageDistribution),
                                        damageEntry.getType(), damageEntry.getElement()));
                                damage = Math.max(0, damage - (int) ((double) damage * piercingDamageDistribution));
                                //может вернуть null
                                damagedPart = getRandomBodyPartFromListBySize(damagedPart.getInnerBodyParts(), damagedPart.getSize());//(!)(list,size)

                            }
                        }
                        break;
                    }
                    case CUTTING: {
                        BodyPart damagedPart = getRandomBodyPartFromListBySize(getOuterBodyParts());
                        if (damagedPart != null && !damagedPart.isImmune(damageEntry)) {
                            damagedPart.damage(new DamageEntry(damageEntry));
                            //Отрезаем конечность если наносим большой урон
                            if (!damagedPart.isAlive()
                                    && (double) damageEntry.getValue()
                                    >= (double) damagedPart.getMaxHp() * cutOffConstant) {
                                damagedPart.addTag(BodyPartTag.CUTTED);
                                rebuildBodyPartsList();
                            }
                        } else {
                            //IMMUNE
                        }
                        break;

                    }
                    case CRUSHING: {
                        BodyPart damagedPart = getRandomBodyPartFromListBySize(getOuterBodyParts());
                        if (damagedPart != null && !damagedPart.isImmune(damageEntry)) {
                            int damage = damageEntry.getValue();
                            //максимальный суммарный размер частей тела по которым будет нанесен урон
                            double size = crushingDamageToSizeMultipler * (double) damage;
                            //выбираем случайные части тела
                            LinkedList<BodyPart> damagedParts = new LinkedList<BodyPart>();
                            damagedParts.add(damagedPart);
                            for (int i = 0; (i - 1 < crushingDamagePartsCount)
                                    && getSumBodyPartsSizeFromList(damagedParts) <= size; i++) {
                                BodyPart tempPart = damagedPart.getRandomNearBodyPart();
                                if (tempPart != null && !damagedParts.contains(tempPart)) {
                                    damagedParts.add(tempPart);
                                }
                            }
                            double sumSize = getSumBodyPartsSizeFromList(damagedParts);
                            double tempDamage = (double) damageEntry.getValue();
                            for (BodyPart bodyPart : damagedParts) {
                                bodyPart.damage(new DamageEntry((int) (tempDamage * bodyPart.getSize() / sumSize),
                                        damageEntry.getType(), damageEntry.getElement()));
                            }

                        } else {
                            //IMMUNE EVENT
                        }
                        break;
                    }
                    case UNIFORM: {
                        double tempSize = getSumBodyPartsSize();
                        double tempDamage = (double) damageEntry.getValue();
                        for (BodyPart bodyPart : bodyParts) {
                            //TODO что-то сделать с точностью урона
                            bodyPart.damage(new DamageEntry((int) (tempDamage * bodyPart.getSize() / tempSize),
                                    damageEntry.getType(), damageEntry.getElement()));
                        }
                        break;
                    }
                }

            }
            //Собираем урон для создания ивента
            DamageValue recivedDamage = new DamageValue();
            for (BodyPart part : bodyParts) {
                recivedDamage.addDamageValue(part.getRecivedDamage());
                part.getRecivedDamage().clear();
            }
            return recivedDamage;
            //owner.callEvent(new DamageRecivedEvent(recivedDamage, owner))
        }
    }

    public double getSumBodyPartsSize() {
        return getSumBodyPartsSizeFromList(bodyParts);
    }

    public BodyPart getRandomNearBodyPartBySize(BodyPart bodyPart) {
        if (bodyPart != null) {
            return getRandomBodyPartFromListBySize(bodyPart.getNearBodyParts());
        }
        return null;
    }

    public BodyPart getRandomBodyPartFromListBySize(LinkedList<BodyPart> list) {
        return getRandomBodyPartFromListBySize(list,
                getSumBodyPartsSizeFromList(list));
    }

    public BodyPart getRandomBodyPartFromListBySize(LinkedList<BodyPart> list, double size) {
        /**
         * выбирает случайную часть тела из списка или null
         */
        if (list != null || list.size() != 0) {
            synchronized (list) {
                //double size = getSumBodyPartsSizeFromList(list);
                double random = Math.random();//[0,1)
                double tempSize = 0;
                for (BodyPart part : list) {
                    tempSize += part.getSize() / size;
                    if (random < tempSize) {
                        return part;
                    }
                }
            }
        }
        return null;
    }

    public double getSumBodyPartsSizeFromList(LinkedList<BodyPart> list) {
        double size = 0;
        if (list != null) {
            synchronized (list) {
                for (BodyPart bodyPart : list) {
                    if (bodyPart != null) {
                        size += bodyPart.getSize();
                    }
                }
            }
        }
        return size;
    }

    public void rebuildBodyPartsList() {
        bodyParts = new LinkedList<BodyPart>();
        if (mainBodyPart != null) {
            synchronized (bodyParts) {
                rebuildBodyPartsList(mainBodyPart);
            }
            rebuildTagMap();
        }
    }

    private void rebuildBodyPartsList(BodyPart bodyPart) {
        if (!bodyPart.hasTag(BodyPartTag.CUTTED) && !bodyParts.contains(bodyPart)) {
            bodyParts.add(bodyPart);
            bodyPart.setOwner(this);
            for (BodyPart tempBodyPart : bodyPart.getInnerBodyParts()) {
                rebuildBodyPartsList(tempBodyPart);
            }
            for (BodyPart tempBodyPart : bodyPart.getLinkedBodyParts()) {
                rebuildBodyPartsList(tempBodyPart);
            }
        }
    }

    public LinkedList<BodyPart> getOuterBodyParts() {
        LinkedList<BodyPart> tempBodyParts = new LinkedList<BodyPart>();
        if (getMainBodyPart() != null) {
            getOuterBodyParts(getMainBodyPart(), tempBodyParts);
        }
        return tempBodyParts;
    }

    private void getOuterBodyParts(BodyPart part, LinkedList<BodyPart> list) {
        if (part != null && !part.hasTag(BodyPartTag.CUTTED)) {
            list.add(part);
            for (BodyPart tempPart : part.getLinkedBodyParts()) {
                getOuterBodyParts(tempPart, list);
            }
        }
    }

    public LinkedList<BodyPart> getBodyPartsListIgnoreCut() {
        LinkedList<BodyPart> tempBodyParts = new LinkedList<BodyPart>();
        if (getMainBodyPart() != null) {
            getBodyPartsListIgnoreCut(getMainBodyPart(), tempBodyParts);
        }
        return tempBodyParts;
    }

    private void getBodyPartsListIgnoreCut(BodyPart part, LinkedList<BodyPart> list) {
        if (part != null) {
            list.add(part);
            for (BodyPart tempPart : part.getLinkedBodyParts()) {
                getBodyPartsListIgnoreCut(tempPart, list);
            }
            for (BodyPart tempPart : part.getInnerBodyParts()) {
                getBodyPartsListIgnoreCut(tempPart, list);
            }
        }
    }

    public void rebuildTagMap() {
        tagMap = new EnumMap<BodyPartTag, Double>(BodyPartTag.class);
        synchronized (tagMap) {
            for (BodyPartTag tag : BodyPartTag.values()) {
                tagMap.put(tag, new Double(0));
            }
            for (BodyPart bodyPart : bodyParts) {
                //если орган жив и максималльные хп существуют
                if (bodyPart.getMaxHp() != 0 || bodyPart.getHp() != 0) {
                    double increaseValue = (double) bodyPart.getHp() / (double) bodyPart.getMaxHp();
                    //increaseValue from (0,1)
                    for (BodyPartTag tag : bodyPart.getTagsSet()) {
                        tagMap.put(tag, tagMap.get(tag) + increaseValue);
                    }
                }
            }
        }
    }

    public List<BodyPart> getBodyPartsListByTag(BodyPartTag tag) {
        List<BodyPart> temp = new ArrayList<>();
        synchronized (bodyParts) {
            for (BodyPart part : bodyParts) {
                if (part.hasTag(tag)) {
                    temp.add(part);
                }
            }
        }
        return temp;
    }

    public LinkedList<BodyPart> getBodyPartsList() {
        LinkedList<BodyPart> temp = new LinkedList<BodyPart>();
        synchronized (bodyParts) {
            temp.addAll(bodyParts);
        }
        return temp;
    }

    /*public List<WeaponPropertyInterface> getAttackingBodyParts() {
        List<WeaponPropertyInterface> temp = new ArrayList<>();
        synchronized(bodyParts){
            for(BodyPart part : bodyParts){
                if(part.canAttack()){
                    temp.add(part);
                }
            }
        }
        return temp;
    }     */

    public BodyPart getMainBodyPart() {
        return mainBodyPart;
    }

    public void setMainBodyPart(BodyPart part) {

        this.mainBodyPart = part;
        rebuildBodyPartsList();

    }

    public int getBodyPartsCount() {
        return bodyParts.size();
    }

    public double getBlood() {
        return blood;
    }

    public void increaseBlood(double incBlood) {
        if (incBlood >= 0) {
            blood = Math.min(maxBlood, blood + incBlood);
        } else {
            blood = Math.max(0, blood + incBlood);
        }
    }

    public double getMaxBlood() {
        return maxBlood;
    }

    public void setMaxBlood(double newMaxBlood) {
        blood = Math.max(blood, blood + (newMaxBlood - maxBlood));//увеличиваем колличество крови вместе с повышением maxBlood, но не уменьшаем вместе с понижением
        maxBlood = newMaxBlood;
        blood = Math.min(blood, maxBlood);
    }

    public void setBloodRegeneration(double regeneration) {
        bloodRegeneration = regeneration;
    }

    public double getBloodRegeneration() {
        return bloodRegeneration;
    }

    public ItemDefenseStatsInterface getResists() {
        if (owner != null) {
            return owner.getResists();
        }
        return null;
    }

    ////EQUIPMENT 

    @Override
    public Item getItemInSlot(EquipmentSlotType slotType) {
        //Получаем вещь из первого попавшегося слота
        List<Pair<BodyPart, EquipmentSlot>> slotsList = getEquipmentSlotsList();
        for (Pair<BodyPart, EquipmentSlot> slot : slotsList) {
            if (slot.second().getType().equals(slotType)) {
                return slot.second().getItem();
            }
        }
        return null;
    }

    @Override
    public List<Item> getItemList() {
        List<Item> itemsList = new ArrayList<>();
        List<Pair<BodyPart, EquipmentSlot>> slotsList = getEquipmentSlotsList();
        for (Pair<BodyPart, EquipmentSlot> slot : slotsList) {
            if (!slot.second().isEmpty() && !itemsList.contains(slot.second().getItem())) {
                itemsList.add(slot.second().getItem());
            }
        }
        return itemsList;
    }

    @Override
    public int getSlotsCount() {
        return getEquipmentSlotsList().size();
    }

    @Override
    public List<Pair<BodyPart, EquipmentSlot>> getEquipmentSlotsList() {
        //TODO Может нужно хранить этот список а не переделывать его каждый раз
        List<Pair<BodyPart, EquipmentSlot>> equipmentSlotsList = new ArrayList<>();
        synchronized (bodyParts) {
            for (BodyPart part : bodyParts) {
                EquipmentSlot slot = part.getEquipmentSlot();
                if (slot != null) {
                    equipmentSlotsList.add(new Pair<BodyPart, EquipmentSlot>(part, slot));
                }
            }
        }
        return equipmentSlotsList;
    }

    @Override
    public boolean isEmptySlot(EquipmentSlotType slotType) {
        List<Pair<BodyPart, EquipmentSlot>> slotsList = getEquipmentSlotsList();
        for (Pair<BodyPart, EquipmentSlot> slot : slotsList) {
            if (slot.second().getType().equals(slotType) && slot.second().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isItemEquiped(Item item) {
        List<Pair<BodyPart, EquipmentSlot>> slotsList = getEquipmentSlotsList();
        for (Pair<BodyPart, EquipmentSlot> slot : slotsList) {
            if (slot.second().getItem() == item) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unEquip(Item item) {
        //снимаем и с отсуствующих частей тела
        boolean unEquiped = false;
        LinkedList<BodyPart> parts = getBodyPartsListIgnoreCut();
        for (BodyPart part : parts) {
            if (part.getEquipmentSlot() != null && part.getEquipmentSlot().getItem() == item) {
                part.getEquipmentSlot().unEquip();
                unEquiped = true;
            }
        }
        return unEquiped;
    }

    @Override
    public boolean canEquip(Item item) {
        WorldObjectPropertyInterface eqProperty = item.getPropertyByType(WorldObjectPropertyType.ITEM_EQUIPMENT);
        if (eqProperty == null) {
            return false;
        }
        List<EquipmentSlotType> slotTypes = ((EquipableItemPropertyInterface) eqProperty).getSlotTypes();
        List<Pair<BodyPart, EquipmentSlot>> slots = getEquipmentSlotsList();
        synchronized (slotTypes) {
            for (EquipmentSlotType type : slotTypes) {
                EquipmentSlot tempSlot = null;
                for (Pair<BodyPart, EquipmentSlot> slot : slots) {
                    if (slot.second().isEmpty() && slot.second().getType() == type) {
                        tempSlot = slot.second();
                        break;
                    }
                }
                if (tempSlot != null) {
                    slots.remove(tempSlot);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public synchronized boolean equip(Item item) {
        WorldObjectPropertyInterface eqProperty = item.getPropertyByType(WorldObjectPropertyType.ITEM_EQUIPMENT);
        if (eqProperty == null) {
            return false;
        }
        List<EquipmentSlotType> slotTypes = ((EquipableItemPropertyInterface) eqProperty).getSlotTypes();
        List<Pair<BodyPart, EquipmentSlot>> slots = getEquipmentSlotsList();

        List<BodyPart> equipingParts = new ArrayList<>();
        List<EquipmentSlot> equipingSlots = new ArrayList<>();
        for (EquipmentSlotType type : slotTypes) {
            boolean found = false;
            for (Pair<BodyPart, EquipmentSlot> slot : slots) {
                if (slot.second().isEmpty() && slot.second().getType().equals(type)) {
                    equipingSlots.add(slot.second());
                    equipingParts.add(slot.first());
                    found = true;
                    break;
                }
            }
            if(!found){
                return false;
            }

        }
        boolean success = true;
        EquipmentSlot lastSlot = null;
        for(EquipmentSlot slot : equipingSlots){
            if(!slot.equip(item, owner, equipingParts)){
                success = false;
                lastSlot = slot;
                break;
            }
        }
         if(!success){
             for(EquipmentSlot slot : equipingSlots){
                 if(slot != lastSlot){
                     slot.unEquip();
                 } else {
                    break;
                 }
             }
         } else {
             item.setOwner(owner);
         }
        return success;
    }

    @Override
    public List<Item> getItems() {
        List<Item> weapons = new ArrayList<>();
        List<Item> items = getItemList();
        for (Item item : items) {
            if (item.getPropertyByType(WorldObjectPropertyType.WEAPON) != null) {
                weapons.add(item);
            }
        }
        return weapons;
    }

    public Creature getOwner() {
        return owner;
    }
}
