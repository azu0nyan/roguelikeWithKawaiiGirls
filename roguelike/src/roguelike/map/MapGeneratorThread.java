package roguelike.map;

import roguelike.*;
import roguelike.worldgenerator.PreGeneratedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Следит за кординатами и генерирует карту вокруг него
 * mapGeneratorThread = new MapGeneratorThread(this, preMap);  *
 * mapGeneratorThread.generateFirstTime();
 * mapGeneratorThread.start();
 * @author azu
 */
public class MapGeneratorThread extends Thread implements TileCordinatesChangedListener, ChunkObserverGenerator {

    public static int wallID = 2;
    public static int waterID = 3;

    private Game game;
    private TileCordinatesLinkedToChunk center;
    private static PreGeneratedMap preGeneratedMap;
    public SynchronousQueue<TileCordinates> cordinatesChangesHistory;
    private List<Chunk> loadedChunks;
    private int chunkLoadRadius = 30;


    public MapGeneratorThread(Game game, PreGeneratedMap preGeneratedMap){
        super("map generator");
        this.preGeneratedMap = preGeneratedMap;
        this.game = game;
        loadedChunks = new ArrayList<>();
        cordinatesChangesHistory = new SynchronousQueue<>();

    }

    public synchronized void setTileCordLinked(TileCordinatesLinkedToChunk tileCordLinked){
        if(center != null){
            center.removeListener(this);
        }
        center = tileCordLinked;
        center.addListener(this);
        cordinatesChanged(center, 0, 0, 0);
    }

    @Override
    public void cordinatesChanged(TileCordinatesLinkedToChunk tCord, int dx, int dy, int dz) {
        try {
            cordinatesChangesHistory.put(tCord.cloneSimpleCordinates());
        } catch (InterruptedException e) {
            System.out.println("ERROR Cordinates changed processing interrupted in mapGeneratorThread tCord:" + tCord + " dx:" + dx + " dy:" + dy + " dz:" + dz);
        }
    }

    @Override
    public void run() {
        System.out.println("Map generator thread started");
        while(!game.isExit){
            TileCordinates  newCurrentCordinates = null;
            try {
                newCurrentCordinates = cordinatesChangesHistory.take();
                System.out.println("Chunk generating zone cordinates changed to " + newCurrentCordinates.toString());
                process(newCurrentCordinates);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void process(TileCordinates newCurrentCordinates){
        List<Chunk> chunksToLoad = center.getChunk().getChunksInRadius(newCurrentCordinates, chunkLoadRadius);
        for(Chunk loadedChunk : loadedChunks){
            if(!chunksToLoad.contains(loadedChunk)){
                loadedChunk.removeObserver(this);
            }
        }
        for(Chunk chunkToLoad : chunksToLoad){
            chunkToLoad.addObserver(this);
        }
    }
    @Override
    public String getChunkObserverDescription() {
        return "Chunk observer at " + center.toString() + " in queue "  + cordinatesChangesHistory.size();
    }

    /**
     * по мере необходимости генерирует новые куски карты
     */
    public void update(){

    }

    public void generateFirstTime(){
        ChunkAggregation c = new ChunkAggregation();
        c.setSize(preGeneratedMap.maxX, preGeneratedMap.maxY, 1);
        generateChunkAggregation(c);
        game.getMap().setChunkAggregation(c);
    }

    /**
     * Инициализирует чанки
     *
     */
    private void generateChunkAggregation(ChunkAggregation c){
        System.out.println("ChunkAggregation generation started");
        c.chunks = new Chunk[c.getSizeX()][c.getSizeY()][c.getSizeZ()];
        System.out.println("Aggregation have " + String.valueOf(c.getSizeX() * c.getSizeY() * c.getSizeZ()) + " chunks");
        //инициализация чанков
        for(int i = 0; i < c.getSizeX(); i++){
            for(int j = 0; j < c.getSizeY(); j++){
                for(int k = 0; k < c.getSizeZ(); k++){
                    c.chunks[i][j][k] = new Chunk(new ChunkCordinates(i + c.getCordinates().getX(), j + c.getCordinates().getY(), k + c.getCordinates().getZ()), game);
                    c.chunks[i][j][k].setProtototype(preGeneratedMap.getChunkPrototype(i, j));
                }
            }
        }
        //creating Chunk links
        for(int i = 0; i < c.getSizeX(); i++){
            for(int j = 0; j < c.getSizeY(); j++){
                for(int k = 0; k < c.getSizeZ(); k++){
                    if(i > 0){
                        c.chunks[i][j][k].decXChunk = c.chunks[i - 1][j][k];
                    }
                    if(i < c.getSizeX()- 1){
                        c.chunks[i][j][k].incXChunk = c.chunks[i + 1][j][k];
                    }
                    if(j > 0){
                        c.chunks[i][j][k].decYChunk = c.chunks[i][j - 1][k];
                    }
                    if(j < c.getSizeY() - 1){
                        c.chunks[i][j][k].incYChunk = c.chunks[i][j + 1][k];
                    }
                    if(k > 0){
                        c.chunks[i][j][k].decZChunk = c.chunks[i][j][k - 1];
                    }
                    if(k < c.getSizeZ() - 1){
                        c.chunks[i][j][k].incZChunk = c.chunks[i][j][k + 1];
                    }
                }
            } 
        }
    }

    /**
     *  z  = x * xCoeff + y * yCoeff + freeCoeff;
     */
    public static Triple<Double, Double, Double> getXYPlaneCoefs(double x0, double y0, double z0, double x1, double y1, double z1, double x2, double y2, double z2){
        /*
        * |x - x0, x1 - x0, x2 - x0|
        * |y - y0, y1 - y0, y2 - y0| = 0
        * |z - z0, z1 - z0, z2 - z0|
        * (x - x0)Dyz - (y - y0)Dxz + (z - z0)Dxy = 0
        */
        double dyz = Tools.det(y1 - y0, y2 - y0, z1 - z0, z2 - z0);
        double dxz = Tools.det(x1 - x0, x2 - x0, z1 - z0, z2 - z0);
        double dxy = Tools.det(x1 - x0, x2 - x0, y1 - y0, y2 - y0);
        /*System.out.println("x - " +  x0 + " ," + (x1 - x0) + ", " + (x2 - x0));
        System.out.println("y - " +  y0 + " ," + (y1 - y0) + ", " + (y2 - y0));
        System.out.println("z - " +  z0 + " ," + (z1 - z0) + " ," + (z2 - z0));
        System.out.println("dyz:" + dyz + " dxz:" + dxz  + " dxy:" + dxy );         */
        double xCoeff = - dyz / dxy;
        double yCoeff = dxz / dxy;
        double freeCoeff = (x0 * dyz - y0 * dxz + z0 * dxy) / dxy;
        //System.out.println("z = " + xCoeff + " * x + " + yCoeff + " * y + " + freeCoeff);
        return new Triple<Double, Double, Double>(xCoeff, yCoeff, freeCoeff);


    }
    public void generateChunk(Chunk chunk){
        long startTime = System.currentTimeMillis();
        System.out.println("Generating chunk at " + chunk.getCordinates().toString());
        int tiles [][][] =  chunk.getAndCreateTiles();
        int chunkX = chunk.getCordinates().getX();
        int chunkY = chunk.getCordinates().getY();
        /**
         * 1__________2
         * | plane1   _|
         * |        _| |
         * |      _|   |
         * |    _|     |
         * |  _|       |
         * |_|__plane2_|
         * 3           4
         */
        Chunk chunk1 = chunk;
        ChunkCordinates chunkCord = chunk.getCordinates();
        TileCordinates tileChunkCord = chunkCord.toTileCordinates();
        Plane plane1 = new Plane(getXYPlaneCoefs(
            tileChunkCord.getX(), tileChunkCord.getY(), preGeneratedMap.getXYHeight(chunkCord.getX(), chunkCord.getY()),
            tileChunkCord.getX() + Chunk.chunkSizeX, tileChunkCord.getY(), preGeneratedMap.getXYHeight(chunkCord.getX() + 1, chunkCord.getY()),
            tileChunkCord.getX(), tileChunkCord.getY() + Chunk.chunkSizeY, preGeneratedMap.getXYHeight(chunkCord.getX(), chunkCord.getY() + 1)));
        Plane plane2 = new Plane(getXYPlaneCoefs(
                tileChunkCord.getX() + Chunk.chunkSizeX, tileChunkCord.getY(), preGeneratedMap.getXYHeight(chunkCord.getX() + 1, chunkCord.getY()),
                tileChunkCord.getX(), tileChunkCord.getY() + Chunk.chunkSizeY, preGeneratedMap.getXYHeight(chunkCord.getX(), chunkCord.getY() + 1),
                tileChunkCord.getX() + Chunk.chunkSizeX, tileChunkCord.getY() + Chunk.chunkSizeY, preGeneratedMap.getXYHeight(chunkCord.getX() + 1, chunkCord.getY() + 1)));

        for(int i = 0; i < Chunk.chunkSizeX; i++){//plane1
            for (int j = 0; j < Chunk.chunkSizeX; j++){
                double z;
                if( i + j < Chunk.chunkSizeX){
                    z = plane1.getZ(tileChunkCord.getX() + i, tileChunkCord.getY() + j);
                } else {
                    z = plane2.getZ(tileChunkCord.getX() + i, tileChunkCord.getY() + j);
                }

                if(z >= tileChunkCord.getZ()){
                    for (int k = (int)Math.min(z - tileChunkCord.getZ(), Chunk.chunkSizeZ - 1); k >= 0; k--){
                        tiles[i][j][k] = CONFIGURATION.wallID;
                    }
                }
            }
        }
        for(int l = 0; l <= preGeneratedMap.seaLevel; l++){
            for(int i = 0; i < Chunk.chunkSizeX; i++){
                for (int j = 0; j < Chunk.chunkSizeY; j++){
                    if(tiles[i][j][l] == 0){
                        tiles[i][j][l] = CONFIGURATION.waterID;
                    }
                }
            }
        }
        System.out.println("Chunk at " + chunk.getCordinates().toString() + " generated for " + (System.currentTimeMillis() - startTime) + "ms");

    }

    public boolean isWalkableAt(Chunk c, int x, int y, int z){
        int t = c.tiles[x][y][z];
        if (t == -1){
            return false;
        }
        if(game.getTileType(t).isWalkable){
            if(t != 0){
                return true;                
            }else{                
                if((z > 0) && game.getTileType(c.tiles[x][y][z - 1]).isWalkableOver){
                    return true;
                }
            }
        }        
        return false;
    }

    /**
     * добавляет в чанк с лестницы там где они нужны
     */
    private void addLadders(Chunk c,double chance){//chance 0 - 1
       if(chance < 0){
           chance = 0;
       }
       if(chance > 1){
           chance = 1;
       }
       for(int i = 0; i < Chunk.chunkSizeX; i++){
            for(int j = 0; j < Chunk.chunkSizeY; j++){
                for(int k = 0; k < Chunk.chunkSizeZ - 1; k++){//!!!! 15
                    if(isWalkableAt(c, i, j, k)){
                        if((i < 15) && !game.getTileType(c.tiles[i + 1][j][k]).isWalkable && isWalkableAt(c, i + 1, j, k + 1)){
                            if(Math.random() > chance){
                                c.tiles[i][j][k] =4;
                            }
                        }
                        if((i > 0) && !game.getTileType(c.tiles[i - 1][j][k]).isWalkable && isWalkableAt(c, i - 1, j, k + 1)){
                            if(Math.random() > chance){
                                c.tiles[i][j][k] = 4;
                            }
                        }
                        if((j < 15) && !game.getTileType(c.tiles[i][j + 1][k]).isWalkable && isWalkableAt(c, i, j + 1, k + 1)){
                            if(Math.random() > chance){
                                c.tiles[i][j][k] = 4;
                            }
                        }
                        if((j > 0) && !game.getTileType(c.tiles[i][j - 1][k]).isWalkable &&  isWalkableAt(c, i, j - 1, k + 1)){
                            if(Math.random() > chance){
                                c.tiles[i][j][k] = 4;
                            }
                        }
                    
                    }
                }
            }
        }  
    }
    /**
     * сглаживает ландшафт, к сожалению не так как нужно
     */
    private void smooth(Chunk c, int count, int level){ //level 0 - 6   
        //TODO использовать глобальные кординаты и соседние чанки
        for(int i = 0; i < Chunk.chunkSizeX; i++){
            for(int j = 0; j < Chunk.chunkSizeY; j++){
                for(int k = 0; k < Chunk.chunkSizeZ; k++){
                    int emptyTilesCount = 0;
                    if((i > 0) && (c.tiles[i - 1][j][k] == 0)){
                        emptyTilesCount++;
                    }
                    if((i < 15) && (c.tiles[i + 1][j][k] == 0)){
                        emptyTilesCount++;
                    }
                    if((j > 0) && (c.tiles[i][j - 1][k] == 0)){
                        emptyTilesCount++;
                    }
                    if((j < 15) && (c.tiles[i][j + 1][k] == 0)){
                        emptyTilesCount++;
                    }
                    if((k > 0) && (c.tiles[i][j][k - 1] == 0)){
                        emptyTilesCount++;
                    }
                    if((k < 15) && (c.tiles[i][j][k + 1] == 0)){
                        emptyTilesCount++;
                    }
                    if(emptyTilesCount >= level){
                        c.tiles[i][j][k] = 0;
                    } else if(emptyTilesCount < 2){
                        c.tiles[i][j][k] = 2;
                    }
                }
            }
        } 
        if(count > 0){
            smooth(c,count - 1, level);
        }
    }
    /**
     * Заполняет чанк тайлами типа tile
     */
    private void fillChunk(Chunk c, int type){

        for(int i = 0; i < Chunk.chunkSizeX; i++){
            for(int j = 0; j < Chunk.chunkSizeY; j++){
                for(int k = 0; k < Chunk.chunkSizeZ; k++){
                    
                    c.tiles[i][j][k] = type;
                }
            }
        }
    }
}
