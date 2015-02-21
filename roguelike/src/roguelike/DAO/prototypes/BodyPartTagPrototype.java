package roguelike.DAO.prototypes;

import it.biobytes.ammentos.AutomaticType;
import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;
import roguelike.DAO.HasId;
import roguelike.creature.BodyPartTag;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 17.01.14
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
@PersistentEntity(
        sourceDomain = "BODY_PART_TAGS",
        targetDomain = "BODY_PART_TAGS",
        primaryKey = "id"
)
public class BodyPartTagPrototype implements HasId {
    @PersistentField(
            fieldName = "id",
            automaticType = AutomaticType.FRAMEWORK
    )
    int id;

    @PersistentField(
            fieldName = "bodyPartID",
            automaticType = AutomaticType.FRAMEWORK
    )
    int bodyPartID;

    @PersistentField
    String tag;

    public BodyPartTag getTag(){
        try{
            return BodyPartTag.valueOf(tag);
        } catch (IllegalArgumentException e){
            System.out.println("!!!Wrong body part tag:" + tag);
        }
        return null;
    }

    @Override
    public int getID() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBodyPartID() {
        return bodyPartID;
    }

    public void setBodyPartID(int bodyPartID) {
        this.bodyPartID = bodyPartID;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
