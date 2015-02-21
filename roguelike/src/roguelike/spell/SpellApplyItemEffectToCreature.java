package roguelike.spell;

import roguelike.creature.Creature;
import roguelike.item.Item;


/**
 *
 * @author nik
 */
public class SpellApplyItemEffectToCreature extends SpellItemEquipment {
    /**
     * Заклинание при касте добавляющее EffectItem к существу 
     */
    private EffectItem effect = null;
    /*public SpellApplyItemEffectToCreature(int id){
        effect = EffectsFactory.getEffect(id);
    }*/
    
    public SpellApplyItemEffectToCreature(EffectItem effect){
        setEffect(effect);
    }
    public SpellApplyItemEffectToCreature(EffectItem effect, Item item){
        setEffect(effect);
        setItem(item);
    }
    @Override
    public void setItem(Item item){
        super.setItem(item);
        if(effect != null){
            effect.setItem(item);
        }
    }
    public void setEffect(EffectItem effect){
        this.effect = effect;
        if(effect != null){
            effect.setItem(getItem());
        }
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
