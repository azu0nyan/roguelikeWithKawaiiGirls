package roguelike.event;

import roguelike.CONSTANTS;
import roguelike.creature.CreatureHumanoid;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 21.06.13
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class CreatureEquipItemEvent extends CreatureUsedBodyPartsEvent implements CreatureEquipItemEventInterface {

    private CreatureGoingToEquipItemEventInterface goingToEvent;

    public CreatureEquipItemEvent(CreatureGoingToEquipItemEventInterface goingToEvent){
        super(goingToEvent.getCreature(), goingToEvent.getUnBlockedBodyParts(), goingToEvent.getBlockedBodyParts());
        this.goingToEvent = goingToEvent;
    }

    @Override
    public CreatureHumanoid getCreature() {
        return goingToEvent.getCreature();
    }

    @Override
    public Item getItem() {
        return goingToEvent.getItem();
    }

    @Override
    public CreatureGoingToEquipItemEventInterface getGoingToEvent() {
        return goingToEvent;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_EQUIP_ITEM);
        return temp;
    }

    @Override
    public double getActionWeight() {
        return getItem().getWeight() * CONSTANTS.BODY_PARTS_USED_EQUIP_WEIGHT_MULTIPLIER;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " equipped:" + (getItem() == null?"null":getItem().toString());
    }
}
