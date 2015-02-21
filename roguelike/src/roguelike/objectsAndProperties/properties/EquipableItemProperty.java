package roguelike.objectsAndProperties.properties;

import roguelike.creature.BodyPart;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.stats.Stats;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 08.10.13
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class EquipableItemProperty implements EquipableItemPropertyInterface, StatsObjectPropertyInterface {

    private Item owner;
    private List<EquipmentSlotType> slotTypes;
    private Stats stats;
    private List<BodyPart> owningBodyParts;

    public EquipableItemProperty(Item owner, List<EquipmentSlotType> slotTypes, Stats stats){

        this.owner = owner;
        this.slotTypes = new CopyOnWriteArrayList<>(slotTypes);
        this.stats = stats;
        owningBodyParts = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<EquipmentSlotType> getSlotTypes() {
        return slotTypes;
    }

    @Override
    public Stats getStats() {
        return stats;
    }


    @Override
    public List<BodyPart> getOwningBodyParts() {
        return owningBodyParts;
    }

    @Override
    public void onEquip(List<BodyPart> bodyParts) {
        owningBodyParts.clear();
        owningBodyParts.addAll(bodyParts);
    }

    @Override
    public void onUnEquip() {
        owningBodyParts.clear();
    }

    @Override
    public List<WorldObjectPropertyType> getPropertyTypes() {
        List<WorldObjectPropertyType> types = new ArrayList<>();
        types.add(WorldObjectPropertyType.STATS);
        types.add(WorldObjectPropertyType.ITEM_EQUIPMENT);
        return types;
    }

    @Override
    public LocatedObjectWithPropertiesInterface getOwner() {
        return owner;
    }
}
