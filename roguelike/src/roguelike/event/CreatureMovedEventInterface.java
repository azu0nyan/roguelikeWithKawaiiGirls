package roguelike.event;

import roguelike.TileCordinates;
import roguelike.creature.Creature;
import roguelike.creature.MovementType;

/**
 *
 * @author azu
 */
public interface CreatureMovedEventInterface extends CreatureUsedBodyPartsEventInterface{

    public TileCordinates getStart();

    public TileCordinates getEnd();

    public MovementType getMovementType();

    public Creature getCreature();

    public CreatureGoingToMoveEventInterface getGoingToEvent();

}
