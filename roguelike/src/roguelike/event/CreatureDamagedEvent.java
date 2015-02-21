package roguelike.event;

import java.util.LinkedList;
import java.util.List;

import roguelike.creature.Creature;
import roguelike.stats.DamageValue;

/**
 *
 * @author nik
 */
public class CreatureDamagedEvent extends Event implements CreatureDamagedEventInterface{
    
    private Creature damagedCreature;
    private DamageValue damageValue;
    
    public CreatureDamagedEvent(Creature damagedCreature, DamageValue value){
        this.damagedCreature = damagedCreature;
        damageValue = value;
    }

    @Override
    public Creature getDamagedCreature() {
        return damagedCreature;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_DAMAGED);
        return temp;
    }

    @Override
    public DamageValue getDamage() {
        return damageValue;
    }

    @Override
    public String getEventDescription() {
        String dmCr = "null";
        if(getDamagedCreature() != null){
            dmCr = getDamagedCreature().getName();
        }
        String dmg = "null";
        if(getDamage() != null){
            dmg = getDamage().toString();
        }
        return dmCr + " takes " + dmg + " damage";
    }
        
}
