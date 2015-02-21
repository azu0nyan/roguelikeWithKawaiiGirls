package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionDecreaseZViewCord extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        ui.decreaseZViewCord();
    }
}