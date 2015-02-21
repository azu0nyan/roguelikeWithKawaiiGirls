package roguelike.stats;

import java.util.LinkedList;

/**
 *
 * @author azu
 */
/**
 * структура для хранения урона разных типов
 */
public class DamageValue {

    private LinkedList<DamageEntry> damages;
    
    public DamageValue(){
        damages =  new LinkedList<DamageEntry>();        
    }

    public DamageValue(DamageValue damage) {
        this();
        if(damage == null){
            return;
        }
        damages =  new LinkedList<DamageEntry>(); 
        LinkedList<DamageEntry> tempDamages = damage.getDamages(); 
        if(tempDamages != null){
            synchronized(tempDamages){
                for(DamageEntry entry : tempDamages){
                    addDamageToExistingEntry(entry);
                }
            }
        }
    }

    public void addDamageByType(int value, DamageType type, DamageElement element){
        damages.add(new DamageEntry(value, type, element));
    }

    public void addDamageEntry(DamageEntry entry){
        damages.add(entry);
    }

    public void addDamageToExistingEntry(DamageEntry entry){
        boolean isAdded = false;
        synchronized(damages){
            for(DamageEntry damageEntry : damages){
                if(damageEntry.getType() == entry.getType() && damageEntry.getElement() == entry.getElement()){
                    damageEntry.increaseValue(entry.getValue());
                    isAdded = true;
                    break;
                }
            }
            if(!isAdded){
                addDamageEntry(new DamageEntry(entry));
            }
        }
    }

    public void addDamageValue(DamageValue value){
        LinkedList <DamageEntry> damage = value.getDamages();
        synchronized(damage){
            for(DamageEntry entry : damage){
                addDamageToExistingEntry(entry);
            }
        }
    }

    public int getSumDamage(){
        int sumDamage = 0;
        synchronized(damages){
            for(DamageEntry damage : damages){
                sumDamage += damage.getValue();
            }
        }
        return sumDamage;
    }

    public int getDamagesCount(){
        return damages.size();
    }

    public LinkedList<DamageEntry> getDamages(){
        return damages;
    }

    public void clear(){
        damages.clear();
    }

    public DamageValue getCopy() {
        return new DamageValue(this);
    }

    @Override 
    public String toString(){
        if(damages.size() == 0){
            return "nothing";
        }
        String str = "";
        for(DamageEntry dmg : damages){
            str += dmg.toString();
        }        
        return str;
    }
}
