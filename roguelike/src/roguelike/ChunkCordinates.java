package roguelike;

import roguelike.map.Chunk;

/**
 *
 * @author azu
 */
public class ChunkCordinates extends Cordinates{
    public ChunkCordinates(){
        
    }

    public ChunkCordinates(ChunkCordinates cord){
        super(cord);
    }

    public ChunkCordinates(int x_, int y_){
        super(x_, y_);
    }

    public ChunkCordinates(int x_, int y_, int z_){
        super(x_, y_, z_);
    }

    public boolean isTileIncludedAt(TileCordinates tCord){
        
        return (tCord.getX() / Chunk.chunkSizeX == getX()) && (tCord.getY() / Chunk.chunkSizeY == getY()) && (tCord.getZ() / Chunk.chunkSizeZ == getZ())
                && (tCord.getX() >= 0) && (tCord.getY() >= 0) && (tCord.getZ() >= 0);
    }
    public TileCordinates toTileCordinates(){
        return new TileCordinates(getX() * Chunk.chunkSizeX, getY() * Chunk.chunkSizeY, getZ() * Chunk.chunkSizeZ);
    }
    
}
