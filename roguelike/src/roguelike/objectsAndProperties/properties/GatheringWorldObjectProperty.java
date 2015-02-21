package roguelike.objectsAndProperties.properties;

import roguelike.datetime.Date;
import roguelike.datetime.Season;
import roguelike.event.ItemsGatheredEvent;
import roguelike.event.ItemsGatheredEventInterface;
import roguelike.event.listener.SeasonChangedFruitsGrowthListener;
import roguelike.item.Item;
import roguelike.item.loot.LootList;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 01.10.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class GatheringWorldObjectProperty implements GatheringWorldObjectPropertyInterface {

    LocatedObjectWithProperties owner;
    LootList gatheringItems;

    public GatheringWorldObjectProperty(LocatedObjectWithProperties owner, LootList gatheringItems) {
        this.owner = owner;
        this.gatheringItems = gatheringItems.cloneWithEmptyLoot(owner);
        new SeasonChangedFruitsGrowthListener(this, owner.getChunk().getGlobalDate(), Season.SUMMER, Season.WINTER, Date.dayPerMonth * 2);
    }

    @Override
    public boolean canGather() {
        return gatheringItems.getItemsToLoot().size() > 0;
    }

    @Override
    public ItemsGatheredEventInterface gather(List<Item> items) {
        List<Item> res = new ArrayList<>();
        for(Item item : items){
            if(gatheringItems.lootItem(item) != null){
                res.add(item);
            }
        }
        ItemsGatheredEventInterface ev = new ItemsGatheredEvent(this, res);
        owner.getLinkedCordinates().getChunk().processEvent(ev);
        return ev;
    }

    @Override
    public List<Item> getItemsForGathering() {
        return gatheringItems.getItemsToLoot();
    }

    public void generateLoot() {
        gatheringItems.generateLoot(owner.getLinkedCordinates());
    }

    public void removeLoot() {
        gatheringItems.clearLoot();
    }


    @Override
    public List<WorldObjectPropertyType> getPropertyTypes() {
        List<WorldObjectPropertyType> types = new ArrayList<>();
        types.add(WorldObjectPropertyType.GATHERABLE);
        return types;
    }

    @Override
    public LocatedObjectWithProperties getOwner(){
        return owner;
    }
}
