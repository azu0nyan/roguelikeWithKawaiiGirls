package roguelike.stats;

/**
 *
 * @author azu
 */
public class DamageEntry {
    private DamageType type; 
    private DamageElement element;
    private int value;
    public DamageEntry(int value, DamageType type, DamageElement element){
        this.value = value;
        this.type = type;
        this.element = element;
    }
    public DamageEntry(DamageEntry entry){
        this.value = entry.value;
        this.type = entry.type;
        this.element = entry.element;
    }
    public DamageEntry(int value, DamageEntry entry){
        this.value = value;
        this.type = entry.type;
        this.element = entry.element;
    }
    public  DamageType getType(){
        return type;
    }
    public DamageElement getElement(){
        return element;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void increaseValue(int value){
        this.value += value;
    }
    @Override
    public String toString(){
        return "[" + type.toString().substring(0, 4) + "|" +
                element.toString().substring(0, 4) + ":" + String.valueOf(value) + "]"; 
    }
}
