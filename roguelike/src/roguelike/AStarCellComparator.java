package roguelike;

import java.util.Comparator;

/**
 *
 * @author nik
 */
public class AStarCellComparator implements Comparator<AStarCell>{
    @Override
    public int compare(AStarCell x, AStarCell y)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        if (x.getCost() < y.getCost()){
            return -1;
        } else if (x.getCost() > y.getCost()){
            return 1;
        }
        return 0;
    }
}
