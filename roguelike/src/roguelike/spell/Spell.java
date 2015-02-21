package roguelike.spell;

/**
 *
 * @author nik
 */
public abstract class Spell {
    int id = 1;
    String description = "Spell descriotion"; 
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    
}
