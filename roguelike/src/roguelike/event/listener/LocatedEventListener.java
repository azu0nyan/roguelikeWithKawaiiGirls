package roguelike.event.listener;

import roguelike.map.Chunk;
import roguelike.TileCordinatesChangedListener;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.event.EventType;

import java.util.List;

/**
 *
 * @author azu
 */
public abstract class LocatedEventListener extends EventListener implements TileCordinatesChangedListener{

    private int tilesRadius;
    private List<Chunk> listeningChunks;

    public LocatedEventListener(EventType type, TileCordinatesLinkedToChunk tCord, int tilesRadius){
        super(type);
        this.tilesRadius = tilesRadius;
        tCord.addListener(this);

        //Запиливаемся в слушатели
        listeningChunks = tCord.getChunk().getChunksInRadius(tCord, tilesRadius);
        for(Chunk chunk : listeningChunks){
            chunk.addListener(this);
        }
    }

    @Override
    public final void cordinatesChanged(TileCordinatesLinkedToChunk tCord, int dx, int dy, int dz) {
        List<Chunk> chunks = tCord.getChunk().getChunksInRadius(tCord, tilesRadius);
        //удаляем старые
        for(Chunk chunk : listeningChunks){
            if(!chunks.contains(chunk)){
                chunk.removeListener(this);
            }
        }
        //добавляем новые
        for(Chunk chunk : chunks){
            if(!listeningChunks.contains(chunk)){
                chunk.addListener(this);
            }
        }
        listeningChunks = chunks;
    }
}
