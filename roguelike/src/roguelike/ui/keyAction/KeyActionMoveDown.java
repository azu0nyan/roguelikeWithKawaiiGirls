package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionMoveDown extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        ui.game.movePlayerByDirection(4);
    }
}
