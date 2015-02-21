/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.ChunkGenerator;

/**
 *
 * @author azu
 */
public abstract class ChunkHeightGeneratorInterface {
    ChunkMapGenerator generator;
    public ChunkHeightGeneratorInterface(ChunkMapGenerator generator){
        this.generator = generator;
    }
    public abstract void generateChunkHeight(GeneratingChunk chunk);
}
