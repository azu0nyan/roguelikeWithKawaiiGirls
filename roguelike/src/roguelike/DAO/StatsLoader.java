package roguelike.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import roguelike.stats.Stats;

/**
 *
 * @author nik
 */
public class StatsLoader {
     private ArrayList<Stats> stats;
    
    public StatsLoader(){
        stats = new ArrayList<Stats>();
    }
    private void addStats(Stats stats){
        this.stats.add(stats);
    }
    public Stats getStats(String name){
        for(Stats stat : stats){
            if(stat.getName().equalsIgnoreCase(name)){
                return stat;
            }
        }
        return null;
    }
    
    public void loadStatsFromDB(String DBName, String tableName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat_ = conn.createStatement();
        ResultSet rs = stat_.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {    
            Stats stat = new Stats();
            stat.setId(rs.getInt("id"));
            stat.setName(rs.getString("name"));
            stat.hitPoints = rs.getInt("baseHP");
            stat.manaPoints = rs.getInt("baseMP");
            stat.stamina = rs.getInt("stamina");
            stat.intellect = rs.getInt("intellect");
            stat.agility = rs.getInt("agility");
            stat.strength = rs.getInt("strength");  
            stat.speed = rs.getInt("movementSpeed");
            addStats(stat);
        }        
        //System.out.println(String.valueOf(stats.size()));
        rs.close();
        conn.close();            
    }
}
    

