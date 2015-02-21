package roguelike.objectsAndProperties.properties;

import java.util.LinkedList;
import java.util.List;

import roguelike.creature.BodyPart;
import roguelike.stats.DamageValue;

/**
 *
 * @author nik
 */
public interface WeaponPropertyInterface extends WorldObjectPropertyInterface {

    public DamageValue getDamage();

    public double getWeight();

    public boolean isItem();

    public boolean canAttack();

    public List<BodyPart> getOwningBodyParts();
}
