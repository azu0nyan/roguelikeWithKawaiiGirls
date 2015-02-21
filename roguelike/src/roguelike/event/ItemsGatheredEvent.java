package roguelike.event;

import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 04.10.13
 * Time: 3:48
 * To change this template use File | Settings | File Templates.
 */
public class ItemsGatheredEvent extends Event implements ItemsGatheredEventInterface {

    private GatheringWorldObjectPropertyInterface property;
    private List<Item> items;

    public ItemsGatheredEvent(GatheringWorldObjectPropertyInterface property, List<Item> items){

        this.property = property;
        this.items = items;
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public GatheringWorldObjectPropertyInterface getProperty() {
        return property;
    }
}
