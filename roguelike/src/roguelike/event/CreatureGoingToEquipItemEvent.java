package roguelike.event;

import roguelike.Tools;
import roguelike.creature.BodyPartTag;
import roguelike.creature.CreatureHumanoid;
import roguelike.item.Item;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 21.06.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class CreatureGoingToEquipItemEvent extends CreatureGoingToUseBodyPartsEvent implements CreatureGoingToEquipItemEventInterface {
    private CreatureHumanoid creature;
    private Item item;

    public CreatureGoingToEquipItemEvent(CreatureHumanoid creature, Item item){
        super(creature.getBody().getBodyPartsListByTag(BodyPartTag.HAUL));
        this.creature = creature;
        this.item = item;
    }

    @Override
    public CreatureHumanoid getCreature() {
        return creature;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void onEndAction() {
        if (!isCanceled()) {
            creature.equip(this);
        }
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_GOING_TO_EQUIP_ITEM);
        return temp;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " going to equip:" + (item == null?"null":item.toString()) +
                " using:"+ Tools.getSeparatedStringList(getUsingBodyParts(), "[", "]", "|");
    }
}
