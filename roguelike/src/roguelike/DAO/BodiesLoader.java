package roguelike.DAO;

import it.biobytes.ammentos.PersistenceException;
import roguelike.DAO.prototypes.BodyPrototype;
import roguelike.creature.Body;
import roguelike.creature.Creature;

/**
 *
 * @author azu
 */
public class BodiesLoader extends  SmthPrototypesLoader<BodyPrototype>{
    public void loadBodiesFromDB(String dbName) throws PersistenceException, ClassNotFoundException {
        loadFromDB(dbName, BodyPrototype.class);
    }

    public Body getBodyById(int id, Creature owner){
        return getPrototypeById(id).getInstance(owner.getLinkedCordinates().getChunk().getGame(), owner);
    }
}
