package roguelike.DAO;

import com.sun.istack.internal.Nullable;
import roguelike.Game;
import roguelike.item.loot.LootList;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 01.10.13
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public class LootListLoader {
    private Map<Integer, LootList> lootLists;
    private Game game;

    public LootListLoader(Game game_){
        game = game_;
        lootLists = new HashMap<>();
    }

    public LootList getLootList(int id){
        return lootLists.get(id);
    }

    public void loadLootListsFromDB(String DBName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        String query = "SELECT * FROM LOOT_LIST ORDER BY id";
        System.out.println(query);
        ResultSet rs = stat.executeQuery(query);
        Integer id = 0;
        LootList tmp = null;
        while (rs.next()) {
            int newID = rs.getInt("id");
            if(newID != id){
                if(tmp != null){
                    addLootList(id, tmp);
                }
                tmp = new LootList(game.getItemPrototypesLoader(), null);
                id = newID;
            }
            double chance = rs.getDouble("chance");
            int itemID = rs.getInt("itemId");
            int count = rs.getInt("count");
            for(int i = 0; i < count; i++){
                tmp.addItemToDropList(chance, itemID);
            }
        }
        if(tmp != null){
            addLootList(id, tmp);
        }
        rs.close();
        conn.close();

    }

    public void addLootList(Integer id, LootList l){
        lootLists.put(id, l);
    }
    public LootList getLootListById(int id, @Nullable LocatedObjectWithProperties owner){
        LootList list = getLootList(id);
        return list.cloneWithEmptyLoot(owner);
    }
}
