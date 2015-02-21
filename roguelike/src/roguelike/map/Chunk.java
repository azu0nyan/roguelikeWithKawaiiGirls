package roguelike.map;

import roguelike.*;
import roguelike.creature.MovementType;
import roguelike.datetime.GlobalDate;
import roguelike.event.EventInterface;
import roguelike.event.listener.EventListener;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.ObjectType;
import roguelike.worldgenerator.ChunkPrototype2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author azu
 */
public class Chunk {

    public static final int chunkSizeX = 16;
    public static final int chunkSizeY = 16;
    public static final int chunkSizeZ = 256;

    public int[][][] tiles = null;

    public Chunk incXChunk = null;
    public Chunk decXChunk = null;
    public Chunk incYChunk = null;
    public Chunk decYChunk = null;
    public Chunk incZChunk = null;
    public Chunk decZChunk = null;

    private double temperature = 20;//TODO больше температуры
    private ChunkPrototype2D protototype;

    private ChunkCordinates cord;

    private Game game;

    private List<ChunkObserverGenerator> observers;
    private ChunkState generationState = ChunkState.NON_GENERATED;

    private ChunkEventProcessor chunkEventProcessor = new ChunkEventProcessor();//ДА! вся работа с ивентами и слушателями тут

    List<LocatedObjectWithPropertiesInterface> objects;


    @Deprecated
    private int type = 0;/*
                            0 пустой
                            1 плоскость со случайными препятствиями
                            2 фрактальный чанк
                            3 заполненный
                            4 генерируется вместе с другими чанками(не вызовом generateChunk())
                         */

    
    public Chunk(ChunkCordinates cCord, Game game){
        cord = cCord;
        this.game = game;
        objects = new CopyOnWriteArrayList<>();
        observers = new ArrayList<>();
    }
    //WORDLDGEN ETC
    public void addObserver(ChunkObserverGenerator observer){
        synchronized (observers){
            if(!observers.contains(observer)){
                observers.add(observer);
                if(generationState.equals(ChunkState.NON_GENERATED)){
                    observer.generateChunk(this);
                }
            }
        }
    }

    public void removeObserver(ChunkObserverGenerator observer){
        synchronized (observers){
            observers.remove(observer);
            if(observers.size() == 0){
                freeTilesMemory();
            }
        }
    }

    public synchronized int [][][] getAndCreateTiles(){
        if(tiles == null){
            tiles = new int [chunkSizeX][chunkSizeY][chunkSizeZ];
        }
        return tiles;
    }

    public synchronized void setGenerationState(ChunkState state){
        this.generationState = state;
    }

    public ChunkState getGenerationState(){
        return generationState;
    }
    public synchronized void freeTilesMemory(){
        tiles = null;
        generationState = ChunkState.NON_GENERATED;
    }


    //OBJECTS
    public void  addObject(LocatedObjectWithPropertiesInterface obj){
        objects.add(obj);
    }

    public void removeObject(LocatedObjectWithPropertiesInterface obj){
        objects.remove(obj);
    }

    public List<LocatedObjectWithPropertiesInterface> getObjectsInTile(TileCordinates tCord){
        if(cord.isTileIncludedAt(tCord)){
            List<LocatedObjectWithPropertiesInterface> res = new ArrayList<>();
            for(LocatedObjectWithPropertiesInterface obj : objects){
                if(obj.getLinkedCordinates().same(tCord)){
                    res.add(obj);
                }
            }
            return res;
        } else {
            return getChunk(tCord).getObjectsInTile(tCord);
        }
    }

    public List<LocatedObjectWithPropertiesInterface> getObjectsInTile(TileCordinates tCord, ObjectType type){
        List res = new ArrayList();
        for(LocatedObjectWithPropertiesInterface obj : getObjectsInTile(tCord)){
            if(obj.getType().equals(type)){
                res.add(obj);
            }
        }
        return res;
    }

    private List<LocatedObjectWithPropertiesInterface> getObjects(){
        return objects;
    }

    public List<LocatedObjectWithPropertiesInterface> getObjectsInRadius(TileCordinates center, int tilesRadius){
        Set<LocatedObjectWithPropertiesInterface> res = new HashSet<>();
        for(Chunk c : getChunksInRadius(center, tilesRadius)){
            for(LocatedObjectWithPropertiesInterface obj : c.getObjects()){
                if(Tools.getDistance(center, obj.getLinkedCordinates()) <= tilesRadius){
                    res.add(obj);
                }
            }
        }
        return new ArrayList<>(res);//4 concurrency
    }

    public List<LocatedObjectWithPropertiesInterface> getObjectsInRadius(TileCordinates center, int tilesRadius, ObjectType type){
        List res = new ArrayList();
        for(LocatedObjectWithPropertiesInterface obj : getObjectsInRadius(center, tilesRadius)){
            if(obj.getType().equals(type)){
                res.add(obj);
            }
        }
        return res;
    }

    public double getSumObjectsSize(TileCordinates cord){
        double res = 0;
        for(LocatedObjectWithPropertiesInterface obj : getObjectsInTile(cord)){
            res += obj.getSize();
        }
        return res;
    }

    public boolean canMoveObject(TileCordinates cord, LocatedObjectWithPropertiesInterface obj){
        return (obj.getSize() + getSumObjectsSize(cord)) <= 1;
    }

    @Deprecated
    public int getType(){
        return type;
    }

    @Deprecated
    public void setType(int type){
        this.type = type;
    }

    public ChunkCordinates getCordinates(){
        return cord;
    }

    public void setCordinates(ChunkCordinates cord){
        this.cord = cord;
    }

    public boolean isTilesLoaded(){
        return !(tiles == null); 
    }

    public boolean inThisChunk(TileCordinates tCord){
        return cord.isTileIncludedAt(tCord);
    }

    public Chunk getChunk(ChunkCordinates cCord){
        if(cord.equals(cCord)){
            return this;
        } else {
            if((incXChunk != null) && (cCord.getX() > cord.getX())){
                return incXChunk.getChunk(cCord);
            }
            if((decXChunk != null) && (cCord.getX() < cord.getX())){
                return decXChunk.getChunk(cCord);
            }
            if((incYChunk != null) && (cCord.getY() > cord.getY())){
                return incYChunk.getChunk(cCord);
            }
            if((decYChunk != null) && (cCord.getY() < cord.getY())){
                return decYChunk.getChunk(cCord);
            }
            if((incZChunk != null) && (cCord.getZ() > cord.getZ())){
                return incZChunk.getChunk(cCord);
            }
            if((decZChunk != null) && (cCord.getZ() < cord.getZ())){
                return decZChunk.getChunk(cCord);
            }
        }
        return null;
    }

    public Chunk getChunk(TileCordinates tCord){
        return getChunk(tCord.toChunkCordinates());
    }

    public int getTile(TileCordinates tCord){
        if(cord.isTileIncludedAt(tCord)){
            if(tiles != null){
                return tiles[tCord.getX() % chunkSizeX][tCord.getY() % chunkSizeY][tCord.getZ() % chunkSizeZ];
            }
        } else {
            return getChunk(tCord).getTile(tCord);
        }
        return -1;
    }

    public void setTile(TileCordinates tCord, int t){
        if(cord.isTileIncludedAt(tCord)){
            if(tiles != null){
                tiles[tCord.getX() % chunkSizeX][tCord.getY() % chunkSizeY][tCord.getZ() % chunkSizeZ] = t;
            }
        } else {
            getChunk(tCord).setTile(tCord, t);
        }
    }

    @Deprecated
    public TileCordinates getNearAngle(TileCordinates tCord){
            //TODO как бы это получше сделать
            TileCordinates a000 = cord.toTileCordinates();
            TileCordinates a001 = cord.toTileCordinates();
            a001.add(0, 0, chunkSizeZ);
            TileCordinates a010 = cord.toTileCordinates();
            a010.add(0, chunkSizeY, 0);
            TileCordinates a011 = cord.toTileCordinates();
            a011.add(0, chunkSizeY, chunkSizeZ);
            TileCordinates a100 = cord.toTileCordinates();
            a100.add(chunkSizeX, 0, 0);
            TileCordinates a101 = cord.toTileCordinates();
            a101.add(chunkSizeX, 0,chunkSizeZ);
            TileCordinates a110 = cord.toTileCordinates();
            a110.add(chunkSizeX, chunkSizeY, 0);
            TileCordinates a111 = cord.toTileCordinates();
            a111.add(chunkSizeX, chunkSizeY, chunkSizeZ);
            ArrayList<TileCordinates> tCords = new ArrayList<TileCordinates>(8);
            tCords.add(a000);
            tCords.add(a001);
            tCords.add(a010);
            tCords.add(a011);
            tCords.add(a100);
            tCords.add(a101);
            tCords.add(a110);
            tCords.add(a111);
            return Tools.getNearestTileCordinates(tCord, tCords);
    }

    public List<Chunk> getChunksInRadius(TileCordinates center, int tilesRadius){
        if(inThisChunk(center)){
            ArrayList<Chunk> chunks = new ArrayList<Chunk>();
            if(tilesRadius == 1){
                chunks.add(this);
                return chunks;
            }
            int xChunks = 1 + tilesRadius / chunkSizeX;
            int yChunks = 1 + tilesRadius / chunkSizeY;
            int zChunks = 1 + tilesRadius / chunkSizeZ;
            for(int i = cord.getX() - xChunks; i < cord.getX() + xChunks; i++){
                for(int j = cord.getY() - yChunks; j < cord.getY() + yChunks; j++){
                    for(int k = cord.getZ() - zChunks; k < cord.getZ() + zChunks; k++){
                        Chunk c = getChunk(new ChunkCordinates(i, j, k));
                        if(c != null){
                            chunks.add(c);
                        }
                    }
                }
            }
            return chunks;
        } else {
            return getChunk(center).getChunksInRadius(center, tilesRadius);
        }
    }

    public double getTemperatureInTile(TileCordinates cord) {
        return getChunk(cord).getTemperature();
    }

    public double getTemperature(){
        return temperature;
    }
    //events

    public void addListener(EventListener eventListener){
        chunkEventProcessor.addEventListener(eventListener);
    }

    public void removeListener(EventListener eventListener){
        chunkEventProcessor.removeEventListener(eventListener);
    }

    public void processEvent(EventInterface event){
        chunkEventProcessor.processEvent(event);
    }
    //smth
    public Game getGame(){ //nebezopasno
        return game;
    }
    public GlobalDate getGlobalDate(){
        return game.getGlobalDate();
    }
    public boolean noMovementCollisions(TileCordinates start, TileCordinates end, MovementType type){
        return game.noMovementCollisions(start, end, type);
    }
    /**
     * DEBUG ONLY
     */
    public List<EventListener> getEventListeners(){
        return chunkEventProcessor.getEventListeners();
    }


    public ChunkPrototype2D getProtototype() {
        return protototype;
    }

    public void setProtototype(ChunkPrototype2D protototype) {
        this.protototype = protototype;
    }
}
