package roguelike.DAO.prototypes;

import it.biobytes.ammentos.FieldTypeEnum;
import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;
import roguelike.DAO.HasId;
import roguelike.Game;
import roguelike.creature.Body;
import roguelike.creature.Creature;

/**
 *
 * @author nik
 */
@PersistentEntity(
        sourceDomain = "BODIES",
        targetDomain = "BODIES",
        primaryKey = "id"
)
public class BodyPrototype implements HasId {

    @PersistentField(
            fieldName = "id"
    )
    public int id;
    @PersistentField
    public String name;
    @PersistentField
    public int mainBodyPart;
    @PersistentField
    public double maxBlood;
    @PersistentField
    public double bloodRegeneration;
    
    public Body getInstance(Game game, Creature owner){
        Body body = new Body(owner);
        body.setMaxBlood(maxBlood);
        body.setBloodRegeneration(bloodRegeneration);
        body.setMainBodyPart(game.getBodyPartsLoader().getBodyPartById(mainBodyPart));
        return body;
    }

    public String getName(){
        return name;
    }    
    public void setName(String name){
        this.name = name;
    }

    @Override
    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainBodyPart() {
        return mainBodyPart;
    }

    public void setMainBodyPart(int mainBodyPart) {
        this.mainBodyPart = mainBodyPart;
    }

    public double getMaxBlood() {
        return maxBlood;
    }

    public void setMaxBlood(double maxBlood) {
        this.maxBlood = maxBlood;
    }

    public double getBloodRegeneration() {
        return bloodRegeneration;
    }

    public void setBloodRegeneration(double bloodRegeneration) {
        this.bloodRegeneration = bloodRegeneration;
    }

    @Override
    public String toString(){
        return id + ":" + name;
    }


}
