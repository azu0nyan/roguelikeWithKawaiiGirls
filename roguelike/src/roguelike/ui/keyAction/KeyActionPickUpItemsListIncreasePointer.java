package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionPickUpItemsListIncreasePointer extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        ui.pickupableItems.increaseIterator();
    }
    
}
