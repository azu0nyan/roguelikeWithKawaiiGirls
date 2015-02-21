package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionPickUpItemsListIncreasePointer extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        ui.pickupableItems.increaseIterator();
    }
    
}
