package roguelike.creature;

import roguelike.Pair;
import roguelike.item.EquipmentSlot;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;

import java.util.List;

/**
 *
 * @author azu
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
