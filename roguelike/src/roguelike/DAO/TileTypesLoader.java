package roguelike.DAO;

import it.biobytes.ammentos.PersistenceException;
import roguelike.DAO.prototypes.TileType;

/**
 *
 * @author nik
 */

public class TileTypesLoader extends SmthPrototypesLoader<TileType>{
    
    /*private ArrayList<TileType> tileTypes;
    
    public TileTypesLoader(){
        tileTypes = new ArrayList<TileType>();
    }
    public TileType getTileType(int id_){
        for(TileType t:tileTypes){
            if(t.id == id_){
                return t;
            }//заменить на хешмап
        }
        return new TileType();
    }
    
    public void addTileType(TileType t){
        tileTypes.add(t);        
    }
    public void loadTileTypesFromDB(String DBName, String tableName) throws Exception{
            //DBName RogueLikeDB.s3db tablename Tiles
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {
            TileType t = new TileType();
            t.name = rs.getString("name");
            t.id = rs.getInt("typeId");
            t.symbol = rs.getString("Symbol").charAt(0);
            t.color = (short) rs.getInt("color");
            t.charColor = (short) rs.getInt("charColor");
            t.isWalkable = rs.getBoolean("isWalkable");
            t.isViewed = rs.getBoolean("isViewed");
            t.isLadder = rs.getBoolean("isLadder");
            t.isWalkableOver = rs.getBoolean("isWalkableOver");
            addTileType(t);
        }
        rs.close();
        conn.close();
        
        
    }      */
    public void loadTileTypesFromDB(String DBName) throws PersistenceException {
        loadFromDB(DBName, TileType.class);
    }

    public TileType getTileType(int id_) {
        return getPrototypeById(id_);
    }
}
