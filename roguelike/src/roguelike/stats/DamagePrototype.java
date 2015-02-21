package roguelike.stats;


/**
 *
 * @author nik
 */
public class DamagePrototype {
    public DamageValue damage;
    private String name;
    private int id;
    
    public DamagePrototype(){
        damage = new DamageValue();
    }
    public DamageValue getInstance(){
        return damage.getCopy();
    }
    public String getName(){
        return name;
    }    
    public void setName(String name){
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
