package roguelike.ui.keyAction;

import roguelike.creature.Creature;
import roguelike.ui.GraphicsUI;

import java.util.LinkedList;

/**
 *
 * @author azu
 */
public class KeyActionNextTarget extends KeyAction{
    @Override
    public void doAction(GraphicsUI ui){
        LinkedList<Creature> enemies = ui.game.getVisibleEnemies(ui.game.getPlayer());
        Creature currentTarget = ui.game.getPlayer().getTarget();
        boolean isNext = false;
        for(Creature cr : enemies){
            if(isNext){
                ui.game.getPlayer().setTarget(cr);
                return;
            }
            if(cr == currentTarget){
                isNext = true;
            }            
        }
        if (enemies.size() > 0) {
            ui.game.getPlayer().setTarget(enemies.getFirst());
        }
    }
}
