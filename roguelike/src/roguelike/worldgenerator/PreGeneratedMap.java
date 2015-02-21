package roguelike.worldgenerator;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 27.10.13
 * Time: 4:24
 * To change this template use File | Settings | File Templates.
 */
public class PreGeneratedMap {

    ChunkPrototype2D[][] chunkPrototypes2D;
    public int maxX;
    public int maxY;
    public int seaLevel = 1;


    public PreGeneratedMap(int x, int y){
        maxX = x;
        maxY = y;
        chunkPrototypes2D = new ChunkPrototype2D [x + 1][y + 1];//не используемые буферные чанки сбоку


    }

    public ChunkPrototype2D getChunkPrototype(int x, int y){
        if(x < 0 || y < 0 || x > maxX || y > maxY){
            return null;
        }
        return chunkPrototypes2D[x][y];
    }
    public double getXYHeight(int x, int y){
        if(x < 0 || y < 0 || x > maxX || y > maxY){
            return 0;
        }
        return getChunkPrototype(x, y).getHeight();
    }

    public void setXYHeight(int x, int y, double height){
        if(x < 0 || y < 0 || x > maxX || y > maxY){
            return;
        }
        if(chunkPrototypes2D[x][y] == null){
            chunkPrototypes2D[x][y] = new ChunkPrototype2D(this, height);
        } else {
            getChunkPrototype(x, y).setHeight(height);
        }
    }
}
