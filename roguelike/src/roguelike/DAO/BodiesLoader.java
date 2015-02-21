package roguelike.DAO;

import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import it.biobytes.ammentos.Ammentos;
import it.biobytes.ammentos.PersistenceException;
import it.biobytes.ammentos.query.Query;
import org.sqlite.SQLiteConnectionPoolDataSource;
import roguelike.DAO.prototypes.BodyPrototype;
import roguelike.Game;
import roguelike.creature.Body;
import roguelike.creature.Creature;

import javax.sql.DataSource;

/**
 *
 * @author nik
 */
public class BodiesLoader extends  SmthPrototypesLoader<BodyPrototype>{
    public void loadBodiesFromDB(String dbName) throws PersistenceException, ClassNotFoundException {
        loadFromDB(dbName, BodyPrototype.class);
    }

    public Body getBodyById(int id, Creature owner){
        return getPrototypeById(id).getInstance(owner.getLinkedCordinates().getChunk().getGame(), owner);
    }
}
