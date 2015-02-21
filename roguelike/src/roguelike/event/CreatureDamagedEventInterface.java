package roguelike.event;

import roguelike.creature.Creature;
import roguelike.stats.DamageValue;

/**
 *
 * @author azu
 */
public interface CreatureDamagedEventInterface extends EventInterface{

    public Creature getDamagedCreature();

    public DamageValue getDamage();

}
