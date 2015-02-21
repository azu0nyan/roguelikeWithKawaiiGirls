package roguelike.creature;

import java.util.LinkedList;
import java.util.List;

import roguelike.Pair;
import roguelike.item.EquipmentSlot;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;

/**
 *
 * @author nik
 */
public interface EquipmentInterface {

    boolean canEquip(Item item);

    boolean equip(Item item);

    Item getItemInSlot(EquipmentSlotType slotType);

    List<Item> getItemList();

    int getSlotsCount();
   
    List<Pair<BodyPart,EquipmentSlot>> getEquipmentSlotsList();

    boolean isEmptySlot(EquipmentSlotType slotType);

    boolean isItemEquiped(Item item);

    boolean unEquip(Item item);

    public List<Item> getItems();
    
}
