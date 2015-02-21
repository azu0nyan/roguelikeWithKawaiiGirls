/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

/**
 *
 * @author nik
 */
public class GeneratingChunk {
    public GeneratingChunk(int x, int y){
        cord = new ChunkCordinates(x, y);
    }
    public boolean isGenerated = false;
    public boolean isOcean = false;
    public int height = 0;
    ChunkCordinates cord;
    
}
