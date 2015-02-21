package roguelike.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import roguelike.CONFIGURATION;

/**
 *
 * @author nik
 */
public class DamagesLoader {
    LinkedList<DamagePrototype> prototypes;
    int damagesToLoad;

    public DamagesLoader() {
        prototypes = new LinkedList<DamagePrototype>();
        damagesToLoad = CONFIGURATION.maxDamagesToLoad;
    }

    public void addPrototypeType(DamagePrototype type) {
        prototypes.add(type);
    }

    public DamageValue getDamageByName(String name) {
        synchronized (prototypes) {
            for (DamagePrototype damage : prototypes) {
                if (damage.getName().equalsIgnoreCase(name)) {
                    return damage.getInstance();
                }
            }
        }
        return null;
    }

    public DamageValue getDamageById(int id) {
        synchronized (prototypes) {
            for (DamagePrototype damage : prototypes) {
                if (damage.getId() == id) {
                    return damage.getInstance();
                }
            }
        }
        return null;
    }

    public void loadDamagesFromDB(String DBName, String tableName) throws Exception {
        //DBName RogueLikeDB.s3db tableName Damages 
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {
            DamagePrototype prototype = new DamagePrototype();
            prototype.setId(rs.getInt("id"));
            prototype.setName(rs.getString("name"));
            for (int i = 1; i <= damagesToLoad; i++) {
                int damage = 0;
                try {
                    damage  = rs.getInt("damage" + String.valueOf(i));
                    
                } catch (Exception continue_){
                    continue;
                }
                String tempType = rs.getString("type" + String.valueOf(i));
                DamageType type;
                try {
                     type = DamageType.valueOf(tempType);                                     
                } catch (Exception ignore) {
                    if (tempType != null && "".equals(tempType) &&  "null".equals(tempType)) {
                        System.out.println("Unknown DamageType:" + tempType);
                    }
                    continue;
                }
                String tempElement = rs.getString("element" + String.valueOf(i));
                DamageElement element;
                try {
                    element = DamageElement.valueOf(tempElement);                                     
                } catch (Exception ignore) {
                    if (tempElement != null && "".equals(tempElement) && "null".equals(tempElement)) {
                        System.out.println("Unknown DamgeElement:" + tempElement);
                    }
                    continue;
                }
                prototype.damage.addDamageEntry(new DamageEntry(damage, type, element));                
            }
            addPrototypeType(prototype);
        }
    }
}
