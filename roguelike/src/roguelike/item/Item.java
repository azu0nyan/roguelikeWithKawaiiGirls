package roguelike.item;

import com.sun.istack.internal.Nullable;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.TileCordinatesLinkedToChunkBindListener;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.ObjectType;
import roguelike.objectsAndProperties.properties.MaterialsAndPartsListPropertyInterface;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.List;

/**
 *
 * @author azu
 */
public class Item extends LocatedObjectWithProperties implements LocatedObjectWithPropertiesInterface {

    public static double weightItemSizeMultiplier = 0.1;

    private double weight;

    LocatedObjectWithPropertiesInterface owner;

    //TODO перемещения предметов в линкед кордс
    public Item(TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner) {
        super(tCord);
        setName("item name");
        setDescription("item description");
        setSymbol('I');
        setOwner(owner);
    }

    @Override
    public ObjectType getType() {
        return ObjectType.ITEM;
    }

    @Override
    public double getSize() {
        return getWeight() * weightItemSizeMultiplier;
    }

    public double getWeight(){
        return getPropertyByType(WorldObjectPropertyType.MATERIALS) != null?((MaterialsAndPartsListPropertyInterface)getPropertyByType(WorldObjectPropertyType.MATERIALS)).getWeight():0;
    }

    public LocatedObjectWithPropertiesInterface getOwner(){
        return owner;
    }

    private synchronized void onPickUp(LocatedObjectWithPropertiesInterface owner){
        this.owner = owner;
        getLinkedCordinates().moveTo(owner.getLinkedCordinates());
        getLinkedCordinates().bindTo(owner.getLinkedCordinates());

    }

    private synchronized void onDrop(){
        getLinkedCordinates().unbind();
        TileCordinatesLinkedToChunkBindListener l = owner.getLinkedCordinates().getBinding();
        if(l != null){
            getLinkedCordinates().bindTo(l);
        }
        owner = null;
    }

    public void setOwner(@Nullable LocatedObjectWithPropertiesInterface owner) {
        if(owner == null){
            if(this.owner != null){
                onDrop();
            }
        } else {
            if (this.owner != null){
                System.out.println("!!!!STRANGE OWNER CHANGE:" + this.owner.toString() + " -> " + owner.toString());
                onDrop();
                onPickUp(owner);
            } else {
                onPickUp(owner);
            }
        }

    }

    public boolean hasOwner(){
        return owner != null;
    }

    @Override
    public String toString(){
        return "objectItem" + getName();
    }

    public static double getSumWeight(List<Item> items){
        if(items == null){
            return 0;
        }
        double res = 0;
        for(Item item : items){
            res += item.getWeight();
        }
        return res;
    }
}
