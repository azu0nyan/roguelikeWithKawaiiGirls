package roguelike;

import java.util.LinkedList;
import roguelike.creature.Creature;
import roguelike.creature.EquipmentInterface;
import roguelike.item.EquipmentSlot;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;

/**
 *
 * @author nik
 */
public class Equipment {//implements EquipmentInterface 
/*
    private Creature owner;
    private LinkedList<EquipmentSlot> equipment;

    public Equipment(Creature owner) {
        setOwner(owner);
        equipment = new LinkedList<EquipmentSlot>();
    }
    
    public void setOwner(Creature owner){
        this.owner = owner;
    }
    @Override
    public boolean isEmptySlot(EquipmentSlotType slotType) {
        /**
         * true если есть хотя бы один пустой слот указанного типа
         */
        /*for (EquipmentSlot slot : equipment) {
            if ((slot.getType() == slotType) && (slot.isEmpty())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canEquip(Item item) {
        /**
         * есть ли пустой слот под эту вещь
         */
       /* if (item == null) {
            return false;
        }
        for (EquipmentSlot slot : equipment) {
            if ((isEmptySlot(item.getSlotType())) || ((item.getSlotType() == EquipmentSlotType.TWOHAND)
                    && isEmptySlot(EquipmentSlotType.RIGHTHAND)
                    && isEmptySlot(EquipmentSlotType.LEFTHAND))) {
                return true;
            }
        }
        return false;
    }

    private EquipmentSlot getEmptySlot(EquipmentSlotType slotType) {
        /**
         * получить пустой слот указанного типа
         */
        /*for (EquipmentSlot slot : equipment) {
            if ((slot.getType() == slotType) && (slot.isEmpty())) {
                return slot;
            }
        }
        return null;
    }

    @Override
    public Item getItemInSlot(EquipmentSlotType slotType) {
        /**
         * получить вещь в указанном слоте
         */
        /*for (EquipmentSlot slot : equipment) {
            if ((slot.getType() == slotType) && (!slot.isEmpty())) {
                return slot.getItem();
            }
        }
        return null;
    }

    @Override
    public boolean equip(Item item) {
        /**
         * одеть вещь
         */
        /*if (item == null) {
            return false;
        }
        if (item.getSlotType() != EquipmentSlotType.TWOHAND) {
            EquipmentSlot slot = getEmptySlot(item.getSlotType());
            if (slot != null) {                
                return slot.equip(item, owner);
            }
        } else {
            EquipmentSlot rightHandSlot = getEmptySlot(EquipmentSlotType.RIGHTHAND);
            EquipmentSlot leftHandSlot = getEmptySlot(EquipmentSlotType.LEFTHAND);
            if ((leftHandSlot != null) && (rightHandSlot != null)) {
                boolean left = leftHandSlot.equip(item, owner);
                boolean right = rightHandSlot.equip(item, owner);
                return left || right;
            }
        }
        return false;
    }

    @Override
    public Item unEquip(EquipmentSlotType slotType) {
        //снять вешь из указанного слота
        for (EquipmentSlot slot : equipment) {
            if ((slot.getType() == slotType) && (!slot.isEmpty())) {
                Item item = slot.unEquip(owner);
                if (item.getSlotType() == EquipmentSlotType.TWOHAND) {
                    unEquip(EquipmentSlotType.RIGHTHAND);
                    unEquip(EquipmentSlotType.LEFTHAND);
                }
                return item;
            }
        }
        return null;
    }

    @Override
    public Item unEquip(Item item) {
        //снять вешь 
        if (!isItemEquiped(item)) {
            return null;
        }
        if (item.getSlotType() == EquipmentSlotType.TWOHAND) {
            return unEquip(EquipmentSlotType.RIGHTHAND);
            //Так же уберет оружее и из левой руки
        }
        return unEquip(item.getSlotType());
    }

    public void addSlot(EquipmentSlot slot) {
        /**
         * добавить слот
         */
       /* equipment.add(slot);
    }

    public void addSlot(EquipmentSlotType slotType) {
        /**
         * //создать и добавить слот указанного типа
         */
        /*this.addSlot(new EquipmentSlot(slotType));
    }
    
    @Override
    public int getSlotsCount() {
        return equipment.size();
    }

    @Override
    public boolean isItemEquiped(Item item) {
        /**
         * одета ли вещь
         */
       /* if (item != null) {
            for (EquipmentSlot slot : equipment) {
                if (slot.getItem() == item) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return
     */
    /*@Override
    public LinkedList<EquipmentSlot> getSlotsList() {
        return equipment;
    }

    @Override
    public LinkedList<Item> getItemList() {
        LinkedList<Item> itemsList = new LinkedList<Item>();
        for (EquipmentSlot slot : equipment) {
            if (slot.getItem() != null) {
                itemsList.add(slot.getItem());
            }
        }
        return itemsList;
    }*/
}
