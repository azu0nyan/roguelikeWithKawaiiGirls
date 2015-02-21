package roguelike.event;

import roguelike.CONSTANTS;
import roguelike.TileCordinates;
import roguelike.creature.Creature;
import roguelike.creature.MovementType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 18.06.13
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class CreatureMovedEvent extends CreatureUsedBodyPartsEvent implements CreatureMovedEventInterface{

    CreatureGoingToMoveEventInterface goingToEvent;

    public CreatureMovedEvent(CreatureGoingToMoveEventInterface goingToEvent){
        super(goingToEvent.getCreature(), goingToEvent.getUnBlockedBodyParts(), goingToEvent.getBlockedBodyParts());
        this.goingToEvent = goingToEvent;
    }

    @Override
    public TileCordinates getStart() {
        return goingToEvent.getStart();
    }

    @Override
    public TileCordinates getEnd() {
        return goingToEvent.getEnd();
    }

    @Override
    public MovementType getMovementType() {
        return goingToEvent.getMovementType();
    }

    @Override
    public Creature getCreature(){
        return goingToEvent.getCreature();
    }

    @Override
    public CreatureGoingToMoveEventInterface getGoingToEvent() {
        return goingToEvent;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.CREATURE_MOVED);
        return res;
    }

    @Override
    public double getActionWeight() {
        return getCreature().getBody().getSumBodyPartsSize() * CONSTANTS.BODY_PARTS_USED_WALK_MULTIPLIER; //TODO В зависимости от типа перемещения
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " moved from:" + (getStart() == null?"null":getEnd().toString()) + " to:" + (getEnd() == null?"null":getEnd().toString());
    }
}
