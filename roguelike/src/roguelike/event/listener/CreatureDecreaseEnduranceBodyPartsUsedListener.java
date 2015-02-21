package roguelike.event.listener;

import java.util.List;

import roguelike.creature.BodyPart;
import roguelike.creature.Creature;
import roguelike.event.CreatureUsedBodyPartsEventInterface;
import roguelike.event.EventInterface;
import roguelike.event.EventType;
import roguelike.event.listener.conditions.CreatureAliveCondition;

/**
 * Ловим событие о действии части тела и уменьшаем запас сил у нее
 * @author nik
 */
public class CreatureDecreaseEnduranceBodyPartsUsedListener extends ConditionLocatedEventListener{

    Creature owner;


    public CreatureDecreaseEnduranceBodyPartsUsedListener(Creature owner){
        super(EventType.CREATURE_USED_BODY_PARTS, owner.getLinkedCordinates(), 1, new CreatureAliveCondition(owner));
        this.owner = owner;
    }


    @Override
    public String processEventWithoutConditions(EventInterface event){
        try{
            CreatureUsedBodyPartsEventInterface tempEvent = (CreatureUsedBodyPartsEventInterface)event;
            if(tempEvent.getCreatureUsedBodyParts() != owner){
                return "";
            }
            /*WeaponPropertyInterface weapon = tempEvent.getDamagingWeapon();
            if(weapon == null || weapon.getOwningBodyParts() == null){
                return;
            }       */
            double endToDecrease= tempEvent.getActionWeight();// owner.calcEnduranceDecreace(weapon);
            double enduranceDecreased = 0;
            List<BodyPart> parts = tempEvent.getUsedBodyParts();//weapon.getOwningBodyParts();
            for(BodyPart part : parts){
                enduranceDecreased -= part.modifyEndurance(-(endToDecrease / parts.size()));//in for size != 0 always
            }

            return toString() + " decreased:" + enduranceDecreased;//TODO ивент на всю эту фигню
        } catch (Exception ignore){
            return "Exception in CreatureDecreaseEnduranceBodyPartsUsedListener with owner " + owner.toString() + " " + ignore.getMessage();
        }
    }

    @Override
    public String toString(){
        return "CreatureDecreaseEnduranceBodyPartsUsedListener owner:" + owner.toString();
    }

}
