package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;
import roguelike.ui.InventoryMenuPointerState;

/**
 *
 * @author azu
 */
public class KeyActionInventoryMenuLeftSwitch extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        if(ui.getInventoryMenu().getCurrentState() == InventoryMenuPointerState.INVENTORY){
            ui.getInventoryMenu().switchPointer();
        }
    }
    
}
