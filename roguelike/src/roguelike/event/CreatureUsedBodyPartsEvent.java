package roguelike.event;

import roguelike.creature.BodyPart;
import roguelike.creature.Creature;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 15.08.13
 * Time: 3:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class CreatureUsedBodyPartsEvent extends Event implements CreatureUsedBodyPartsEventInterface {

    List<BodyPart> usedBodyParts;
    List<BodyPart> blockedBodyParts;
    Creature creature;

    public CreatureUsedBodyPartsEvent(Creature cr, List<BodyPart> usedBodyParts, List<BodyPart> blockedBodyParts){
        this.creature = cr;
        this.usedBodyParts = usedBodyParts;
        this.blockedBodyParts = blockedBodyParts;
    }


    @Override
    public List<BodyPart> getBlockedBodyParts(){
        return blockedBodyParts;
    }

    @Override
    public Creature getCreatureUsedBodyParts(){
        return creature;
    }

    @Override
    public List<BodyPart> getUsedBodyParts() {
        return  usedBodyParts;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> temp = super.getTypes();
        temp.add(EventType.CREATURE_USED_BODY_PARTS);
        return temp;
    }

    @Override
    public abstract double getActionWeight();
}
