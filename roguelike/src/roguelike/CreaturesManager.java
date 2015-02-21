package roguelike;

import java.util.ArrayList;
import java.util.Random;

import roguelike.DAO.CreaturesLoader;
import roguelike.creature.Creature;
import roguelike.event.Event;
import roguelike.map.Chunk;

/**
 *
 * @author nik
 */
public class CreaturesManager {

    private CreaturesLoader creaturesTypesLoader;
    private Game game;
    private ArrayList<Creature> creatures;

    public CreaturesManager(Game game_) {
        game = game_;
        creatures = new ArrayList<Creature>();
        creaturesTypesLoader = new CreaturesLoader();
    }

    public void loadCreatureTypesFromDB(String DBName, String tableName) throws Exception {
        creaturesTypesLoader.loadCreaturesFromDB(DBName);
    }

    public void addCreature(Creature creature) {
        if (creature != null) {
            creatures.add(creature);
        }
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public void spawnRandomCreature() {
        Random r = new Random();
        spawnCreatureAt(game.getRandomWalkableCordinates(), r.nextInt() % 2);
    }

    public void spawnRandomCreatureAt(TileCordinates tCord) {
        Random r = new Random();
        spawnCreatureAt(tCord, r.nextInt() % 2);
    }

    public void spawnCreatureAt(TileCordinates tCord, int type) {
        try {
            Chunk c = game.getChuckAt(tCord);
            TileCordinatesLinkedToChunk linkedCord = new TileCordinatesLinkedToChunk(c, tCord, null);
            Creature creature = creaturesTypesLoader.getCreatureByType(type, linkedCord);
            creatures.add(creature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAllCreatures(int deltaTime) {//TODO сделать отдельный поток обновления существ
        for (Creature creature : creatures) {
            /*if (creature.getMovementDirection() != 0 && creature.canMove()) {
                game.moveCreature(creature);
            }   */
            creature.update(deltaTime);
        }
    }

    public void event(Event event) {
        for (Creature c : creatures) {
            c.event(event);
        }
    }

    public ArrayList<Creature> getVisibleCreatures(Creature creature) {
        ArrayList<Creature> visibleCreatures = new ArrayList<Creature>();
        for (Creature tempCreature : creatures) {
            if (game.canSeeTile(creature, tempCreature.getCordinates())) {
                visibleCreatures.add(tempCreature);
            }
        }
        return visibleCreatures;
    }
}
