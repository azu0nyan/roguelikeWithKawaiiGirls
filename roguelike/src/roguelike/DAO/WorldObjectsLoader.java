package roguelike.DAO;

import org.intellij.lang.annotations.Language;
import roguelike.Game;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.worldobject.WorldObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 19.08.13
 * Time: 2:31
 * To change this template use File | Settings | File Templates.
 */
public class WorldObjectsLoader {

    private Map<Integer,WorldObjectPrototype> worldObjectPrototypes;
    private Game game;

    public WorldObjectsLoader(Game game_){
        game = game_;
        worldObjectPrototypes = new HashMap<>();
    }

    public WorldObjectPrototype getWorldObjectPrototype(Integer id){
       return worldObjectPrototypes.get(id);
    }

    public void loadWorldObjectPrototypesFromDB(String DBName, String tableName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        @Language("SQL")
        String query = "SELECT WORLDOBJECTS.*, WORLDOBJECTS.id AS worldObjectID, OBJECT_PROPERTIES.*, PROPERTIES.*, PROPERTIES.type AS propertyType FROM WORLDOBJECTS " +
                "    LEFT JOIN OBJECT_PROPERTIES ON (OBJECT_PROPERTIES.objectID = WORLDOBJECTS.id " +
                "                               AND OBJECT_PROPERTIES.objectType = \"WORLDOBJECT\" )"+
                "    LEFT JOIN PROPERTIES ON (OBJECT_PROPERTIES.propertyID = PROPERTIES.id)";
        System.out.println(query);
        ResultSet rs = stat.executeQuery(query);
        int id = 0;
        WorldObjectPrototype tmp = null;
        while (rs.next()) {
            int newID = rs.getInt("worldObjectID");
            if(newID != id){
                if(tmp != null){
                    addWorldObjectPrototype(tmp);
                }
                tmp = WorldObjectPrototype.parse(rs);
                id = newID;
            }
            PropertyPrototype property = PropertyPrototype.parse(rs, game);
            if(property != null){
                tmp.addProperty(property);
            }
            //addWorldObjectPrototype(WorldObjectPrototype.parse(rs));
        }
        if(tmp != null){
            addWorldObjectPrototype(tmp);
        }
        rs.close();
        conn.close();

    }

    public void addWorldObjectPrototype(WorldObjectPrototype c){
        worldObjectPrototypes.put(c.id, c);
    }

    public WorldObject getWorldObjectById(int id, TileCordinatesLinkedToChunk tCord){
        WorldObjectPrototype prototype = getWorldObjectPrototype(id);
        return prototype.createInstance(tCord);
    }
}
