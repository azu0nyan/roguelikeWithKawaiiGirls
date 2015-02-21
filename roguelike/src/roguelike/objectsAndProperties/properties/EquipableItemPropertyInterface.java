package roguelike.objectsAndProperties.properties;

import roguelike.creature.BodyPart;
import roguelike.item.EquipmentSlotType;
import roguelike.stats.Stats;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 08.10.13
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public interface EquipableItemPropertyInterface extends WorldObjectPropertyInterface {

    public List<EquipmentSlotType> getSlotTypes();

    public List<BodyPart> getOwningBodyParts();

    public void onEquip(List<BodyPart> bodyParts);

    public void onUnEquip();


}
