/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

/**
 *
 * @author azu
 */
public class TileCordinates extends Cordinates{
    public TileCordinates(){
        
    }
    public TileCordinates(TileCordinates cord){
        super(cord);
    }        
    public TileCordinates(int x_, int y_){
        super(x_, y_);
    }
    public TileCordinates(int x_, int y_, int z_){
        super(x_, y_, z_);
    }
    
    public ChunkCordinates toChunkCordinates(){
        return new ChunkCordinates(x / 16, y / 16, z / 16);
    }
}
