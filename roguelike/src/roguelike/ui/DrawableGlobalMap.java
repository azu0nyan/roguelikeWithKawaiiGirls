package roguelike.ui;

import roguelike.map.Chunk;
import roguelike.worldgenerator.ChunkPrototype2D;
import roguelike.worldgenerator.Generator;
import roguelike.worldgenerator.PreGeneratedMap;

import java.awt.*;

import static roguelike.ui.DrawableGlobalMapMode.*;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 14.01.14
 * Time: 6:23
 * To change this template use File | Settings | File Templates.
 */
public class DrawableGlobalMap extends DrawableInterfacePart {

    DrawableGlobalMapMode mode = HEIGHT;
    int xSquareSize;
    int ySquareSize;

    public DrawableGlobalMap(GraphicsUI ui) {
        super(ui);
    }

    @Override
    public void draw(int x, int y, int width, int height){
        super.draw(x, y, width, height);
        int xSize = getUI().game.getMap().getChunkAggregation().getSizeX();
        int ySize = getUI().game.getMap().getChunkAggregation().getSizeY();
        xSquareSize = width / xSize;
        ySquareSize = height / ySize;
        Chunk playerChunk = (getUI().getGame().getPlayer() != null)?(getUI().getGame().getPlayer().getChunk()):null;
        for(int i = 0; i < xSize; i++){
            for(int j = 0; j < ySize; j++){
                ChunkPrototype2D currentChunk = getUI().getGame().getChuckPrototypeAtChunkXY(i, j);
                Color drawingColor = Color.BLACK;
                switch (mode){
                    case HEIGHT:{
                        double chunkHeight = currentChunk.getHeight() / 2;
                        if(chunkHeight > 0 ){
                            drawingColor = new Color(Math.min(255, (int) chunkHeight), Math.min(255, (int) chunkHeight), Math.min(255, (int) chunkHeight));
                        } else if (chunkHeight == 0){
                            drawingColor = Color.ORANGE;
                        } else {
                            drawingColor = Color.BLUE;
                        }
                        break;
                    }
                    case AVERAGE_TEMPERATURE:{
                        //-100,100
                        double temperature = currentChunk.getAverageTemperature();
                        if(temperature > 0){
                            temperature = Math.min(temperature, 100);
                            drawingColor  = new Color((int)(temperature * 255.0 / 100.0), 0, 0);
                        } else {
                            temperature = Math.max(temperature, -100);
                            drawingColor =  new Color(0, 0, (int)(-temperature * 255.0 / 100.0));
                        }
                        break;
                    }
                    case RAINFALL:{
                        break;
                    }
                    case DRAINAGE:{
                        break;
                    }
                    case MAGIC_CAPACITY:{
                        break;
                    }
                    case BIOMES:{
                        break;
                    }
                }

                if(playerChunk != null && playerChunk.getCordinates().getX() == i && playerChunk.getCordinates().getY() == j){
                    drawingColor = Color.RED;
                }
                fillSquare(i, j, drawingColor);
            }
        }
    }

    public void nextMode(){
        mode = mode.getNext(mode);
    }

    public void fillSquare(int i, int j, Color color){
        getUI().fillRect(i * xSquareSize, j * ySquareSize, xSquareSize, ySquareSize, color);
    }
}
