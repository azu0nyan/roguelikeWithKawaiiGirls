package roguelike.ui.keyAction;

import roguelike.creature.Creature;
import roguelike.ui.GraphicsUI;

import java.util.LinkedList;

/**
 *
 * @author azu
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