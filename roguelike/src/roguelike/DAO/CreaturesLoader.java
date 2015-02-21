package roguelike.DAO;

import it.biobytes.ammentos.PersistenceException;
import roguelike.DAO.prototypes.CreaturePrototype;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.creature.Creature;

/**
 *
 * @author azu
 */
public class CreaturesLoader extends SmthPrototypesLoader<CreaturePrototype>{
    
    //private List<CreaturePrototype> creaturePrototypes;
    //private Game game;
    
    /*public CreaturesLoader(Game game_){
        game = game_;
        creaturePrototypes = new ArrayList<CreaturePrototype>();
    }

    public CreaturePrototype getCreatureType(int id_){
        for(CreaturePrototype c: creaturePrototypes){
            if(c.id == id_){
                return c;
            }//заменить на хешмап
        }
        return null;
    }
    public void loadCreatureTypesFromDB(String DBName, String tableName) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {
            CreaturePrototype c = new CreaturePrototype();
            c.id = rs.getInt("creatureId");
            c.name = rs.getString("name");
            c.fraction = rs.getString("fraction");
            c.stats = rs.getString("stats");
            c.size = rs.getDouble("size");
            c.aiType = rs.getString("AIType");  
            c.symbol = rs.getString("symbol").charAt(0);
            c.color = (short) rs.getInt("color");
            c.isHumanoid = rs.getBoolean("isHumanoid");
            c.isDead = rs.getBoolean("isDead");
            c.body = rs.getString("body");
            addCreatureType(c);
        }
        rs.close();
        conn.close();     
        
    }
    
    public void addCreatureType(CreaturePrototype c){
        if(!creaturePrototypes.contains(c)){
            creaturePrototypes.add(c);
        }
    }          */
    public void loadCreaturesFromDB(String dbName) throws PersistenceException {
        loadFromDB(dbName, CreaturePrototype.class);
    }

    public Creature getCreatureByType(int type, TileCordinatesLinkedToChunk tCord){
        CreaturePrototype cType = getPrototypeById(type);
        return cType.createInstance(tCord.getChunk().getGame(), tCord);
    }
    
}
