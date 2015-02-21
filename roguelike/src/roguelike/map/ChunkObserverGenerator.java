package roguelike.map;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 27.10.13
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
public interface ChunkObserverGenerator {

    public String getChunkObserverDescription();

    public void generateChunk(Chunk c);

}
