package roguelike;

import it.biobytes.ammentos.Ammentos;
import org.sqlite.SQLiteDataSource;
import roguelike.creature.BodyPartTag;
import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.StatsObjectPropertyInterface;
import roguelike.stats.DamageElement;
import roguelike.stats.DamageType;
import roguelike.stats.ItemDefenseStatsInterface;
import roguelike.stats.Stats;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.EnumSet;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 *
 * @author nik
 */
public class Tools {

    public static long time;

    public static void startTimer(){
        time = System.currentTimeMillis();
    }

    public static long endTimer(){
        return System.currentTimeMillis() - time;
    }

    public static BodyPartTag mapElementToBodyPartImmuneTag(DamageElement element) {
        switch (element) {
            case NATURE:
                return BodyPartTag.NATUREIMMUNE;
            case FROST:
                return BodyPartTag.FROSTIMMUNE;
            case FIRE:
                return BodyPartTag.FIREIMMUNE;
            case HOLY:
                return BodyPartTag.HOLYIMUNE;
            case UNHOLY:
                return BodyPartTag.UNHOLYIMUNE;
            case PHUSICAL:
                return BodyPartTag.PHUSICALIMMUNE;
        }
        return null;
    }

    public static BodyPartTag mapDamageTypeToBodyPartImunneTag(DamageType type){
        switch(type){
            case CUTTING:
                return BodyPartTag.CUTTINGIMMUNE;
            case CRUSHING: 
                return BodyPartTag.CRUSHINGIMMUNE;
            case PIERCING: 
                return BodyPartTag.PIERCINGIMMUNE;
            case UNIFORM:
                return BodyPartTag.DAMAGEIMMUNE;                    
        }
        return null;
    }

    public static boolean isMagicDamage(DamageElement element){
        if(element != null  && element != DamageElement.PHUSICAL){
            return true;
        }
        return false;        
    }

    public static boolean isPhusicalDamage(DamageElement element){
        if(element != null && element == DamageElement.PHUSICAL){
            return true;
        } 
        return false;
        
    }

    public static int getResist(ItemDefenseStatsInterface resists, DamageElement element){
        if (resists != null) {
            switch (element) {
                case NATURE:
                    return resists.getNatureResist();
                case FROST:
                    return resists.getFrostResist();
                case FIRE:
                    return resists.getFireResist();
                case HOLY:
                    return resists.getHolyResist();
                case UNHOLY:
                    return resists.getUnholyResist();
                case PHUSICAL:
                    return resists.getPhusicalResist();
            }
        }
        return 0;
    }

    public static int getDefense(Item item, DamageType type) {
        if (item != null && item.getPropertiesByType(WorldObjectPropertyType.STATS) != null) {
            Stats stats = ((StatsObjectPropertyInterface)(item.getPropertiesByType(WorldObjectPropertyType.STATS).get(0))).getStats();
            switch (type) {
                case CUTTING:
                    return stats.getCuttingDefense();
                case CRUSHING:
                    return stats.getCrushingDefense();
                case PIERCING:
                    return stats.getPiercingDefense();
                case UNIFORM:
                    return 0;
            }
        }
        return 0;
    }

    public static int getMovementDirection(TileCordinates creatureCord, TileCordinates Target){
       
        if(creatureCord.getX() > Target.getX()){
            return 1;
        }        
        if(creatureCord.getX() < Target.getX()){
            return 2;
        }   
        if(creatureCord.getY() > Target.getY()){
            return 3;
        }        
        if(creatureCord.getY() < Target.getY()){
            return 4;
        }  
        return 0;
        
    }

    public static double getDistance(TileCordinates start, TileCordinates end){
        /**
         * расстояние между двумя точками
         */
        return Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) +
                         (start.getY() - end.getY()) * (start.getY() - end.getY()) +
                         (start.getZ() - end.getZ()) * (start.getZ() - end.getZ()));
    }

    public static TileCordinates getNearestTileCordinates(TileCordinates point, List<TileCordinates> tileCordinates){
        if(tileCordinates.size() == 0){
            return  null;
        }
        TileCordinates res = tileCordinates.get(0);
        double minDistance = getDistance(res, point);
        for(TileCordinates tCord : tileCordinates){
            double tmpDistance = getDistance(tCord, point);
            if(tmpDistance < minDistance){
                minDistance = tmpDistance;
                res = tCord;
            }
        }
        return res;
    }
    public static String getProgressString(double value, double max, int length){
        if(max == 0){
            return "";
        }
        int sharpsCount = (int)(length * value / max);        
        char [] str = new char[length];
        for(int i = 0; i < length; i++){
            if(i < sharpsCount){
                str[i] = '#';                
            } else {
                str [i] = '-';
            }
        }
        return "[" + (new String(str)) + "]";
    }

    public static <T> String getSeparatedStringList(List<T> list, String start, String end, String separator){
        String res = start;
        boolean first = true;
        for(T o : list){
            if(first){
                res += o.toString();
                first = false;
            } else {
                res += separator;
                res += o.toString();
            }
        }
        res += end;
        return res;
    }

    public static String getSeparatedStringList(EnumSet<BodyPartTag> tags, String start, String end, String separator) {
        String res = start;
        boolean first = true;
        for(Object o : tags){
            if(first){
                res += o.toString();
                first = false;
            } else {
                res += separator;
                res += o.toString();
            }
        }
        res += end;
        return res;
    }

    /**
     * a b
     * c d
     */
    public static double det(double a, double b, double c, double d){
        return a * d - b * c;
    }

    public static void initAmmentosIfNot() {
        if(Ammentos.getDataSource() != null){
            return;
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + CONFIGURATION.dbName);
        Ammentos.setDataSource(dataSource);
    }

    public static BinaryOperator<Integer> max = (id1, id2) -> (id1 > id2)?id1:id2;
    public static BinaryOperator<Integer> min = (id1, id2) -> (id1 < id2)?id1:id2;
}
