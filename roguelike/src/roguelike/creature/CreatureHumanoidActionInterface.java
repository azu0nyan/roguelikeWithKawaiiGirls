package roguelike.creature;

import roguelike.item.Item;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 18.06.13
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureHumanoidActionInterface extends CreatureActionInterface {

    public void actionPickUpItem(Item item);

    public void actionDropItem(Item item);

    public void actionEquipItem(Item item);

    public void actionUnEquipItem(Item item);

}
