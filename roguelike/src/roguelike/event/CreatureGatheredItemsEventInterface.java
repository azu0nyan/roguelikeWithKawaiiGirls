package roguelike.event;

import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 28.08.13
 * Time: 5:58
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureGatheredItemsEventInterface extends CreatureUsedBodyPartsEventInterface, CreaturePickUpItemsEventInterface {

    public CreatureGoingToGatherItemsEventInterface getGoingToEvent();

    public ItemsGatheredEventInterface getGatheredEvent();

    public GatheringWorldObjectPropertyInterface getProperty();
}
