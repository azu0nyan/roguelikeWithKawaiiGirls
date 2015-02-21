package roguelike.event;

import roguelike.CONSTANTS;
import roguelike.creature.CreatureHumanoid;
import roguelike.item.Item;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 21.06.13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class CreatureUnEquipItemEvent extends CreatureUsedBodyPartsEvent implements CreatureUnEquipItemEventInterface {

    CreatureGoingToUnEquipItemEventInterface goingToEvent;

    public CreatureUnEquipItemEvent(CreatureGoingToUnEquipItemEventInterface goingToEvent){
        super(goingToEvent.getCreature(), goingToEvent.getUnBlockedBodyParts(), goingToEvent.getUnBlockedBodyParts());
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
    public CreatureGoingToUnEquipItemEventInterface getGoingToEvent() {
        return goingToEvent;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_UN_EQUIP_ITEM);
        return temp;
    }

    @Override
    public double getActionWeight() {
        return getItem().getWeight() * CONSTANTS.BODY_PARTS_USED_UNEQUIP_WEIGHT_MULTIPLIER;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " un equipped:" + (getItem() == null?"null":getItem().toString());
    }
}
