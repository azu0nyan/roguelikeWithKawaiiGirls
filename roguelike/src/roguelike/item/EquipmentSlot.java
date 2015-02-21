package roguelike.item;

import roguelike.creature.BodyPart;
import roguelike.creature.Creature;
import roguelike.objectsAndProperties.properties.EquipableItemPropertyInterface;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.List;

/**
 *
 * @author azu
 */
public class EquipmentSlot {
    
    private EquipmentSlotType type = EquipmentSlotType.BODY;
    private Item equipedItem = null;
    
    public EquipmentSlot(EquipmentSlotType type){
        this.type = type;
    }
    
    public boolean equip(Item item, Creature owner, List<BodyPart> bodyParts){
        if(item != null && owner != null && isEmpty()){
            equipedItem = item;
            List<WorldObjectPropertyInterface> tmp = item.getPropertiesByType(WorldObjectPropertyType.ITEM_EQUIPMENT);
            if(tmp.size() > 0){
                ((EquipableItemPropertyInterface)(tmp.get(0))).onEquip(bodyParts);
                return true;
            }
        }
        return false;
    }
    
    public Item unEquip(){
        if(equipedItem == null){
            return null;
        }
        Item temp = equipedItem;
        equipedItem = null;
        List<WorldObjectPropertyInterface> tmp = temp.getPropertiesByType(WorldObjectPropertyType.ITEM_EQUIPMENT);
        if(tmp.size() > 0){
            ((EquipableItemPropertyInterface)(tmp.get(0))).onUnEquip();
        }
        return temp;
    }

    public Item getItem(){
        return equipedItem;
    }

    public EquipmentSlotType getType(){
        return type;
    }

    @Override
    public String toString(){
        if(equipedItem != null){
        return type.toString()+ " : " + equipedItem.toString();    
        }
        return type.toString() + " : Null";
    }

    public boolean isEmpty(){        
        return equipedItem == null;
    }
    
}
