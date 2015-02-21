package roguelike.DAO;

import roguelike.TileCordinatesLinkedToChunk;
import roguelike.worldobject.WorldObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 19.08.13
 * Time: 2:12
 * To change this template use File | Settings | File Templates.
 */
public class WorldObjectPrototype {
    public int id;
    public String name;
    public char symbol;
    public String description;
    public List<PropertyPrototype> properties;

    WorldObjectPrototype(){
        properties = new ArrayList<>();
    }
    public static WorldObjectPrototype parse(ResultSet rs) throws SQLException {
        WorldObjectPrototype res = new WorldObjectPrototype();
        res.id = rs.getInt("worldObjectID");
        res.name = rs.getString("name");
        res.symbol = rs.getString("symbol").charAt(0);
        res.description = rs.getString("description");

        return res;
    }

    public void addProperty(PropertyPrototype property){
        properties.add(property);
    }

    public WorldObject createInstance(TileCordinatesLinkedToChunk tCord){
        WorldObject res = new WorldObject(tCord);
        res.setName(name);
        res.setSymbol(symbol);
        res.setDescription(description);
        for(PropertyPrototype prototype : properties ){
            res.addProperty(prototype.createInstance(res));
        }

        return res;
    }
}
