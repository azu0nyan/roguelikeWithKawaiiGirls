package roguelike.DAO;

import roguelike.Fraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author azu
 */
public class FractionsLoader {
    
    private ArrayList<Fraction> fractions;
    
    public FractionsLoader(){
        fractions = new ArrayList<Fraction>();
    }
    private void addFraction(Fraction fraction){
        fractions.add(fraction);
    }
    public Fraction getFraction(String name){
        for(Fraction fraction : fractions){
            if(fraction.getName().equalsIgnoreCase(name)){
                return fraction;
            }
        }
        return null;
    }
    public void loaFractionsFromDB(String DBName, String tableName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {
            //читаем список фракций
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Fraction fraction = new Fraction(id, name);
            addFraction(fraction);
        }
        /*for(Fraction fraction : fractions){
            System.out.println(String.valueOf(fraction.getID()) + " " +
                    fraction.getName());
        }*/
        rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {
            //читаем список отношений фракций
            Fraction fraction = getFraction(rs.getString("name"));
            for(Fraction fract : fractions){
                fraction.setRelationship(fract, rs.getInt(fract.getName()));
            }
        }
        rs.close();
        conn.close();     
        
    }
}
