package roguelike.event;

import roguelike.creature.Creature;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;

/**
 *
 * @author azu
 */
public interface CreatureWeaponDamagedEventInterface extends CreatureDamagedEventInterface, CreatureUsedBodyPartsEventInterface{

    public WeaponPropertyInterface getDamagingWeapon();

    public Creature getAttackingCreature();

    public CreatureGoingToCauseWeaponDamageEventInterface getGoingToEvent();
}
