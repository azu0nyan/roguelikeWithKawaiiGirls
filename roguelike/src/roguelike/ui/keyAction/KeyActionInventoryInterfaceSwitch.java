package roguelike.ui.keyAction;

import roguelike.ui.GameInterfaceState;
import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionInventoryInterfaceSwitch extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        if(ui.interfaceState.equals( GameInterfaceState.INVENTORY)){
            ui.interfaceState = GameInterfaceState.GAME;
        } else {
            ui.interfaceState = GameInterfaceState.INVENTORY;
        }
    }
    
}
