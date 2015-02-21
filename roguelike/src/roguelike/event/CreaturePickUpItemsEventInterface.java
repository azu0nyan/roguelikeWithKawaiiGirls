package roguelike.event;

import roguelike.creature.Creature;
import roguelike.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 18.06.13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public interface CreaturePickUpItemsEventInterface extends CreatureUsedBodyPartsEventInterface{

    public Creature getCreature();

    public List<Item> getItems();

    public CreatureGoingToPickUpItemsEventInterface getGoingToEvent();
}
