/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

import roguelike.map.Chunk;

/**
 *
 * @author nik
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
        
        return (tCord.x / Chunk.chunkSizeX == x) && (tCord.y / Chunk.chunkSizeY == y) && (tCord.z / Chunk.chunkSizeZ == z)
                && (tCord.x >= 0) && (tCord.y >= 0) && (tCord.z >= 0);
    }
    public TileCordinates toTileCordinates(){
        return new TileCordinates(x * Chunk.chunkSizeX, y * Chunk.chunkSizeY, z * Chunk.chunkSizeZ);
    }
    
}
