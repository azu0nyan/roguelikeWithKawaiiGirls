package roguelike.event;

import roguelike.TileCordinates;
import roguelike.creature.Creature;
import roguelike.creature.MovementType;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 07.07.13
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureGoingToMoveEventInterface extends CreatureGoingToUseBodyPartsEventInterface {

    public TileCordinates getStart();

    public TileCordinates getEnd();

    public MovementType getMovementType();

    public Creature getCreature();
}
