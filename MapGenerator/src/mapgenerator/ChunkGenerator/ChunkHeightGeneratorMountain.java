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
public class ChunkHeightGeneratorMountain extends ChunkHeightGeneratorInterface{
    int x;
    int y;
    int radius;
    int height;
    int levelsCount = 256;
    LinkedList<ChunkHeightGeneratorIncreaseHeightInCircle> heightGenerators;
    public ChunkHeightGeneratorMountain(ChunkMapGenerator generator, int x, int y, int radius, int height){
        super(generator);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.height = height;
        heightGenerators = new LinkedList<ChunkHeightGeneratorIncreaseHeightInCircle>();
        createGenerators();
    }
    private void createGenerators(){
        Random r = new Random();
        for(int i = 0; i < levelsCount; i++){
            //берем кординаты внутри круга
            int dx = r.nextInt(radius);
            int dy = r.nextInt((int)Math.sqrt((double)(radius * radius - dx * dx)));
            int tempRadius = (int)(Math.sqrt((double)(dx * dx + dy * dy)) * (1 + Math.random()));
            dx *= getRandomSign();
            dy *= getRandomSign();           
            ChunkHeightGeneratorIncreaseHeightInCircle gen = 
                    new ChunkHeightGeneratorIncreaseHeightInCircle(generator, x + dx, y + dy, tempRadius, 1);
            heightGenerators.add(gen);
        }
    }
    public int getRandomSign(){
        //TODO сделать класс tools
        if(Math.random() > 0.5){
            return 1;
        }
        return -1;
    }
    @Override
    public void generateChunkHeight(GeneratingChunk chunk) {
        //немного оптимизации
        if((Math.pow(chunk.cord.x - x, 2)  + Math.pow(chunk.cord.y - y, 2)) < radius * radius * 8){
            int tempHeight = chunk.height;
            for(ChunkHeightGeneratorIncreaseHeightInCircle generator : heightGenerators){
                generator.generateChunkHeight(chunk);
            }
            double tempDoubleHeight = (double)chunk.height - (double)tempHeight;
            tempDoubleHeight = (double)height * tempDoubleHeight / (double)levelsCount;
            chunk.height = tempHeight + (int)tempDoubleHeight;
 
        }        
    }
}
