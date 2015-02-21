package roguelike.DAO;

import org.intellij.lang.annotations.Language;
import roguelike.Game;
import roguelike.objectsAndProperties.Material;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 06.10.13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class MaterialsLoader {

    private Map<Integer,Material> materials;
    private Game game;

    public MaterialsLoader(Game game_){
        game = game_;
        materials = new HashMap<>();
    }

    public Material getMaterial(Integer id){
        return materials.get(id);
    }

    public void loadMaterialsFromDB(String DBName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        @Language("SQL")
        String query = "SELECT MATERIALS.*, MATERIALS.id AS materialID, OBJECT_PROPERTIES.*, PROPERTIES.*, PROPERTIES.type AS propertyType FROM MATERIALS " +
                "    LEFT JOIN OBJECT_PROPERTIES ON (OBJECT_PROPERTIES.objectID = MATERIALS.id " +
                "                               AND OBJECT_PROPERTIES.objectType = \"MATERIAL\" )"+
                "    LEFT JOIN PROPERTIES ON (OBJECT_PROPERTIES.propertyID = PROPERTIES.id)";
        System.out.println(query);
        ResultSet rs = stat.executeQuery(query);
        int id = 0;
        Material tmp = null;
        while (rs.next()) {
            int newID = rs.getInt("materialID");
            if(newID != id){
                if(tmp != null){
                    addMaterial(tmp);
                }
                tmp = Material.parse(rs);
                id = newID;
            }
            PropertyPrototype property = PropertyPrototype.parse(rs, game);
            if(property != null){
                tmp.addProperty(property);
            }
        }
        if(tmp != null){
            addMaterial(tmp);
        }
        rs.close();
        conn.close();

    }

    public void addMaterial(Material c){
        materials.put(c.getId(), c);
    }

}
