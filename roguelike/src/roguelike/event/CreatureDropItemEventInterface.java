package roguelike.event;

import roguelike.creature.Creature;
import roguelike.item.Item;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 21.06.13
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureDropItemEventInterface extends CreatureUsedBodyPartsEventInterface {

    public Creature getCreature();

    public Item getItem();

    public CreatureGoingToDropItemEventInterface getGoingToEvent();
}
