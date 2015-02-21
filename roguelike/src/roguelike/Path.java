package roguelike;

import java.util.LinkedList;
/**
 *
 * @author nik
 */
public class Path {
    
    LinkedList<TileCordinates> path;
    
    public Path(){
        path = new LinkedList<TileCordinates>();
    }

    public void add(TileCordinates cord){
        path.addFirst(cord);
    }

    public void addLast(TileCordinates cord){
        path.addLast(cord);
    }

    public TileCordinates getNextPathPoint(){
        return path.pollFirst();
    }

    public TileCordinates getEndOfPath(){
        return path.peekLast();
    }

    public int getLength(){
        return path.size();
    }

    public String toString(){
        String res = "[";
        for(TileCordinates tileCordinates : path){
            res += tileCordinates.toString();
        }
        res += "]";
        return res;
    }
}
