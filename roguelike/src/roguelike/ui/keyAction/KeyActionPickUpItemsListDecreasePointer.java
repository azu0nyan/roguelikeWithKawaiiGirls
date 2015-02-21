package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionPickUpItemsListDecreasePointer extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        ui.pickupableItems.decreaseIterator();
    }
    
}
