package roguelike.creature;

import roguelike.TileCordinates;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 18.06.13
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureActionInterface {

    public void actionMove(TileCordinates cord, MovementType type);

    public void actionAttack(Creature creature);

    public void actionGather(GatheringWorldObjectPropertyInterface worldObject, List<Item> items);

}
