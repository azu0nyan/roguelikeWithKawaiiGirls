package roguelike.spell;

import roguelike.creature.Creature;

/**
 * 
 * @author azu
 */
public abstract class SpellCastableOnHimself extends Spell{
    public abstract void cast(Creature caster);
}
