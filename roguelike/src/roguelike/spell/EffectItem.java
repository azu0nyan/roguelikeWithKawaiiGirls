package roguelike.spell;

import roguelike.creature.Creature;
import roguelike.item.Item;

/**
 *
 * @author nik
 */
public class EffectItem extends Effect{
    /** Все заклинания предметов накладывающие эффекты на существо 
     * должны накладывать эффект этого типа
     * Применяет эффект пока предмет надет на существо
     */
    Item item = null;
    public void setOwner(Creature owner, Item item){
        setOwner(owner);
        setItem(item);
    }
    public void setItem(Item item){
        this.item = item;
    }
    @Override
    public void update(){
        if((owner != null) && (item != null) && (owner.isItemEquiped(item))){
            super.update();
        } else {
            //если предмет уже не надет на существо то юзаем 
            disspell();
        }
    }
}
