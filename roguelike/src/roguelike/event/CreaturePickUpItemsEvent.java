package roguelike.event;

import roguelike.CONSTANTS;
import roguelike.Tools;
import roguelike.creature.Creature;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 21.06.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class CreaturePickUpItemsEvent extends CreatureUsedBodyPartsEvent implements CreaturePickUpItemsEventInterface {

    CreatureGoingToPickUpItemsEventInterface goingToEvent;
    private List<Item> items;

    public CreaturePickUpItemsEvent(CreatureGoingToPickUpItemsEventInterface goingToEvent, List<Item> items){
        super(goingToEvent.getCreature(), goingToEvent.getUnBlockedBodyParts(), goingToEvent.getUnBlockedBodyParts());
        this.goingToEvent = goingToEvent;
        this.items = items;
    }

    @Override
    public Creature getCreature() {
        return goingToEvent.getCreature();
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public CreatureGoingToPickUpItemsEventInterface getGoingToEvent() {
        return goingToEvent;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_PICKUP_ITEM);
        return temp;
    }

    @Override
    public double getActionWeight() {
        double w = 0;
        for(Item item : getItems()){
            w += item.getWeight();
        }
        return w * CONSTANTS.BODY_PARTS_USED_PICKUP_WEIGHT_MULTIPLIER;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " picked up:" + (getItems() == null?"null": Tools.getSeparatedStringList(getItems(), "[", "]", ","));
    }

}
