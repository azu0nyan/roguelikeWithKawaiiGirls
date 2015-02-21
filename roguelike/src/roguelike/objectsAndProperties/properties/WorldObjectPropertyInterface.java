package roguelike.objectsAndProperties.properties;

import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 01.10.13
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public interface WorldObjectPropertyInterface {

    public List<WorldObjectPropertyType> getPropertyTypes();

    public LocatedObjectWithPropertiesInterface getOwner();
}
