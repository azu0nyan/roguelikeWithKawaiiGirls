package roguelike.DAO;

import roguelike.Game;
import roguelike.item.loot.LootList;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.Material;
import roguelike.worldobject.WorldObjectPropertyType;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectProperty;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 01.10.13
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */

/**
 * type             arg0                arg1                arg2                arg3                arg4            arg5        arg6        arg7        arg8        arg9        arg10
 * GATHERABLE       lootListID          -                   -                   -                   -
 */
public class PropertyPrototype {

    WorldObjectPropertyType propertyType;
    String[] args;

    Game game;

    PropertyPrototype(Game game) {
        this.game = game;
        args = new String[10];
    }

    public static PropertyPrototype parse(ResultSet rs, Game game) throws SQLException {
        PropertyPrototype propertyPrototype = new PropertyPrototype(game);
        String propertyType = rs.getString("propertyType");
        if(propertyType == null || "".equals(propertyType)){
            return null;
        }
        propertyPrototype.propertyType = WorldObjectPropertyType.valueOf(propertyType);
        for (int i = 0; i < 10; i++) {
            propertyPrototype.args[i] = rs.getString("arg" + i);
        }
        return propertyPrototype;
    }

    public WorldObjectPropertyInterface createInstance(LocatedObjectWithProperties owner) {
        return createInstance(owner, null, null, null);
    }

    public WorldObjectPropertyInterface createInstance(LocatedObjectWithProperties owner,
                                                       Material objectPartMaterial, String objectPartName, Double objectPartSize) {//Material
        switch (propertyType) {
            case NONE:
                return null;
            case GATHERABLE: {
                LootList list = game.getLootListLoader().getLootList(Integer.valueOf(args[0]));
                return new GatheringWorldObjectProperty(owner, list);
            }
        }
        return null;
    }


}
