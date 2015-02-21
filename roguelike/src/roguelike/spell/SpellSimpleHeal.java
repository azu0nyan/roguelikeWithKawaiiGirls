package roguelike.spell;

import roguelike.creature.Creature;

/**
 *
 * @author azu
 */
public class SpellSimpleHeal extends SpellCastableOnHimself{
    int healValue = 10;
    public SpellSimpleHeal(int healValue){
        this.healValue = healValue;
    }
    @Override
    public void cast(Creature caster){
        if(caster != null){
            caster.applyHealing(healValue);
        }
    }
    
}
