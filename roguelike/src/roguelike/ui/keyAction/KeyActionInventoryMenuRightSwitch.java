package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;
import roguelike.ui.InventoryMenuPointerState;

/**
 *
 * @author nik
 */
public class KeyActionInventoryMenuRightSwitch extends KeyAction{
    
    @Override
    public void doAction(GraphicsUI ui) {
        if(ui.getInventoryMenu().getCurrentState() == InventoryMenuPointerState.EQUIPMENT){
            ui.getInventoryMenu().switchPointer();
        }
    }
    
}