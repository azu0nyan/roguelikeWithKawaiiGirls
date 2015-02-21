package roguelike.objectsAndProperties.properties;

import roguelike.event.ItemsGatheredEventInterface;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 26.08.13
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public interface GatheringWorldObjectPropertyInterface extends WorldObjectPropertyInterface {

    public boolean canGather();

    public ItemsGatheredEventInterface gather(List<Item> items);

    public List<Item> getItemsForGathering();

}
