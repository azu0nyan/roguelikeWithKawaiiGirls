package roguelike.event.listener.conditions;

import roguelike.creature.Creature;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 16.06.13
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public class CreatureAliveCondition extends Condition{

    Creature creature ;

    public CreatureAliveCondition(Creature c){
        creature = c;
    }

    @Override
    public boolean correct() {
        return !creature.isDead();
    }
}
