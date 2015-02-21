/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author azu
 */
public class ChunkHeightGeneratorMountainFail extends ChunkHeightGeneratorInterface{
    int x;
    int y;
    int radius;
    public ChunkHeightGeneratorMountainFail(ChunkMapGenerator generator, int x, int y, int radius){
        super(generator);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    @Override
    public void generateChunkHeight(GeneratingChunk chunk) {
        Random r = new Random();
        double distance = Math.sqrt(Math.pow(x - chunk.cord.x, 2) + Math.pow(y - chunk.cord.y, 2)); 
        if(distance < radius){
            LinkedList<GeneratingChunk> neighbours = generator.getGeneratedNeighbours(chunk);
            int avrNeighbourHeight = 0;
            if(neighbours.size() != 0){
                for(GeneratingChunk ch : neighbours){
                    avrNeighbourHeight += ch.height;
                }
                avrNeighbourHeight /= neighbours.size();
            } else {
                avrNeighbourHeight = chunk.height;
            }
            double koeff = 1;
            /*avrNeighbourHeight -= chunk.height;//высота коры не завимит от высоты поверхности 
            avrNeighbourHeight = Math.abs(avrNeighbourHeight);*/
            koeff = (double)(radius - distance) / ((double)radius * 10);
            chunk.height += (int) (avrNeighbourHeight + (Math.random() * koeff * avrNeighbourHeight));
        }
        
    }
    
}
