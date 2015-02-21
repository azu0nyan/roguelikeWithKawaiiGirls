package roguelike.objectsAndProperties;

import org.jetbrains.annotations.Nullable;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.map.Chunk;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 02.10.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class LocatedObjectWithProperties implements LocatedObjectWithPropertiesInterface {

    private final EnumMap<WorldObjectPropertyType,List<WorldObjectPropertyInterface>> properties;

    private TileCordinatesLinkedToChunk tileCord;

    private String name = "located object Name";
    private String description = "located object description";
    private char symbol = 'L';

    public LocatedObjectWithProperties(TileCordinatesLinkedToChunk tileCord){
        this.tileCord = tileCord.clone();
        this.tileCord.setOwner(this);
        //tileCord.getChunk().getTile(tileCord).addObject(this);//TODO раскоментить когда перепилю тайлы
        properties = new EnumMap<>(WorldObjectPropertyType.class);
    }

    //properties
    @Override
    public void addProperty(WorldObjectPropertyInterface property){
        if(property == null){
            System.out.println("WTF!! trying to add null property");
            return;
        }
        synchronized (properties){
            for(WorldObjectPropertyType propertyType : property.getPropertyTypes()){
                List<WorldObjectPropertyInterface> propertyByType = properties.get(propertyType);
                if(propertyByType == null){
                    propertyByType = new ArrayList<>();
                    properties.put(propertyType, propertyByType);
                }
                propertyByType.add(property);
            }
        }
    }

    @Override
    public void removeProperty(WorldObjectPropertyInterface property){
        if(property == null){
            System.out.println("WTF!! trying to delete null property");
            return;
        }
        synchronized (properties){
            for(WorldObjectPropertyType propertyType : property.getPropertyTypes()){
                List<WorldObjectPropertyInterface> propertyByType = properties.get(propertyType);
                if(!propertyByType.remove(property)){
                    System.out.println("WTF!! trying to delete not exist property");
                }
            }
        }
    }

    @Override
    public boolean hasPropertyByType(WorldObjectPropertyType type){
        synchronized (properties){
            List<WorldObjectPropertyInterface> propertiesByType = properties.get(type);
            return propertiesByType != null && propertiesByType.size() > 0;
        }
    }

    @Override
    public List<WorldObjectPropertyInterface> getPropertiesByType(WorldObjectPropertyType type){
        synchronized (properties){
            List<WorldObjectPropertyInterface> propertiesByType = properties.get(type);
            if(propertiesByType != null){
                return new ArrayList<WorldObjectPropertyInterface>(properties.get(type));
            } else {
                return new ArrayList<WorldObjectPropertyInterface>();
            }
        }
    }

    @Override
    public @Nullable WorldObjectPropertyInterface getPropertyByType(WorldObjectPropertyType type){
        synchronized (properties){
            List<WorldObjectPropertyInterface> propertiesByType = properties.get(type);
            if(propertiesByType != null && propertiesByType.size() > 0){
                return propertiesByType.get(0);
            } else {
                return null;
            }
        }
    }

    @Override
    public TileCordinatesLinkedToChunk getLinkedCordinates(){
        return tileCord;
    }

    public Chunk getChunk(){
        return tileCord.getChunk();
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public char getSymbol(){
        return symbol;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public String toString(){
        return name;
    }
}
