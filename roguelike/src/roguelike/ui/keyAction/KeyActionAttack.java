package roguelike.ui.keyAction;

import roguelike.ui.GraphicsUI;

/**
 *
 * @author azu
 */
public class KeyActionAttack extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){        
        ui.game.getPlayer().attack(ui.game.getPlayer().getTarget());
    }
}
