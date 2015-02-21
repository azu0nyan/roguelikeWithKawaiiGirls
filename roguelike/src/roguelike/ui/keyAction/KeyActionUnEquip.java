
package roguelike.ui.keyAction;

import roguelike.creature.CreatureHumanoidActionInterface;
import roguelike.item.Item;
import roguelike.ui.GraphicsUI;
import roguelike.ui.InventoryMenuPointerState;

/**
 *
 * @author azu
 */
public class KeyActionUnEquip extends KeyAction {
    @Override
    public void doAction(GraphicsUI ui) {
        if(ui.getInventoryMenu().getCurrentState() == InventoryMenuPointerState.EQUIPMENT){
            Item unEquippedItem = ui.getInventoryMenu().getPointedEquipmentSlot().getItem();
            CreatureHumanoidActionInterface player = ui.game.getPlayer();
            player.actionUnEquipItem((Item)unEquippedItem);
        }
    }
}
