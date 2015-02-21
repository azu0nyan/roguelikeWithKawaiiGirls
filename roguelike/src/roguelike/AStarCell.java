package roguelike;

//import java.util.Objects;

/**
 *
 * @author azu
 */
public class AStarCell {
    /**
     * класс ячейки для реализации алгоритма А*
     */
    private TileCordinates cord;
    private AStarCell cameFromCell;//ячейка из которой пришли в данную
    private double pathToCellCost;//длинна пути до ячейки
    private double heuristicCost;//эврестическая стоимость ячейки
    private double cost;// = PathToCellCost + heuristicCost
    
    public AStarCell(TileCordinates cord_,AStarCell cell,double pathCost, double heuristicCost_){
        cord = cord_;
        cameFromCell = cell;
        pathToCellCost = pathCost;
        heuristicCost = heuristicCost_;
        cost = pathToCellCost + heuristicCost;
    }

    public AStarCell(){
        cord = new TileCordinates();
    }
    public AStarCell(TileCordinates cord){
        this.cord = cord;
    }

    public void setPathCost(double pCost){
       pathToCellCost = pCost;
       cost = pathToCellCost + heuristicCost; 
    }

    public void setHeuristicCost(double hCost){
        heuristicCost = hCost;
        cost = pathToCellCost + heuristicCost;
    }

    public double getCost(){
        return cost;
    }

    public double getPathCost(){
        return pathToCellCost;
    }

    public double getHeuristicCost(){
        return heuristicCost;
    }

    public TileCordinates getCordinates(){
        return cord;
    }

    public void setCordinates(TileCordinates cord){
        this.cord = cord;
    }

    public AStarCell getCameFromCell(){
        return cameFromCell;
    }

    public void setCameFromCell(AStarCell cell){
        this.cameFromCell = cell;
    }

    public int CompareTo(AStarCell cell){
        if(cell.cost < this.cost){
            return 1;
        } else if(cell.cost == this.cost){
            return 0;
        } 
        return -1;
    }

    @Override
    public boolean equals(Object other){
        //реализация скопироана (и подправленна) из како-го то мануала
        if(this == other) return true;
        if(other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        AStarCell otherObj = (AStarCell)other;
        return cord.equals(otherObj.cord);
    }

    @Override
    public int hashCode() {
        //автосгенерированный метод!
        int hash = 3;
        hash = 97 * hash;// Objects.hashCode(this.cord);
        return hash;
    }
        
}
