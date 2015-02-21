package roguelike.spell;

import roguelike.*;
import roguelike.creature.Creature;

/**
 *
 * @author nik
 */
public abstract class SpellCastableToCreature extends Spell{
    public abstract void cast(Creature caster, Creature target);
    
}
