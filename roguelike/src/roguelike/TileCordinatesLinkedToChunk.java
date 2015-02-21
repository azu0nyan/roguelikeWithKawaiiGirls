package roguelike;

import com.sun.istack.internal.Nullable;
import roguelike.map.Chunk;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 02.05.13
 * Time: 2:50
 * To change this template use File | Settings | File Templates.
 */
public class TileCordinatesLinkedToChunk extends TileCordinates {

    Chunk chunk;

    ArrayList<TileCordinatesChangedListener> tileCordinatesChangedListeners = new ArrayList<TileCordinatesChangedListener>();

    TileCordinatesLinkedToChunkBindListener bindListener;
    private LocatedObjectWithPropertiesInterface owner;

    /**
     *
     * @param chunk
     * @param tCord
     * @param owner владелец
     * @throws Exception
     */
    public TileCordinatesLinkedToChunk(Chunk chunk, TileCordinates tCord, @Nullable LocatedObjectWithPropertiesInterface owner) throws Exception {
        super(tCord);
        if(chunk == null){
            throw new Exception("Chunk must not be null");
        }
        setOwner(owner);
        setChunk(chunk);
    }

    /**
     * @param cordToBind кординаты будут изменяться соответственно
     */
    public synchronized void bindTo(TileCordinatesLinkedToChunk cordToBind){
        if(bindListener != null){
            bindListener.unBind();
        }
        bindListener = new TileCordinatesLinkedToChunkBindListener(this, cordToBind);
    }

    public void bindTo(TileCordinatesLinkedToChunkBindListener l) {
        bindTo(l.getBindedCordinates());
    }

    public synchronized void unbind(){
        if(bindListener != null){
            bindListener.unBind();
            bindListener = null;
        } else {
            System.out.println("WTF!!! trying to unbind non exist binding");
        }
    }

    private void setChunk(Chunk chunk){
        if(chunk == null){
            return;
        }
        if(owner != null){
            chunk.addObject(owner);
            if(this.chunk != null){
                this.chunk.removeObject(owner);
            }
        }
        this.chunk = chunk;
    }

    public Chunk getChunk(){
        return chunk;
    }

    @Override
    public void cordinatesChanged(int dx, int dy, int dz){
        setChunk(chunk.getChunk(this));
        for(TileCordinatesChangedListener tileCordinatesChangedListener : tileCordinatesChangedListeners){
            tileCordinatesChangedListener.cordinatesChanged(this, dx, dy, dz);
        }
    }

    public void addListener(TileCordinatesChangedListener tileCordinatesChangedListener){
        tileCordinatesChangedListeners.add(tileCordinatesChangedListener);
    }

    public void removeListener(TileCordinatesChangedListener tileCordinatesChangedListener){
        tileCordinatesChangedListeners.remove(tileCordinatesChangedListener);
    }
    public TileCordinatesLinkedToChunkBindListener getBinding(){
        return bindListener;
    }


    public synchronized TileCordinatesLinkedToChunk clone(LocatedObjectWithPropertiesInterface newOwner){
        try {
            TileCordinatesLinkedToChunk res = new TileCordinatesLinkedToChunk(chunk, this, newOwner);
            if(bindListener != null){
                res.bindTo(bindListener.getBindedCordinates());
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("WTF такое никода не может произойти");
        }
        return null;
    }

    public synchronized TileCordinatesLinkedToChunk clone(){
        return clone(owner);
    }

    public int getTile(){
        return getChunk().getTile(this);
    }

    public void setOwner(LocatedObjectWithPropertiesInterface owner) {
        this.owner = owner;
        if(getChunk() != null){
            getChunk().addObject(owner);
        }
    }

    public synchronized TileCordinates cloneSimpleCordinates(){
        return new TileCordinates(this);
    }
}
