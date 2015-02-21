package roguelike.event;

import roguelike.TileCordinates;
import roguelike.Tools;
import roguelike.creature.BodyPartTag;
import roguelike.creature.Creature;
import roguelike.creature.MovementType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 07.07.13
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
public class CreatureGoingToMoveEvent extends CreatureGoingToUseBodyPartsEvent implements CreatureGoingToMoveEventInterface {
    TileCordinates start;
    TileCordinates end;
    MovementType movementType;
    Creature creature;

    public CreatureGoingToMoveEvent(Creature creature, TileCordinates start, TileCordinates end, MovementType movementType){
        super(creature.getBody().getBodyPartsListByTag(BodyPartTag.MOVE));
        this.creature = creature;
        this.start = start;
        this.end = end;
        this.movementType = movementType;
    }

    @Override
    public TileCordinates getStart() {
        return start;
    }

    @Override
    public TileCordinates getEnd() {
        return end;
    }

    @Override
    public MovementType getMovementType() {
        return movementType;
    }

    @Override
    public Creature getCreature(){
        return creature;
    }
    @Override
    public void onEndAction(){
        if(!isCanceled()){
            creature.moveTo(this);
        }
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.CREATURE_GOING_TO_MOVE);
        return res;
    }

    @Override
    public String getEventDescription() {
        return "Creature:" + (creature == null?"null":creature.getName()) + " going to move from:" + (start == null?"null":start.toString()) +
                " to:" + (end == null?"null":end.toString()) + " using:"+ Tools.getSeparatedStringList(getUsingBodyParts(), "[", "]", "|");
    }
}
