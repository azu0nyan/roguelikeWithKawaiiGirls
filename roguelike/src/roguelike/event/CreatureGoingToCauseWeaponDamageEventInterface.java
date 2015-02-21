package roguelike.event;

import roguelike.creature.Creature;
import roguelike.stats.DamageValue;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;

/**
 *
 * @author nik
 */
public interface CreatureGoingToCauseWeaponDamageEventInterface extends CreatureGoingToUseBodyPartsEventInterface{
    
    public Creature getTarget();

    public Creature getAttackingCreature();

    public WeaponPropertyInterface getAttackingWeapon();

    public DamageValue getDamage();
    
}
