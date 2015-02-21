package roguelike.ui.keyAction;

import roguelike.creature.CreatureHumanoidActionInterface;
import roguelike.item.Item;
import roguelike.item.Item;
import roguelike.ui.GraphicsUI;
import roguelike.ui.InventoryMenuPointerState;

/**
 *
 * @author nik
 */
public class KeyActionEquip extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        if(ui.getInventoryMenu().getCurrentState() == InventoryMenuPointerState.INVENTORY){
            Item equipmentItem = ui.getInventoryMenu().getPointedInventoryItem(); 
            if(Item.class.isInstance(equipmentItem)){
                CreatureHumanoidActionInterface player = ui.game.getPlayer();
                player.actionEquipItem((Item)equipmentItem);
            }
        }
    }
    
}
