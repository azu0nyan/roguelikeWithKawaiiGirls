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
public class ChunkMapGenerator {
    
    int height = 512;
    int width = 512;
    public GeneratingChunk [][] chunks;
    LinkedList<GeneratingChunk> generatingQueue;
    LinkedList<ChunkHeightGeneratorInterface> generators;
    
    public ChunkMapGenerator(){
        chunks = new GeneratingChunk [width][height];
        generatingQueue = new LinkedList<GeneratingChunk>();
        generators = new LinkedList<ChunkHeightGeneratorInterface>();
    }
    public void generate(){
        for(int i = 0; i< width; i++){
            for(int j = 0; j < height; j++){
                chunks[i][j] = new GeneratingChunk(i, j);
            }
        }    
        prepareGenerators();
        generateOcean();//TODO заменить на генератор
        //generate landscape objects
        prepareGeneratingQueue();
        while(generatingQueue.size() > 0){
            GeneratingChunk generatingChunk = generatingQueue.pollFirst();
            generateChunk(generatingChunk);
            //добавляем несгенерированных соседей в конец списка
            for(GeneratingChunk ch : getNonGeneratedNeighbours(generatingChunk)){
                if(!generatingQueue.contains(ch)){
                    generatingQueue.addLast(ch);
                }
            }
        }
    }
    public void generateOcean(){
        //генерирует океан 
        //квадратный остров 
        for(int i = 0; i< width; i++){
            for(int j = 0; j < height; j++){
                if( i == 0 || i == width - 1 || j == 0 || j == height - 1){
                    //i < (width / 8) || i > 7 * (width / 8)// j < (height / 8) || j > 7 * (height / 8)                       
                    chunks[i][j].isOcean = true;
                    chunks[i][j].isGenerated = true;
                }
            }
        }
    }
    public void prepareGenerators(){
        
        /*ChunkHeightGeneratorInterface genp = new ChunkHeightGeneratorPerlinNoise(this, 8);
        generators.add(genp);*/
       
        Random r = new Random();
        for(int i = 0; i < 200; i++){
            int x =  r.nextInt(3 * 512 / 4) + 64;
            int y =  r.nextInt(3 * 512 / 4) + 64;
            int rad = r.nextInt(16) + 1;
            int height = r.nextInt(256);
            generators.add(new ChunkHeightGeneratorMountain(this, x, y, rad, height));
        }
        
        
    }
    public void generateChunk(GeneratingChunk chunk){
        for(ChunkHeightGeneratorInterface generator : generators){
            generator.generateChunkHeight(chunk);
        }
        chunk.isGenerated = true;
    }
    public void prepareGeneratingQueue(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(!chunks[i][j].isGenerated && getGeneratedNeighbours(chunks[i][j]).size() != 0){
                    //Нет повторений т.к. каждый чанк обрабатывается 1 раз
                    generatingQueue.add(chunks[i][j]);
                }
            }
        }
    }
    LinkedList<GeneratingChunk> getNeighbours(GeneratingChunk chunk){
        LinkedList<GeneratingChunk> list = new LinkedList<GeneratingChunk>();
        if(chunk.cord.x < width - 1){
            list.add(chunks[chunk.cord.x + 1][chunk.cord.y]);
        }
        if(chunk.cord.x > 0){
            list.add(chunks[chunk.cord.x - 1][chunk.cord.y]);
        }
        if(chunk.cord.y < height - 1){
            list.add(chunks[chunk.cord.x][chunk.cord.y + 1]);
        }
        if(chunk.cord.y > 0){
            list.add(chunks[chunk.cord.x][chunk.cord.y - 1]);
        }
        return list;
    }
    LinkedList<GeneratingChunk> getNonGeneratedNeighbours(GeneratingChunk chunk){
        LinkedList<GeneratingChunk> neighboursList = getNeighbours(chunk);
        LinkedList<GeneratingChunk> list = new LinkedList<GeneratingChunk>();
        for(GeneratingChunk ch : neighboursList){
            if(!ch.isGenerated){
                list.add(ch);
            }
        }
        return list;
    }
    LinkedList<GeneratingChunk> getGeneratedNeighbours(GeneratingChunk chunk){
        LinkedList<GeneratingChunk> neighboursList = getNeighbours(chunk);
        LinkedList<GeneratingChunk> list = new LinkedList<GeneratingChunk>();
        for(GeneratingChunk ch : neighboursList){
            if(ch.isGenerated){
                list.add(ch);
            }
        }
        return list;
    }
}
