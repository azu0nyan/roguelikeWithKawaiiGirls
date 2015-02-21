package roguelike.event;

import roguelike.creature.Creature;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;
import roguelike.stats.DamageValue;

/**
 *
 * @author azu
 */
public interface CreatureGoingToCauseWeaponDamageEventInterface extends CreatureGoingToUseBodyPartsEventInterface{
    
    public Creature getTarget();

    public Creature getAttackingCreature();

    public WeaponPropertyInterface getAttackingWeapon();

    public DamageValue getDamage();
    
}
