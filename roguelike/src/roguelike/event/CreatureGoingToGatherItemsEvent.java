package roguelike.event;

import roguelike.Tools;
import roguelike.creature.BodyPartTag;
import roguelike.creature.Creature;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 28.08.13
 * Time: 5:40
 * To change this template use File | Settings | File Templates.
 */
public class CreatureGoingToGatherItemsEvent extends CreatureGoingToUseBodyPartsEvent implements CreatureGoingToGatherItemsEventInterface{

    GatheringWorldObjectPropertyInterface property;
    private List<Item> items;
    Creature creature;

    public CreatureGoingToGatherItemsEvent(Creature creature, GatheringWorldObjectPropertyInterface property, List<Item> items) {
        super(creature.getBody().getBodyPartsListByTag(BodyPartTag.LABOR));
        this.creature = creature;
        this.property = property;
        this.items = items;
    }

    @Override
    public Creature getCreature() {
        return creature;
    }

    @Override
    public List<Item> getItems() {
        return getGatheringItems();
        //TODO переделать item ивент на items
    }


    @Override
    public GatheringWorldObjectPropertyInterface getGatheringProperty() {
        return property;
    }

    @Override
    public List<Item> getGatheringItems() {
        return items;
    }

    @Override
    public void onEndAction() {
        if (!isCanceled()) {
            creature.gatherItems(this);
        }
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_GOING_TO_GATHER_ITEMS);
        return temp;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (getCreature() == null?"null":getCreature().getName()) + " going to gather items:" + Tools.getSeparatedStringList(getGatheringItems(), "[", "]", "|")+ " from:" +
                (getGatheringProperty()== null?"null":getGatheringProperty().getOwner());
    }

}
