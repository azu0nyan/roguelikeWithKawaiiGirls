package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionInventoryMenuIncreasePointer extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        ui.getInventoryMenu().increasePointer();
    }
    
}
