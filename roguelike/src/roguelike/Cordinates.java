package roguelike;

/**
 *
 * @author nik
 */
public class Cordinates {

    private int x = 0;
    private int y = 0;
    private int z = 0;

    public Cordinates(){

    }

    public Cordinates(Cordinates cord){
        if(cord != null){
            x = cord.x;
            y = cord.y;
            z = cord.z;
        }
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

    public boolean same(Cordinates cord){
        return cord != null && this.x == cord.x && this.y == cord.y && this.z == cord.z;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) return true;
        if (other == null) return false;
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
        return "(" + Integer.toString(x) + " " + Integer.toString(y) + " "  + Integer.toString(z) + ")";
    }
    
    public void moveTo(Cordinates cord){
        int dx = cord.x - this.x;
        int dy = cord.y - this.y;
        int dz = cord.z - this.z;
        x = cord.x;
        y = cord.y;
        z = cord.z;
        cordinatesChanged(dx, dy, dz);
    }

    public void add(Cordinates cord){
        x += cord.getX();
        y += cord.getY();
        z += cord.getZ();
        cordinatesChanged(cord.getX(), cord.getY(), cord.getZ());
    }

    public void add(int x, int y, int z){
        this.x += x;
        this.y += y;
        this.z += z;
        cordinatesChanged(x, y, z);
    }

    public void setX(int x){
        int dx = x - this.x;
        this.x = x;
        cordinatesChanged(dx, 0, 0);
    }

    public int getX(){
        return x;
    }

    public void setY(int y){
        int dy = y - this.y;
        this.y = y;
        cordinatesChanged(0, dy, 0);
    }

    public int getY(){
        return y;
    }

    public void setZ(int z){
        int dz = z - this.z;
        this.z = z;
        cordinatesChanged(0, 0, dz);
    }

    public int getZ(){
        return z;
    }

    public void cordinatesChanged(int dx, int dy, int dz){

    }

    public Cordinates cloneSimpleCordinates(){
        return new Cordinates(x, y, z);
    }
}
