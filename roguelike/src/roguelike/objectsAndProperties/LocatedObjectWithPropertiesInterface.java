package roguelike.objectsAndProperties;

import roguelike.TileCordinatesLinkedToChunk;
import roguelike.worldobject.WorldObjectPropertyType;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 02.10.13
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public interface LocatedObjectWithPropertiesInterface {

    public void addProperty(WorldObjectPropertyInterface property);

    public void removeProperty(WorldObjectPropertyInterface property);

    public boolean hasPropertyByType(WorldObjectPropertyType type);

    public List<WorldObjectPropertyInterface> getPropertiesByType(WorldObjectPropertyType type);

    public WorldObjectPropertyInterface getPropertyByType(WorldObjectPropertyType type);

    public TileCordinatesLinkedToChunk getLinkedCordinates();


    public ObjectType getType();

    public double getSize();

    public String getName();

    public void setName(String name);//?

    public char getSymbol();

    public void setSymbol(char symbol); //?

    public String getDescription();

    public void setDescription(String description);//?
}
