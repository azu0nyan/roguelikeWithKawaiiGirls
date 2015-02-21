package roguelike;

import java.util.ArrayList;

/**
 *
 * @author azu
 */
public class Fraction {
    
    private int id = 1;
    private String name = "";
    private String description;
    private ArrayList<FractionRelationship> relationships;
    
    public Fraction(int id, String name){
        setId(id);
        setName(name);
        relationships = new ArrayList<FractionRelationship>();
    }    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    private void addRelationship(FractionRelationship fractionRelationship){
        relationships.add(fractionRelationship);
    }
    public void setRelationship(Fraction fraction, int relation){
        setRelationship(fraction.getId(), relation);
    }
    public void setRelationship(int id, int relation){
        boolean isSeted = false;
        for(FractionRelationship relationship : relationships){
            if(relationship.getID() == id){
                relationship.setRelationship(relation);
                isSeted = true;
                break;
            }
        }
        if(!isSeted){
            addRelationship(new FractionRelationship(id, relation));
        }
    }
    public int getRelationship(int id){
        for(FractionRelationship relationship : relationships){
            if (relationship.getID() == id){
                return relationship.getRelationships();
            }
        }
        return 0;
    }
    public int getRelationship(Fraction fraction){
        if(fraction != null){
            return getRelationship(fraction.getId());
        }
        return 0;
    }
    public boolean isEnemy(Fraction fraction){
        int relationship = getRelationship(fraction);
        return relationship < 0;
    }
    public boolean isNeutral(Fraction fraction){
        int relationship = getRelationship(fraction);
        return relationship >= 0 && relationship < 256;
    }
    public boolean isFriend(Fraction fraction){
        int relationship = getRelationship(fraction);
        return relationship >= 256;
    }
}
