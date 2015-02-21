package roguelike.spell;

import roguelike.creature.Creature;
import roguelike.item.Item;


/**
 *
 * @author nik
 */
public class SpellItemEquipment extends SpellCastableOnHimself{
    /**
     * Спелл который применяется при надевании вещи
     */
    private Item item = null;
    public void setItem(Item item){
        this.item = item;
    }
    public Item getItem(){
        return item;
    }
    public void cast(Creature caster, Item item){
        
    }
    @Override
    public void cast(Creature caster) {
        cast(caster, item);
    }
    
    
}
