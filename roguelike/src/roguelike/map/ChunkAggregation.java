package roguelike.map;

import roguelike.ChunkCordinates;
import roguelike.TileCordinates;

/**
 *
 * @author azu
 */
public class ChunkAggregation {
    
    private ChunkCordinates cord;//меньший(по всем кординатам угол)
    @Deprecated
    private int type = 0;
    private int sizeX = 4;//chunks
    private int sizeY = 2;//chunks
    private int sizeZ = 2;//chunks
    Chunk[][][] chunks = null;
    //links ArrayList<ChunkAggregation> neighbours;

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public ChunkAggregation(){
       cord = new ChunkCordinates(0,0,0); 
    }

    public void setSize(int x, int y, int z){
        sizeX = x;
        sizeY = y;
        sizeZ = z;        
    }

    public void setSizeX(int x){
        sizeX = x;
    }

    public void setSizeY(int y){
        sizeY = y;
    }

    public void setSizeZ(int z){
        sizeZ = z;
    }

    public int getSizeX(){
        return sizeX;
    }

    public int getSizeY(){
        return sizeY;
    }

    public int getSizeZ(){
        return sizeZ;
    }

    public ChunkCordinates getCordinates(){
        return cord;
    }

    public void setCordinates(ChunkCordinates cord){
        this.cord = cord;
    }

    public int getTileAt(TileCordinates tCord){
        if((tCord.getX() >= 0) && (tCord.getY() >= 0) && (tCord.getZ() >= 0)){
            Chunk c = getChunk(tCord.toChunkCordinates());
            if(c != null){
                return c.getTile(tCord);
            }
        }
        return -1;
    }

    public void setTile(TileCordinates tCord, int t){
        if((tCord.getX() >= 0) && (tCord.getY() >= 0) && (tCord.getZ() >= 0)){
            Chunk c = getChunk(tCord.toChunkCordinates());
            if(c != null){
                c.setTile(tCord, t);
            }
        }
    }

    public boolean isCordinatesIncluded(ChunkCordinates cCord){
        return (cCord.getX() >= cord.getX()) && (cCord.getX() < cord.getX() + sizeX) &&
               (cCord.getY() >= cord.getY()) && (cCord.getY() < cord.getY() + sizeY) &&
               (cCord.getZ() >= cord.getZ()) && (cCord.getZ() < cord.getZ() + sizeZ);
        
    }

    public boolean isCordinatesIncluded(TileCordinates tCord){
        return isCordinatesIncluded(tCord.toChunkCordinates());
    }

    public Chunk getChunk(TileCordinates tCord){
        return getChunk(tCord.toChunkCordinates());
    }

    public Chunk getChunk(ChunkCordinates cCord){
        if(isCordinatesIncluded(cCord)){
            return chunks[cCord.getX() - cord.getX()][cCord.getY() - cord.getY()][cCord.getZ() - cord.getZ()];
        }
        return null;
    }

    public int getMaxHeight(){
        return Chunk.chunkSizeZ * sizeZ;
    }

    public int getMinHeight(){
        return 0;
    }
}
