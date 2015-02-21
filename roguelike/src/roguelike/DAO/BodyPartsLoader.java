package roguelike.DAO;

import roguelike.DAO.prototypes.BodyPartPrototype;
import roguelike.Tools;
import roguelike.creature.BodyPart;

/**
 *
 * @author azu
 */
public class BodyPartsLoader extends SmthPrototypesLoader<BodyPartPrototype>{

    public int maxTagID = 0;
    public int maxConnectedID = 0;

    public int getNextTagId(){
        maxTagID++;
        return maxTagID;
    }

    public int getNextConnectedId() {
        maxConnectedID++;
        return maxConnectedID;
    }

    public void loadBodyPartsTypesFromDB(String DBName) throws Exception {
        loadFromDB(DBName, BodyPartPrototype.class);
        //TODO WTF SIZE????
        maxTagID = getPrototypes().stream().map( p -> {
                    p.getTags().size();
                    return p.getTags().stream().map(i -> i.getID()).reduce(maxTagID, Tools.max);
                }).reduce(maxTagID, Tools.max);
        maxConnectedID = getPrototypes().stream().map(x -> {
            x.getConnectedBodyPartPrototypes().size();
            return x.getConnectedBodyPartPrototypes().stream().map(HasId::getID).reduce(maxConnectedID, Tools.max);
        }).reduce(maxConnectedID, Tools.max);
    }

    public BodyPart getBodyPartById(int id) {
        return getPrototypeById(id).getInstance(this);
    }
}