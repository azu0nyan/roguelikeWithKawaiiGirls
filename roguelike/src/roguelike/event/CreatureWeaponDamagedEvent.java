package roguelike.event;

import java.util.List;

import roguelike.CONSTANTS;
import roguelike.creature.BodyPart;
import roguelike.creature.Creature;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;
import roguelike.stats.DamageValue;

/**
 *
 * @author nik
 */
public class CreatureWeaponDamagedEvent extends CreatureDamagedEvent implements CreatureWeaponDamagedEventInterface{

    
    private CreatureGoingToCauseWeaponDamageEvent damageEvent;
    
    public CreatureWeaponDamagedEvent(Creature damagedCreature, DamageValue recivedDamage, CreatureGoingToCauseWeaponDamageEvent damageEvent){
        super(damagedCreature, recivedDamage);
        this.damageEvent = damageEvent;
        
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_WEAPON_DAMAGED);
        temp.add(EventType.CREATURE_DAMAGED);
        return temp;
    }

    @Override
    public boolean isActionEvent(){
        return false;
    }

    @Override
    public WeaponPropertyInterface getDamagingWeapon() {
        if(damageEvent != null){
            return damageEvent.getAttackingWeapon();
        }
        return null;
    }

    @Override
    public Creature getAttackingCreature() {
       if(damageEvent != null){
           return damageEvent.getAttackingCreature();
       }
       return null;
    }

    @Override
    public CreatureGoingToCauseWeaponDamageEventInterface getGoingToEvent() {
        return damageEvent;
    }

    @Override
    public String getEventDescription() {
        String attCr = "null";
        if(getAttackingCreature() != null){
            attCr = getAttackingCreature().getName();
        }
        String weapon = " null";
        if(getDamagingWeapon()!= null){
            weapon = getDamagingWeapon().toString();
        }
        return super.getEventDescription() + " from " + attCr + " with " + weapon;
    }

    @Override
    public Creature getCreatureUsedBodyParts() {
        return damageEvent.getAttackingCreature();
    }

    @Override
    public List<BodyPart> getBlockedBodyParts() {
        return damageEvent.getBlockedBodyParts();
    }

    @Override
    public List<BodyPart> getUsedBodyParts() {
        return damageEvent.getUsingBodyParts();
    }

    @Override
    public double getActionWeight() {
        return getDamagingWeapon().getWeight() * CONSTANTS.BODY_PARTS_USED_WEAPON_WEIGHT_MULTIPLIER;
    }
}
