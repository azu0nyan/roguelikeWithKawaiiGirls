package roguelike.objectsAndProperties.properties;

import org.jetbrains.annotations.Nullable;
import roguelike.creature.BodyPart;
import roguelike.item.Item;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.stats.DamageValue;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 08.10.13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class WeaponItemProperty implements WeaponPropertyInterface {

    private Item owner;     //TODO -> item
    private DamageValue damage;

    public WeaponItemProperty(Item owner, DamageValue damage){

        this.owner = owner;
    }

    @Override
    public DamageValue getDamage() {
        return damage;
    }

    @Override
    public double getWeight() {
        return owner.getWeight();
    }

    @Override
    public boolean isItem() {
        return true;
    }

    @Override
    public boolean canAttack() {
        return true;//TODO допилить
    }

    @Override
    public @Nullable List<BodyPart> getOwningBodyParts() {
        List<WorldObjectPropertyInterface> tmp =  owner.getPropertiesByType(WorldObjectPropertyType.ITEM_EQUIPMENT);
        if(tmp.size() > 0){
            return ((EquipableItemPropertyInterface)tmp.get(0)).getOwningBodyParts();
        }
        return  null;
    }

    @Override
    public List<WorldObjectPropertyType> getPropertyTypes() {
        List<WorldObjectPropertyType> types = new ArrayList<>();
        types.add(WorldObjectPropertyType.WEAPON);
        return types;
    }

    @Override
    public LocatedObjectWithPropertiesInterface getOwner() {
        return owner;
    }
}
