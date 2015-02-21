package roguelike.item.loot;

import com.sun.istack.internal.Nullable;
import roguelike.DAO.ItemPrototypesLoader;
import roguelike.Pair;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.item.Item;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 30.09.13
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
public class LootList {

    private List<Pair<Double, Integer>> chanceAndItemIds;
    private ItemPrototypesLoader itemPrototypesLoader;
    private List<Item> itemsToLoot;
    private LocatedObjectWithPropertiesInterface owner;

    public LootList(ItemPrototypesLoader itemPrototypesLoader, @Nullable LocatedObjectWithPropertiesInterface owner){
        this.owner = owner;
        chanceAndItemIds = new ArrayList<>();
        this.itemPrototypesLoader = itemPrototypesLoader;
        itemsToLoot = new ArrayList<>();
    }

    public synchronized void addItemToDropList(double chance, int itemId){
        chanceAndItemIds.add(new Pair<Double, Integer>(chance, itemId));
    }

    public synchronized void generateLoot(TileCordinatesLinkedToChunk point){
        itemsToLoot.clear();
        Random r = new Random();
        for(Pair<Double,Integer> chanceAndItemId : chanceAndItemIds){
            if(chanceAndItemId.first() > r.nextDouble()){
                itemsToLoot.add(itemPrototypesLoader.getItemById(chanceAndItemId.second(), point, owner));
            }
        }
    }

    public synchronized void clearLoot(){
        itemsToLoot.clear();
    }

    public synchronized List<Item> getItemsToLoot(){
        return new ArrayList<>(itemsToLoot);
    }

    public synchronized List<Item> lootItems(){
        List<Item> res = itemsToLoot;
        itemsToLoot = new ArrayList<>();
        return res;
    }

    public synchronized Item lootItem(Item item){
        if(itemsToLoot.contains(item)){
            itemsToLoot.remove(item);
            return item;
        }
        return null;
    }

    public synchronized LootList cloneWithEmptyLoot(@Nullable LocatedObjectWithPropertiesInterface owner){
        LootList res = new LootList(itemPrototypesLoader, owner);
        for(Pair<Double, Integer> chanceAndItem : chanceAndItemIds){
            res.addItemToDropList(chanceAndItem.first(), chanceAndItem.second());
        }
        return res;
    }
}
