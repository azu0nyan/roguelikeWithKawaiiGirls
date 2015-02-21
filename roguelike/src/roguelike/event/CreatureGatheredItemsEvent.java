package roguelike.event;

import roguelike.CONSTANTS;
import roguelike.Tools;
import roguelike.creature.Creature;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 04.10.13
 * Time: 4:32
 * To change this template use File | Settings | File Templates.
 */
public class CreatureGatheredItemsEvent extends CreatureUsedBodyPartsEvent implements CreatureGatheredItemsEventInterface {

    private CreatureGoingToGatherItemsEventInterface goingToEvent;
    private ItemsGatheredEventInterface gatheredEvent;

    public CreatureGatheredItemsEvent(CreatureGoingToGatherItemsEventInterface goingToEvent, ItemsGatheredEventInterface gatheredEvent){
        super(goingToEvent.getCreature(), goingToEvent.getUsingBodyParts(), goingToEvent.getBlockedBodyParts());//TODO using?

        this.goingToEvent = goingToEvent;
        this.gatheredEvent = gatheredEvent;
    }
    @Override
    public Creature getCreature() {
        return goingToEvent.getCreature();
    }

    @Override
    public List<Item> getItems() {
        return gatheredEvent.getItems();
    }

    @Override
    public CreatureGoingToGatherItemsEventInterface getGoingToEvent() {
        return goingToEvent;
    }

    @Override
    public ItemsGatheredEventInterface getGatheredEvent() {
        return gatheredEvent;
    }

    @Override
    public GatheringWorldObjectPropertyInterface getProperty() {
        return goingToEvent.getGatheringProperty();
    }

    @Override
    public double getActionWeight() {
        return Item.getSumWeight(getItems()) * CONSTANTS.BODY_PARTS_USED_GATHER_ITEMS_WEIGHT_MULTIPLIER;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_GATHERED_ITEMS);
        return temp;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (getCreature() == null?"null":getCreature().getName()) + " gathered items:" + Tools.getSeparatedStringList(getItems(), "[", "]", "|")+ " from:" +
                (getProperty()== null?"null":getProperty().getOwner());
    }
}
