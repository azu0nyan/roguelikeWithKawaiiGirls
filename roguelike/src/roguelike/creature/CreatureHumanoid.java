package roguelike.creature;

import java.util.*;

import roguelike.Pair;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.event.*;
import roguelike.item.*;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.ObjectType;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;

import static com.google.common.collect.Lists.newArrayList;

/**
 *
 * @author nik
 */


public class CreatureHumanoid extends Creature implements CreatureHumanoidActionInterface{

    Inventory inventory;
    EquipmentInterface equipment;    

    public CreatureHumanoid(TileCordinatesLinkedToChunk tCord){
        super(tCord);
        //создание слотов для экипировки
        equipment = getBody();
        inventory = new Inventory();
    }

    /**
     * Можно ли надеть item на данное существо
     */
    public boolean canEquip(Item item){
        return equipment.canEquip(item);
    }

    public boolean equip(CreatureGoingToEquipItemEvent goingToEvent){
        /**
         * Надеть item на существо, true если удалось
         */
        if(inventory.hasItem(goingToEvent.getItem()) && equipment.canEquip(goingToEvent.getItem())){
            if(equipment.equip(goingToEvent.getItem())){
                goingToEvent.getItem().setOwner(this);
                inventory.onItemEquiped(goingToEvent.getItem());
                recalculateStats();
                CreatureEquipItemEventInterface event = new CreatureEquipItemEvent(goingToEvent);
                processEvent(event);
                return true;
            }
        }
        return false;
    }

    public boolean unEquip(CreatureGoingToUnEquipItemEventInterface goingToEvent){
        /**
         * Снять вещь 
         */
        if(equipment.unEquip(goingToEvent.getItem())){
            inventory.onItemUnEquiped(goingToEvent.getItem());
            recalculateStats();
            CreatureUnEquipItemEventInterface event = new CreatureUnEquipItemEvent(goingToEvent);
            processEvent(event);
            return true;
        }
        return false;
    }
    
    public boolean pickUpItem(CreatureGoingToPickUpItemsEventInterface goingToEvent){
        /**
         * поднять и пложить в инвентарь предмет item с текущего тайла 
         */
        List<Item> pickUpedItems = new ArrayList<>();
        for(Item item : goingToEvent.getItems()){
            if(item != null && !item.hasOwner() &&
                    inventory.canPickUp(item) && getChunk().getObjectsInTile(getLinkedCordinates()).contains(item)){
                if(inventory.addItem(item)){
                    item.setOwner(this);
                    pickUpedItems.add(item);
                }
            }
        }
        if(pickUpedItems.size() > 0){
            CreaturePickUpItemsEvent event = new CreaturePickUpItemsEvent(goingToEvent, pickUpedItems);
            processEvent(event);
            return true;
        }
        return false;
    }

    /**
     * Поднимает вещь "из ниоткуда"
     * @param item
     * @return
     */
    public boolean pickUpItem(Item item){
        return inventory.addItem(item);
    }

    /**
     * список предметов которые можно поднять в том числе и лут
     */
    public List<Item> getPickUpableItems(){
        List <Item> onTileItems = new ArrayList<Item>();
        for(LocatedObjectWithPropertiesInterface item : getChunk().getObjectsInTile(getLinkedCordinates(), ObjectType.ITEM)){
            if(!((Item)item).hasOwner()){
                onTileItems.add((Item)item);
            }
        }
        return onTileItems;
    }

    public LinkedList<Item> getInInventoryItems(){
        return inventory.getItemsList();
    }

    /**
     * выбросить предмет item из инвентаря на текущий тайл
     */
    public boolean dropItem(CreatureGoingToDropItemEventInterface goingToEvent){
        if(inventory.hasItem(goingToEvent.getItem()) && inventory.removeItem(goingToEvent.getItem())){
            goingToEvent.getItem().setOwner(null);
            CreatureDropItemEventInterface event = new CreatureDropItemEvent(goingToEvent);
            processEvent(event);
            return true;
        }

        return false;
    }

    /**
     * возвращает списо слотов экипировки
     */
    public List<EquipmentSlot> getEquipmentSlots(){
        ArrayList<EquipmentSlot> res = new ArrayList<>();
        for(Pair<BodyPart, EquipmentSlot> slot :  equipment.getEquipmentSlotsList()){
            res.add(slot.second());
        }
        return res;
    }

    @Override
    public boolean isItemEquiped(Item item){
        return equipment.isItemEquiped(item);
    }

    @Override
    public List<Item> getEquipedItemList(){
        if(equipment != null){
            return equipment.getItemList();
        }
        return null;
    }

    public double getItemsWeight(){
        return inventory.getWeight();
    }

    @Override
    public void setBody(Body body){
        super.setBody(body);
        this.equipment = body;
    }

    @Override
    public void actionPickUpItem(Item item) {
        CreatureGoingToPickUpItemsEventInterface event = new CreatureGoingToPickUpItemsEvent(this, newArrayList(item));
        processEvent(event);
    }

    @Override
    public void actionDropItem(Item item) {
       CreatureGoingToDropItemEventInterface event = new CreatureGoingToDropItemEvent(this, item);
       processEvent(event);
    }

    @Override
    public void actionEquipItem(Item item) {
        CreatureGoingToEquipItemEventInterface event = new CreatureGoingToEquipItemEvent(this, item);
        processEvent(event);
    }

    @Override
    public void actionUnEquipItem(Item item) {
        CreatureGoingToUnEquipItemEvent event = new CreatureGoingToUnEquipItemEvent(this, item);
        processEvent(event);
    }
}
