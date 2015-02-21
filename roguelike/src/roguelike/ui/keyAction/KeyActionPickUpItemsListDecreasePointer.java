package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionPickUpItemsListDecreasePointer extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        ui.pickupableItems.decreaseIterator();
    }
    
}
