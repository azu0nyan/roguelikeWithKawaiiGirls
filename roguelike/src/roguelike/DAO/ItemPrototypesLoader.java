package roguelike.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.sun.istack.internal.Nullable;
import roguelike.CONFIGURATION;
import roguelike.DAO.prototypes.ItemPrototype;
import roguelike.Game;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.item.Item;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;

/**
 *
 * @author nik
 */
public class ItemPrototypesLoader {
    
    Map<Integer,ItemPrototype> itemTypes;
    Game game;
    public static int slotsToRead = CONFIGURATION.maxSlotsPerItem;
    
    public ItemPrototypesLoader(Game game){
        itemTypes = new HashMap<>();
        this.game = game;
        slotsToRead = CONFIGURATION.maxSlotsPerItem;
    }

    public void addItemPrototype(ItemPrototype type){
        itemTypes.put(type.getId(), type);
    }
    
    public Item getItemByName(String name, TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner){
        for(ItemPrototype itemPrototype : itemTypes.values()){
            if(itemPrototype.getName().equalsIgnoreCase(name)){
                return itemPrototype.createInstance(game, tCord, owner);
            }
        }
        return null;
    }

    public Item getItemById(int id, TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner){
        ItemPrototype res = itemTypes.get(id);
        if(res != null){
            return res.createInstance(game, tCord, owner);
        }
        return null;
    }
    public void loadItemPrototypeFromDB(String DBName, String tableName) throws Exception{
            //DBName RogueLikeDB.s3db tablename Tiles
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        //String query = "SELECT * FROM " + tableName + ";";
        String query = "SELECT ITEMS.*, ITEMS.id AS itemID, ITEMS.type AS itemType, OBJECT_PROPERTIES.*, PROPERTIES.*, PROPERTIES.type  AS propertyType, MATERIALS_4_TEMPLATES.* FROM ITEMS\n" +
                "  LEFT JOIN OBJECT_PROPERTIES ON (OBJECT_PROPERTIES.objectID = items.id AND OBJECT_PROPERTIES.objectType = \"ITEM\" )\n" +
                "  LEFT JOIN PROPERTIES ON (OBJECT_PROPERTIES.propertyID = PROPERTIES.id)\n" +
                "  LEFT JOIN MATERIALS_4_TEMPLATES ON (MATERIALS_4_TEMPLATES.objectId = items.id  AND MATERIALS_4_TEMPLATES.objectType = \"ITEM\")";
        System.out.println(query);
        ResultSet rs = stat.executeQuery(query);

        int id = 0;
        ItemPrototype tmp = null;
        while (rs.next()) {
            int newID = rs.getInt("itemID");
            if(newID != id){
                if(tmp != null){
                    addItemPrototype(tmp);
                }
                tmp = ItemPrototype.parse(rs);
                id = newID;
            }
            PropertyPrototype property = PropertyPrototype.parse(rs, game);
            if(property != null){
                tmp.addProperty(property);
            }
            String partName = rs.getString("templatePartName");
            int materialId = rs.getInt("materialId");
            if(materialId != 0){
                tmp.addMaterial(partName, game.getMaterialsLoader().getMaterial(materialId));
            }
        }
        if(tmp != null){
            addItemPrototype(tmp);
        }





        rs.close();
        conn.close();
        
        
    }
}
