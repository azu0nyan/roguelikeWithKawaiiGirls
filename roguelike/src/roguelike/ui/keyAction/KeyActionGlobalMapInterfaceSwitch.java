package roguelike.ui.keyAction;

import roguelike.ui.GameInterfaceState;
import roguelike.ui.GraphicsUI;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 14.01.14
 * Time: 9:16
 * To change this template use File | Settings | File Templates.
 */
public class KeyActionGlobalMapInterfaceSwitch extends KeyAction {

    @Override
    public void doAction(GraphicsUI ui) {
        if (ui.interfaceState.equals(GameInterfaceState.GLOBAL_MAP)) {
            ui.interfaceState = GameInterfaceState.GAME;
        } else {
            ui.interfaceState = GameInterfaceState.GLOBAL_MAP;
        }
    }

}

