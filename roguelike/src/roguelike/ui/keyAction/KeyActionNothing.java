
package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionNothing extends KeyAction{

    @Override
    public void doAction(GraphicsUI ui) {
        System.out.println("Doing nothing");
    }
    
}
