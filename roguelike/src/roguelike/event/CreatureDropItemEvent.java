package roguelike.event;

import roguelike.CONSTANTS;
import roguelike.creature.Creature;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 21.06.13
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class CreatureDropItemEvent extends CreatureUsedBodyPartsEvent implements CreatureDropItemEventInterface {

    CreatureGoingToDropItemEventInterface goingToEvent;
    private Creature cr;
    private Item item;

    public CreatureDropItemEvent(CreatureGoingToDropItemEventInterface goingToEvent){
        super(goingToEvent.getCreature(), goingToEvent.getUnBlockedBodyParts(), goingToEvent.getBlockedBodyParts());
        this.goingToEvent = goingToEvent;
        cr = goingToEvent.getCreature();
        item = goingToEvent.getItem();
    }

    public CreatureDropItemEvent(CreatureGoingToUseBodyPartsEventInterface bodyEvent, Creature cr, Item item){
        super(cr, bodyEvent.getUnBlockedBodyParts(), bodyEvent.getBlockedBodyParts());
        this.cr = cr;
        this.item = item;
    }


    @Override
    public Creature getCreature() {
        return creature;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public CreatureGoingToDropItemEventInterface getGoingToEvent() {
        return goingToEvent;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_DROP_ITEM);
        return temp;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " dropped:" + (getItem() == null?"null":getItem().toString());
    }

    @Override
    public double getActionWeight() {
        return getItem().getWeight() * CONSTANTS.BODY_PARTS_USED_DROP_WEIGHT_MULTIPLIER;
    }
}
