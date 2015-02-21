package roguelike.event;

import roguelike.creature.CreatureHumanoid;
import roguelike.item.Item;
import roguelike.item.Item;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 21.06.13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureUnEquipItemEventInterface extends  CreatureUsedBodyPartsEventInterface{

    public CreatureHumanoid getCreature();

    public Item getItem();

    public CreatureGoingToUnEquipItemEventInterface getGoingToEvent();
}
