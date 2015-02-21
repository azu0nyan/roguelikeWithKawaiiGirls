package roguelike.DAO;

import it.biobytes.ammentos.Ammentos;
import it.biobytes.ammentos.PersistenceException;
import it.biobytes.ammentos.query.Query;
import roguelike.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 17.01.14
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class SmthPrototypesLoader<TP extends HasId> {

    List<TP> prototypes;

    int maxId = 0;

    SmthPrototypesLoader(){
        prototypes = new ArrayList<>();
    }

    public void addAll(List<TP> list){
        prototypes.addAll(list);
    }

    public void add(TP p){
        prototypes.add(p);
    }

    public TP getPrototypeById(int id){
        for(TP t : prototypes){
            if(t.getID() == id){
                return t;
            }
        }
        return null;
    }

    public void loadFromDB(String dbName, Class c) throws PersistenceException {
        Tools.initAmmentosIfNot();
        List<TP> prototypes = Ammentos.load(c, new Query());
        addAll(prototypes);
        maxId = (prototypes != null)?
                (prototypes.stream().map(TP::getID).reduce(0, Tools.max))
                :0;
    }

    public void savePrototype(TP tp) throws PersistenceException {
        Tools.initAmmentosIfNot();
        Ammentos.save(tp);
    }

    public List<TP> getPrototypes(){
        return prototypes;
    }

    public void remove(TP prototype) throws PersistenceException {
        Tools.initAmmentosIfNot();
        Ammentos.delete(prototype);
    }

    public int getNextID(){
        maxId++;
        return maxId;
    }
}
