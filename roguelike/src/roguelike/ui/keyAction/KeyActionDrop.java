package roguelike.ui.keyAction;

import roguelike.creature.CreatureHumanoidActionInterface;
import roguelike.item.Item;
import roguelike.ui.GraphicsUI;
import roguelike.ui.InventoryMenuPointerState;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 02.10.13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class KeyActionDrop extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        if(ui.getInventoryMenu().getCurrentState() == InventoryMenuPointerState.INVENTORY){
            Item item = ui.getInventoryMenu().getPointedInventoryItem();
            CreatureHumanoidActionInterface player = ui.game.getPlayer();
            player.actionDropItem(item);
        }
    }
}
