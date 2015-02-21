package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionExit extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        System.out.println("Exit action..");
        System.exit(0);
    }
}
