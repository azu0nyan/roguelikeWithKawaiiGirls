package roguelike.objectsAndProperties.properties;

import roguelike.creature.BodyPart;
import roguelike.stats.DamageValue;

import java.util.List;

/**
 *
 * @author azu
 */
public interface WeaponPropertyInterface extends WorldObjectPropertyInterface {

    public DamageValue getDamage();

    public double getWeight();

    public boolean isItem();

    public boolean canAttack();

    public List<BodyPart> getOwningBodyParts();
}
