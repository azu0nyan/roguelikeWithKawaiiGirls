package roguelike.spell;

import roguelike.creature.Creature;

/**
 *
 * @author azu
 */
public abstract class SpellCastableToCreature extends Spell{
    public abstract void cast(Creature caster, Creature target);
    
}
