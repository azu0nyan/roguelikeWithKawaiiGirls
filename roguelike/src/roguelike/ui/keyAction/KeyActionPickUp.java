package roguelike.ui.keyAction;

import roguelike.creature.CreatureHumanoidActionInterface;
import roguelike.item.Item;
import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionPickUp extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        Item item = (Item)ui.pickupableItems.getPointedObject(); 
        if(item != null){
            CreatureHumanoidActionInterface player = ui.game.getPlayer();
            player.actionPickUpItem(item);
        }
    }
    
}
