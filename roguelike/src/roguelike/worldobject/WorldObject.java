package roguelike.worldobject;

import roguelike.TileCordinatesLinkedToChunk;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.ObjectType;

/**
 *
 * @author nik
 */
public class WorldObject extends LocatedObjectWithProperties implements LocatedObjectWithPropertiesInterface{

    private double size;

    public WorldObject(TileCordinatesLinkedToChunk tileCord){
        super(tileCord);
        size = 0;
        setName("WorldObject");
        setSymbol('W');
        setDescription("World object description");
    }

    public void update(){
        
    }

    @Override
    public ObjectType getType() {
        return ObjectType.WORLDOBJECT;
    }

    @Override
    public double getSize() {
        return size;
    }
}
