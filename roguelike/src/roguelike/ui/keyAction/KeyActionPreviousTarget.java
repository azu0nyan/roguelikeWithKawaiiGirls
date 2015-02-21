package roguelike.ui.keyAction;

import java.util.LinkedList;
import roguelike.creature.Creature;
import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
 */
public class KeyActionPreviousTarget extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        LinkedList<Creature> enemies = ui.game.getVisibleEnemies(ui.game.getPlayer());
        Creature currentTarget = ui.game.getPlayer().getTarget();
        Creature previous = null;
        for(Creature cr : enemies){
            if(cr == currentTarget){
                if(previous != null){
                    ui.game.getPlayer().setTarget(previous);
                    return;
                } else {
                    ui.game.getPlayer().setTarget(cr);
                    return;
                }
            }     
        }
        if (enemies.size() > 0) {
            ui.game.getPlayer().setTarget(enemies.getLast());
        }
    }
}