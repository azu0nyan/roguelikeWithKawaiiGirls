package roguelike;

import roguelike.map.Chunk;

/**
 *
 * @author nik
 */
public class TileCordinates extends Cordinates{

    public TileCordinates(){
        
    }

    public TileCordinates(TileCordinates cord){
        super(cord);
    }

    public TileCordinates(int x, int y){
        super(x, y);
    }

    public TileCordinates(int x, int y, int z){
        super(x, y, z);
    }
    
    public ChunkCordinates toChunkCordinates(){
        return new ChunkCordinates(getX() / Chunk.chunkSizeX, getY() / Chunk.chunkSizeY, getZ() / Chunk.chunkSizeZ);
    }


}