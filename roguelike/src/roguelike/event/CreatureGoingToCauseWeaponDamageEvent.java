package roguelike.event;

import roguelike.Tools;
import roguelike.creature.Creature;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;
import roguelike.stats.DamageValue;

import java.util.List;

/**
 *
 * @author azu
 */
public class CreatureGoingToCauseWeaponDamageEvent extends CreatureGoingToUseBodyPartsEvent implements CreatureGoingToCauseWeaponDamageEventInterface{

    private Creature attCr;
    private Creature target;
    private DamageValue damage;
    private WeaponPropertyInterface weapon;

    public CreatureGoingToCauseWeaponDamageEvent(Creature attCr, Creature target, DamageValue damage, WeaponPropertyInterface weapon) {
        super(weapon.getOwningBodyParts());
        this.attCr = attCr;
        this.target = target;
        this.damage = damage;
        this.weapon = weapon;
        canceled = false;
    }

    @Override
    public void onEndAction() {
        if (!isCanceled()) {
            target.damage(this);
        }
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_GOING_TO_CAUSE_WEAPON_DAMAGE);
        return temp;
    }

    @Override
    public boolean isActionEvent(){
        return true;
    }

    @Override
    public Creature getTarget() {
        return target; 
    }

    @Override
    public Creature getAttackingCreature() {
        return attCr;
    }

    @Override
    public WeaponPropertyInterface getAttackingWeapon() {
        return weapon;
    }

    @Override
    public DamageValue getDamage() {
        return damage;
    }

    @Override
    public String getEventDescription() {
        String str;  
        String targ = "null";
        if(getTarget() != null){
            targ = getTarget().getName();
        }
        String dmg = "null";
        if(getDamage() != null){
            dmg = getDamage().toString();
        }
        String attCr = "null";
        if(getAttackingCreature() != null){
            attCr = getAttackingCreature().getName();
        }
        String weapon = " null";
        if(getAttackingWeapon()!= null){
            weapon = getAttackingWeapon().toString();
        }
        str  = "Creature " + attCr + " going to cause  " + dmg 
                + " damage to " + targ + " using " + weapon +
                " body parts:"+ Tools.getSeparatedStringList(getUsingBodyParts(), "[", "]", "|");
        if(canceled){
            str += " and fails";
        }
        return str;
    }

}
