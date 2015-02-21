package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionMoveRight extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        ui.game.movePlayerByDirection(2);
    }
}