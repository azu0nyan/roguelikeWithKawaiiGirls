package roguelike.datetime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 22.08.13
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public enum Season {

    WINTER(new int[]{1, 2, 12}),
    SPRING(new int[]{3, 4, 5}),
    SUMMER(new int[]{6, 7, 8}),
    AUTUMN(new int[]{9, 10, 11});

    private List<Integer> months;

    Season(int[] months) {
        this.months = new CopyOnWriteArrayList<>();
        for (int month : months) {
            this.months.add(month);
        }
    }

    public List<Integer> getMonths(){
        return new ArrayList<>(months);
    }

    public static List<Season> getSeasonsByMonth(int month){//TODO сделать предрасчет этого
        List<Season> res = new ArrayList<>();
        for(Season season : Season.values()){
            if(season.inMonth(month)){
                res.add(season);
            }
        }

        return res;
    }

   public boolean inMonth(int month){
       return months.contains(month);
   }
}
