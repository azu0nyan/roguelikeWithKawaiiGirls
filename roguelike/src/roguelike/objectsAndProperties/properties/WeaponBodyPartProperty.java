package roguelike.objectsAndProperties.properties;

import roguelike.creature.BodyPart;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.stats.DamageValue;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 08.10.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class WeaponBodyPartProperty implements WeaponPropertyInterface {

    private BodyPart owner;

    public WeaponBodyPartProperty(BodyPart owner){
        this.owner = owner;
    }

    @Override
    public DamageValue getDamage() {
        return owner.getDamage();
    }

    @Override
    public double getWeight() {
        return owner.getWithItemWeight();
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean canAttack() {
        return true;//todo dopilit
    }

    @Override
    public List<BodyPart> getOwningBodyParts() {
        return newArrayList(owner);
    }

    @Override
    public List<WorldObjectPropertyType> getPropertyTypes() {
        List<WorldObjectPropertyType> types = new ArrayList<>();
        types.add(WorldObjectPropertyType.WEAPON);
        return types;
    }

    @Override
    public LocatedObjectWithPropertiesInterface getOwner() {
        return owner.getOwner().getOwner();
    }
}
