/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

/**
 *
 * @author nik
 */
public class Cordinates {
    public int x = 0;
    public int y = 0;
    public int z = 0;
    @Override
    public boolean equals(Object other)
    {
        if (this == other) return true;
        if (other == null) return false;
        //понадеемся что следующая строчка нормально будет 
        //работать с наследниками
        if(this.getClass() != other.getClass()) return false;
        Cordinates otherObj = (Cordinates)other;
        return (this.x == otherObj.x) && (this.y == otherObj.y) && 
                    (this.z == otherObj.z);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.x;
        hash = 41 * hash + this.y;
        hash = 41 * hash + this.z;
        return hash;
    }
    
    boolean equals(Cordinates c){
        if(c == this){
            return true;
        } else if(c == null){
            return false;
        }        
        return (this.x == c.x) && (this.y == c.y) && (this.z == c.z);
    }
    @Override
    public String toString(){
        return Integer.toString(x) + " " + Integer.toString(y) + " "
                + Integer.toString(z); 
    }
    
    Cordinates(){
        
    }
    public Cordinates(Cordinates cord){
        if(cord != null){
            x = cord.x;
            y = cord.y;
            z = cord.z;
        }
    }
    public void add(Cordinates cord){
        x += cord.x;
        y += cord.y;
        z += cord.z;
    }        
    public Cordinates(int x_, int y_){
        x = x_;
        y = y_;
        z = 0;
    }
    public Cordinates(int x_, int y_, int z_){
        x = x_;
        y = y_;
        z = z_;
    }
    
    
}
