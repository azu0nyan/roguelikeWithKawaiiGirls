package roguelike.event;

import roguelike.Tools;
import roguelike.creature.BodyPartTag;
import roguelike.creature.Creature;
import roguelike.creature.CreatureHumanoid;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 21.06.13
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class CreatureGoingToPickUpItemsEvent extends CreatureGoingToUseBodyPartsEvent implements CreatureGoingToPickUpItemsEventInterface {

    private CreatureHumanoid creature;
    private List<Item> items;

    public CreatureGoingToPickUpItemsEvent(CreatureHumanoid creature, List<Item> items){
        super(creature.getBody().getBodyPartsListByTag(BodyPartTag.HAUL));
        this.creature = creature;
        this.items = items;
    }

    @Override
    public Creature getCreature() {
        return creature;
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public void onEndAction() {
        if (!isCanceled()) {
            creature.pickUpItem(this);
        }
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_GOING_TO_PICKUP_ITEM);
        return temp;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " going to pick up:" + (getItems() == null?"null": Tools.getSeparatedStringList(getItems(), "[", "]", ","));
    }
}
