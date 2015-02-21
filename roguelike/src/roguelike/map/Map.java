package roguelike.map;

import roguelike.*;
import roguelike.creature.MovementType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * Класс карты, хратин в себе карту, а так же сожержит функции для работы с ней
 * например поиск пути
 * @author azu
 */
public class Map {

    private ChunkAggregation chunkAggregation;//пока только 1
    private Chunk playerChunk;
    private Game game;

    public Map(Game game) {
        this.game = game;
    }

    public int getTileAt(TileCordinates tCord) {
        return chunkAggregation.getTileAt(tCord);
    }

    public Chunk getChunkAt(TileCordinates tCord){
        return chunkAggregation.getChunk(tCord);
    }


    public void setPlayerChunk(Chunk chunk){
        playerChunk = chunk;
    }
    ///////////////////////////////
    ///PATH FINDING////////////////
    ///////////////////////////////
    public Path findPath(TileCordinates start, TileCordinates end) {
        /*
         * TODO при генерации карты создания графа связанных чанков и и поиск
         * кртчайшего маршрута по этому графу
         */
        return findPathAStar(start, end);

    }

    private Path findPathAStar(TileCordinates start, TileCordinates end) {
        Comparator<AStarCell> copmparator = new AStarCellComparator();
        PriorityQueue<AStarCell> closedSet = new PriorityQueue<AStarCell>(128, copmparator);
        PriorityQueue<AStarCell> openSet = new PriorityQueue<AStarCell>(128, copmparator);
        AStarCell startCell = new AStarCell(start, null, 0, 0);
        openSet.add(startCell);
        while (openSet.size() > 0) {
            //берем ячейку с наилучшим Cost
            AStarCell tempCell = openSet.poll();
            //добавляем ее в список уже обработанных
            closedSet.add(tempCell);
            if (tempCell.getCordinates().equals(end)) {
                //  System.out.println("Path finding completed");
                Path path = buildPath(tempCell);
                //System.out.println(path.toString());
                return path;//Build path
            }
            //берем список кординат соседей tempCell
            ArrayList<TileCordinates> tempCellNeighbours = getWalkableNeighbours(tempCell.getCordinates());
            for (TileCordinates neighbourTileCord : tempCellNeighbours) {
                AStarCell neighbourCell = new AStarCell(neighbourTileCord);
                if (closedSet.contains(neighbourCell)) {
                    //если ячейка уже обработанна то
                    // обрабатываем следующую                   
                    continue;
                }

                neighbourCell.setPathCost(tempCell.getCost() + 1);//длинна пути до neighbourCell
                neighbourCell.setHeuristicCost(getHeuristicCost(neighbourCell.getCordinates(), end));
                neighbourCell.setCameFromCell(tempCell);
                if (!openSet.contains(neighbourCell)) {
                    openSet.add(neighbourCell);
                } else {
                    //находим вершину с теми же кординатами
                    AStarCell findedCell = new AStarCell();
                    for (AStarCell cell : openSet) {
                        if (cell.equals(neighbourCell)) {
                            findedCell = cell;
                            break;
                        }
                    }
                    if (findedCell.getPathCost() > neighbourCell.getPathCost()) {
                        openSet.remove(findedCell);
                        openSet.add(neighbourCell);
                    }
                }
            }

        }
        // System.out.println("Path finding failed");
        return null;
    }

    private double getHeuristicCost(TileCordinates start, TileCordinates end) {
        return Tools.getDistance(start, end); //10.0 * game.getTileAt(start).getSumCreatureSize()

    }

    private Path buildPath(AStarCell cell) {
        Path path = new Path();
        if (cell == null) {
            return null;
        }
        do {
            path.add(cell.getCordinates());
            cell = cell.getCameFromCell();
        } while (cell.getCameFromCell() != null);
        return path;
    }

    public ArrayList<TileCordinates> getWalkableNeighbours(TileCordinates cord) {
        ArrayList<TileCordinates> neighbours = new ArrayList<TileCordinates>(4);
        TileCordinates tempCord;
        tempCord = new TileCordinates(cord);
        tempCord.setX(cord.getX() - 1);
        if (game.noMovementCollisions(cord, tempCord, MovementType.WALK)) {
            neighbours.add(tempCord);
        }
        tempCord = new TileCordinates(cord);
        tempCord.setX(cord.getX() + 1);
        if (game.noMovementCollisions(cord, tempCord, MovementType.WALK)) {
            neighbours.add(tempCord);
        }
        tempCord = new TileCordinates(cord);
        tempCord.setY(cord.getY() - 1);
        if (game.noMovementCollisions(cord, tempCord, MovementType.WALK)) {
            neighbours.add(tempCord);
        }
        tempCord = new TileCordinates(cord);
        tempCord.setY(cord.getY() + 1);
        if (game.noMovementCollisions(cord, tempCord, MovementType.WALK)) {
            neighbours.add(tempCord);
        }
        return neighbours;
    }

    public ChunkAggregation getChunkAggregation() {
        return chunkAggregation;
    }
    public void setChunkAggregation(ChunkAggregation chunkAggregation){
        this.chunkAggregation = chunkAggregation;
    }

    public int getMaxHeight() {
        return chunkAggregation.getMaxHeight();
    }

    public int getMinHeight(){
        return chunkAggregation.getMinHeight();
    }
}
