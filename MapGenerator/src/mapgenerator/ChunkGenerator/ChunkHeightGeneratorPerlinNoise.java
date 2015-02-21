/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

/**
 *
 * @author azu
 */
public class ChunkHeightGeneratorPerlinNoise extends ChunkHeightGeneratorInterface{
    int range = 128;
    public ChunkHeightGeneratorPerlinNoise(ChunkMapGenerator generator, int range){
        super(generator);
        this.range = range;
    }
    @Override
    public void generateChunkHeight(GeneratingChunk chunk) {
        int n = chunk.cord.x + chunk.cord.y * 57;
        n = (n<<13) ^ n;
        int height = range / 2 + (int)((range / 2) * ( 1.0f - ( (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) /
     1073741824.0f));
        chunk.height += height; 
    }
    
}
