package roguelike.creature;

import roguelike.Tools;
import roguelike.item.EquipmentSlot;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.EquipableItemProperty;
import roguelike.stats.DamageEntry;
import roguelike.stats.DamageValue;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author azu
 */
public class BodyPart {

    private Body owner;
    private EnumSet<BodyPartTag> tags;
    private double size;
    private int hp;
    private int maxHp;
    private double bleeding;//[0,1] min|max 
    private double endurance;//TODO добавить в таблицу
    private double maxEndurance;
    private boolean lockedToAttack;//находится в процессе "замаха"
    private double enduranceRegen;
    private LinkedList <BodyPart> linkedBodyParts;
    private LinkedList <BodyPart> innerBodyParts;
    EquipmentSlot equipmentSlot;
    
    private boolean canAttack;//пинки, укусы e/t/c
    private DamageValue attackDamage;
    String name;       
    
    DamageValue recivedDamage;
    
    public BodyPart(String name){
        this.name = name;
        tags = EnumSet.noneOf(BodyPartTag.class);
        size = 1;
        hp = 10;
        maxHp = 10;
        endurance = 100;
        maxEndurance = 100;
        enduranceRegen = 5;
        lockedToAttack = false;
        bleeding = 0;
        linkedBodyParts = new LinkedList<BodyPart>();
        innerBodyParts = new LinkedList<BodyPart>();
        recivedDamage = new DamageValue();
    }

    public void update(int deltaTime){
        //TODO bleeding
        modifyEndurance(enduranceRegen * deltaTime / 1000);
        
    }

    public void addTag(BodyPartTag tag){
        tags.add(tag);
    }

    public boolean hasTag(BodyPartTag tag){
        return tags.contains(tag);
    }

    public void removeBodyPart(BodyPart bodyPart){
        synchronized(linkedBodyParts){
            linkedBodyParts.remove(bodyPart);
        }
        synchronized(innerBodyParts){
            innerBodyParts.remove(bodyPart);
        }
    }

    public void addInnerBodyPart(BodyPart bodyPart){
        synchronized(innerBodyParts){
            if(!innerBodyParts.contains(bodyPart) && bodyPart != this){
                innerBodyParts.add(bodyPart);
                bodyPart.setOwner(owner);
            }
        }
    }

    public void addLinkedBodyPart(BodyPart bodyPart){
        synchronized(linkedBodyParts){
            if(!linkedBodyParts.contains(bodyPart) && bodyPart != this){
                linkedBodyParts.add(bodyPart);
                bodyPart.setOwner(owner);
            }
        }
    }

    public EnumSet<BodyPartTag> getTagsSet(){
        return tags;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getHp(){
        return hp;
    }

    public void setMaxHp(int maxHp){
        int deltaHp = maxHp - this.maxHp;
        this.maxHp = maxHp;
        if(deltaHp > 0){
            increaseHp(deltaHp);
        }
        
    }

    public void increaseHp(int incHp){
        if(incHp >= 0){
            this.hp = Math.min(maxHp, hp + incHp);
        } else {
            this.hp = Math.max(0, hp + incHp);
        }
    }

    public int getMaxHp(){
        return maxHp;
    }

    /**
     * Изменяет колличетво выносливаости
     * @param modEndurance положительное или отрицательное колличество выносливаости
     * @return итоговое изменение
     */
    public double modifyEndurance(double modEndurance) {
        double oldEndurance = endurance;
        if(modEndurance > 0){
            endurance = Math.min(maxEndurance, endurance + modEndurance);
        } else {
            endurance = Math.max(0, endurance + modEndurance);
        }
        return endurance - oldEndurance;
    }

    public void setMaxEndurance(int endurance){
        maxEndurance = endurance;
    }

    public double getEndurance(){
        return endurance;
    }

    public double getSize(){
        return size;
    }

    public void setSize(double size){
        this.size = size;
    }

    public void setEquipmentSlot(EquipmentSlotType type){
        if(equipmentSlot == null && type != null && type != EquipmentSlotType.NONE){
            addTag(BodyPartTag.EQUIPMENTSLOT);
            equipmentSlot = new EquipmentSlot(type);
        }
    }

    public EquipmentSlot getEquipmentSlot(){
        return equipmentSlot;
    }

    public double getBleeding(){
        return bleeding;
    }

    public void setBleeding(double bleeding){
        this.bleeding = bleeding;
    }

    public void addBleeding(double bleeding){
        this.bleeding += bleeding;
    }

    public LinkedList<BodyPart> getLinkedBodyParts(){
        return linkedBodyParts;
    }

    public LinkedList<BodyPart> getInnerBodyParts(){
        return innerBodyParts;
    }

    public LinkedList<BodyPart> getNearBodyParts(){
        LinkedList list = new LinkedList();
        synchronized(linkedBodyParts){
            list.addAll(linkedBodyParts);
        }
        synchronized(innerBodyParts){
            list.addAll(innerBodyParts);
        }
        return list;
    }

    public BodyPart getRandomNearBodyPart(){
        LinkedList<BodyPart> tempList = getNearBodyParts();
        if(tempList != null && tempList.size() > 0){
            Random r = new Random();
            return tempList.get(r.nextInt(tempList.size()));
        }
        return null;
        
    }

    private void damage(int damage){
        //TODO generateDamageEvents
        if(damage > 0 ){
            hp = Math.max(0, hp - damage);
        } else {
            hp = Math.min(maxHp, hp + (- damage));
        }
    }

    public void damageIdnoreResists(int damage){
        //TODO generateDamageEvents
        if(damage > 0 ){
            hp = Math.max(0, hp - damage);
        } else {
            hp = Math.min(maxHp, hp + (- damage));
        }
    }

    public void damage(DamageEntry damage) {
        BodyPartTag immuneTypeTag = Tools.mapDamageTypeToBodyPartImunneTag(damage.getType());
        BodyPartTag immuneElementTag = Tools.mapElementToBodyPartImmuneTag(damage.getElement());
        //проверка на имунность к данному урону
        if (!isImmune(damage)) {
            //магические и не очень резисты
            int resist = Tools.getResist(owner.getResists(), damage.getElement());
            //получаем уровень защиты у предмета если он есть
            int defence = 0;
            if (equipmentSlot != null && !equipmentSlot.isEmpty()) {
                Tools.getDefense(equipmentSlot.getItem(), damage.getType());
            }
            resist = Math.max(resist, 1);//ln(1) = 0
            defence = Math.max(defence, 1);
            resist = Math.min(resist, 22000);//ln^2(22000) = 99.9//
            defence = Math.min(defence, 22000 / 5);
            int damageValue = (int) ((double) damage.getValue()
                    * ((100.0 - Math.pow(Math.log(resist), 2)) / 100.0)
                    * ((100.0 - Math.pow(Math.log(defence * 5), 2)) / 100.0));
            //dmg * (100 - ln^2(resist) / 100) * (100 - ln^2(defence * 5) / 100);
            damage(damageValue);
            addRecivedDamage(new DamageEntry(damageValue, damage));
        } else {
            // immune..
        }
    }

    public boolean isImmune(DamageEntry damage){
        BodyPartTag immuneTypeTag = Tools.mapDamageTypeToBodyPartImunneTag(damage.getType());
        BodyPartTag immuneElementTag = Tools.mapElementToBodyPartImmuneTag(damage.getElement());
        //проверка на имунность к данному урону
        if(damage == null || hasTag(immuneTypeTag) || hasTag(immuneElementTag) ||
                (Tools.isMagicDamage(damage.getElement()) && hasTag(BodyPartTag.MAGICIMMUNE)) ||
                (Tools.isPhusicalDamage(damage.getElement()) && hasTag(BodyPartTag.PHUSICALIMMUNE))){
            return true;
        }
        return false;
    }

    public DamageValue getRecivedDamage(){
        return recivedDamage;
    }

    private void addRecivedDamage(DamageEntry entry){
        recivedDamage.addDamageEntry(entry);
    }

    public boolean isAlive(){
        if(hp <= 0 || hasTag(BodyPartTag.CUTTED)){
            return false;
        }
        return true;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setOwner(Body body){
        owner = body;
    }

    public Body getOwner(){
        return owner;
    }

    public boolean canAttack(){
        return canAttack;
    }
    
    @Override
    public String toString(){
        String str = getName() + " [" + String.valueOf((getHp())) + "/" + 
                String.valueOf(getMaxHp()) + "]";
        str += Tools.getSeparatedStringList(tags, "[", "]", "|");
        if(maxEndurance > 0){
            str += Tools.getProgressString(endurance, maxEndurance, 6);
        }
        return str;
    }

    public DamageValue getDamage() {
        return attackDamage;
    }

    public double getWithItemWeight() {
        if(equipmentSlot != null && equipmentSlot.getItem() != null){
            int slotsCount = ((EquipableItemProperty)equipmentSlot.getItem().getPropertiesByType(WorldObjectPropertyType.ITEM_EQUIPMENT)).getSlotTypes().size();
            return size + equipmentSlot.getItem().getWeight() / (double)slotsCount;
        }
        return size;//А вы не знали что у нас размер примерно равен весу :D
    }

    public void lockToAttack(){
        lockedToAttack = true;
    }

    public void unlockToAttack(){
        lockedToAttack = false;
    }

    public boolean isLockedToAttack(){
        return lockedToAttack;
    }
    
    boolean isItemEquiped(Item item) {
        return equipmentSlot != null && equipmentSlot.getItem() == item;
    }

    
    
}
