package roguelike.item;

import java.util.LinkedList;

/**
 *
 * @author azu
 */
public class Inventory {
    
    LinkedList<Item> items;
    double maxWeight = 100;
    double equipedItemsWeight = 0;
    
    public Inventory(){
        items = new LinkedList<Item>();
    }
    public double getWeight(){
        /**
         * вес всех надетых и лежащих в инвентаре вещей
         */
        double weight = 0;
        for (Item item : items) {
            weight += item.getWeight();
        }
        return weight + equipedItemsWeight;
    }
    
    public boolean canPickUp(Item item){
        /**
         * поместьтся ли item в инвентарь
         */
        if(item != null){
            return (item.getWeight() + getWeight()) <= maxWeight;
        } 
        return false;
    }
    
    public boolean addItem(Item item){
        /**
         * добавление вещи в инвентарь, false если у нее слишком большой вес
         */
        if(getWeight() + item.getWeight() <= maxWeight){
            items.add(item);
            return true;
        }
        return false;
    }
    public void addIgnoreWeight(Item item){
        /**
         * добавления вещи в инвентарь игнорируя ее вес
         */
        items.add(item);
    }
    public boolean removeItem(Item item){
        /**
         * удаление вещи из инветаря
         */
        return items.remove(item);
        
    }
    public boolean hasItem(Item item){
        /**
         * есть ли эта вещь в инвентаре
         */
        return items.contains(item);
    }
    public void onItemEquiped(Item item){
        /**
         * вызывается когда вещь надета для перерасчета веса и добавления вещи в
         * инвентарь
         */
        if(item != null){
            equipedItemsWeight += item.getWeight();
            removeItem(item);
        }
    }
    public void onItemUnEquiped(Item item){
        /**
         * вызывается когда вещь снята для перерасчета веса, и добавления вещи 
         * в инвентарь
         */
        if(item != null){
            equipedItemsWeight -= item.getWeight();
            addIgnoreWeight(item);
        }
    }
    public void setEquipedItemsWeight(double weight){
        /**
         * Установить вес экипированных вещей
         */
        equipedItemsWeight = weight;
    }
    public LinkedList<Item> getItemsList(){
        return items;
    }    
}
