/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

/**
 *
 * @author nik
 */
public class ChunkHeightGeneratorIncreaseHeightInCircle extends ChunkHeightGeneratorInterface{
    int x;
    int y;
    int radius;
    int height = 10;
    
    public ChunkHeightGeneratorIncreaseHeightInCircle(ChunkMapGenerator generator, int x, int y, int radius, int height){
        super(generator);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.height = height;
    }
    @Override
    public void generateChunkHeight(GeneratingChunk chunk) {
        double distance = Math.sqrt(Math.pow(x - chunk.cord.x, 2) + Math.pow(y - chunk.cord.y, 2)); 
        if(distance < radius){
            chunk.height += height;
        }
    }
    
}
