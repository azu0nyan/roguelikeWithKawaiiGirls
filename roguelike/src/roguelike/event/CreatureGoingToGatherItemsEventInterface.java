package roguelike.event;

import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 28.08.13
 * Time: 5:26
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureGoingToGatherItemsEventInterface extends CreatureGoingToUseBodyPartsEventInterface, CreatureGoingToPickUpItemsEventInterface {

    public GatheringWorldObjectPropertyInterface getGatheringProperty();

    public List<Item> getGatheringItems();

}
