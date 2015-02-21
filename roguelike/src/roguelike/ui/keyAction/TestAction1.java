package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class TestAction1 extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){        
        System.out.print(ui.game.getTileAt(ui.game.getPlayer().getCordinates()));
    }
}