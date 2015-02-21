package roguelike;

/**
 *
 * @author azu
 */
public class FractionRelationship {
    int fractionId;
    int relationship;
    
    public FractionRelationship(int id, int relationships){
        this.fractionId = id;
        this.relationship = relationships;
    }
    public int getID(){
        return fractionId;
    }
    public int getRelationships(){
        return relationship;
    }
    public void setRelationship(int relationship){
        this.relationship = relationship;
    }
}
