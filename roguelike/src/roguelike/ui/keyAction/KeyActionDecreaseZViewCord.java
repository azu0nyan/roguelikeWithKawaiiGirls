package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionDecreaseZViewCord extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        ui.decreaseZViewCord();
    }
}