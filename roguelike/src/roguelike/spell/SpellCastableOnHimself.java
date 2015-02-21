package roguelike.spell;

import roguelike.*;
import roguelike.creature.Creature;

/**
 * 
 * @author nik
 */
public abstract class SpellCastableOnHimself extends Spell{
    public abstract void cast(Creature caster);
}
