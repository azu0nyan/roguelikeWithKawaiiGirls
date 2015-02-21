package roguelike.event;

import roguelike.creature.Creature;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;

/**
 *
 * @author nik
 */
public interface CreatureWeaponDamagedEventInterface extends CreatureDamagedEventInterface, CreatureUsedBodyPartsEventInterface{

    public WeaponPropertyInterface getDamagingWeapon();

    public Creature getAttackingCreature();

    public CreatureGoingToCauseWeaponDamageEventInterface getGoingToEvent();
}
