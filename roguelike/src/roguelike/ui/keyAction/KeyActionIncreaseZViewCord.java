
package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionIncreaseZViewCord extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        ui.increaseZViewCord();
    }
}