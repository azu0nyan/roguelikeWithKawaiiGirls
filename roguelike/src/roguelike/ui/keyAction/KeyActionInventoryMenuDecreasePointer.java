package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionInventoryMenuDecreasePointer extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        ui.getInventoryMenu().decreasePointer();
    }
    
}