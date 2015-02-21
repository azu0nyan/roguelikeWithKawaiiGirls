package roguelike.ui.keyAction;

import roguelike.ui.GameInterfaceState;
import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionBodyInfoSwitch extends KeyAction{
     @Override
    public void doAction(GraphicsUI ui) {
        if(ui.interfaceState == GameInterfaceState.BODY){
            ui.interfaceState = GameInterfaceState.GAME;
        } else if(ui.interfaceState == GameInterfaceState.GAME){
            ui.interfaceState = GameInterfaceState.BODY;
        }
    }
}
