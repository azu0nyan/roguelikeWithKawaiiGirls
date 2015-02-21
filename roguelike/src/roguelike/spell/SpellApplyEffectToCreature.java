package roguelike.spell;

import roguelike.creature.Creature;

/**
 *
 * @author nik
 */
public class SpellApplyEffectToCreature extends SpellCastableOnHimself{
    /**
     * Заклинание при касте добавляющее эффект к существу 
     */
    Effect effect = null;
    public SpellApplyEffectToCreature(int id){
        effect = EffectsFactory.getEffect(id);
    }
    public SpellApplyEffectToCreature(Effect effect){
        this.effect = effect;
    }
    public void setEffect(Effect effect){
        this.effect = effect;
    }
    public Effect getEffect(){
        return effect;
    }
    @Override
    public void cast(Creature caster){
        if(caster != null){
            caster.applyEffect(effect);
        }
    }
}
