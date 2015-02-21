package roguelike.stats;

/**
 *
 * @author nik
 */
/**
 * Структура для хранения характеристик, по умолчанию применяется ко всем
 * предметам
 */
public class Stats {

    int id = 0;
    String name = "";      
    
    public int hitPoints = 0;
    public int intellect = 0;
    public int strength = 0;
    public int agility = 0;
    public int manaPoints = 0;
    public int stamina = 0;    
    public int speed = 0;//милисекунд/тайл
    
    //резисты
    public int natureResist = 0;
    public int frostResist = 0;
    public int fireResist = 0;
    public int holyResist = 0;
    public int unholyResist = 0;
    public int phusicalResist = 0;
    //Не суммируются, защищают часть тела на которую надеты     
    public int cuttingDefense = 0;
    public int piercingDefense = 0;
    public int crushingDefense = 0;
    public Stats(Stats stats) {
        add(stats);
    }
    public Stats() {
        
    }
    public void add(Stats stats) {
        if (stats != null) {
            hitPoints += stats.hitPoints;
            manaPoints += stats.manaPoints;
            intellect += stats.intellect;
            strength += stats.strength;
            agility += stats.agility;
            stamina += stats.stamina;
            speed += stats.speed;
            
            natureResist += stats.natureResist;
            frostResist += stats.frostResist;
            fireResist += stats.fireResist;
            holyResist += stats.holyResist;
            unholyResist += stats.unholyResist;
            phusicalResist += stats.phusicalResist;
        }
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getCuttingDefense(){
        return cuttingDefense;
    }
    public int getPiercingDefense(){
        return piercingDefense;
    }
    public int getCrushingDefense(){
        return crushingDefense;
    }
}
