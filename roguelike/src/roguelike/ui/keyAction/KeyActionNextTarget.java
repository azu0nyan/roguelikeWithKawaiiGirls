package roguelike.ui.keyAction;

import java.util.LinkedList;
import roguelike.creature.Creature;
import roguelike.ui.GraphicsUI;

/**
 *
 * @author nik
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
