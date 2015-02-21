package roguelike;

import roguelike.DAO.*;
import roguelike.DAO.prototypes.TileType;
import roguelike.creature.Body;
import roguelike.creature.Creature;
import roguelike.creature.CreaturePlayer;
import roguelike.creature.MovementType;
import roguelike.datetime.GlobalDate;
import roguelike.event.Event;
import roguelike.map.Chunk;
import roguelike.map.ChunkAggregation;
import roguelike.map.Map;
import roguelike.map.MapGeneratorThread;
import roguelike.spell.Effect;
import roguelike.spell.EffectsFactory;
import roguelike.stats.DamageValue;
import roguelike.stats.DamagesLoader;
import roguelike.stats.Stats;
import roguelike.worldgenerator.ChunkPrototype2D;
import roguelike.worldgenerator.Generator;
import roguelike.worldgenerator.PreGeneratedMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author azu
 */
public class Game {

    private GlobalDate date;

    private TileTypesLoader tileTypesLoader;
    private StatsLoader statsLoader;
    private MaterialsLoader materialsLoader;
    private DamagesLoader damagesLoader;
    private ItemPrototypesLoader itemPrototypesLoader;
    private BodyPartsLoader bodyPartsLoader;
    private BodiesLoader bodiesLoader;
    private FractionsLoader fractionsLoader;
    private LootListLoader lootListLoader;
    private WorldObjectsLoader worldObjectsLoader;
    private CreaturesManager creaturesManager;
    ///game loop
    //private EventsManager eventsManager;
    ThreadGameLoop gameLoop;
    public boolean isExit = false;
    public int gameMode = 0;
    long deltaTime = 0;
    private long lastUpdateTime = 0;
    private CreaturePlayer player;
    private int playerMoveDirection;//0 - none 1 - left 2 - right 3 - top 4 - bottom 
    private Map map;
    private MapGeneratorThread mapGeneratorThread;
    private PreGeneratedMap preGeneratedMap;
    private int mapSizeX = 513;
    private int mapSizeY = 513;



    public Game() {
    }

    /**
     * Инициальзация и загрузка ресурсов
     */
    void init() throws Exception {
        date = new GlobalDate(0, 0, 1, 1, 1);

        loadFromDB();

        generateMap();

        System.out.println("Initialization completed");
    }

    public void spawnPlayerAndStart(int chunkX, int chunkY) throws Exception {
        spawnPlayer(chunkX, chunkY);
        if (gameMode == 0) {
            gameLoop = new ThreadGameLoop(this);
            gameLoop.start();
        }
    }


    public void spawnPlayer(int chunkX, int chunkY) throws Exception {

        System.out.println("Player spawning");
        TileCordinates tempCord = new ChunkCordinates(chunkX, chunkY, 0).toTileCordinates();
        System.out.println("Temp cords:" + tempCord);
        TileCordinatesLinkedToChunk playerCord = new TileCordinatesLinkedToChunk(getChuckAt(tempCord), tempCord, null);
        mapGeneratorThread.setTileCordLinked(playerCord);
        mapGeneratorThread.process(tempCord);
        TileCordinates droppedTCord = dropUpToGround(tempCord);
        System.out.println("Real player cords:" + droppedTCord);
        player = new CreaturePlayer(new TileCordinatesLinkedToChunk(getChuckAt(droppedTCord), droppedTCord, null));
        mapGeneratorThread.setTileCordLinked(player.getLinkedCordinates());

        Body body = bodiesLoader.getBodyById(1, player);
        player.setBody(body);
        player.setFraction(fractionsLoader.getFraction("defaultPlayerFraction"));
        Stats playerStats = statsLoader.getStats("basePlayerStats");
        if(CONFIGURATION.debug){
            playerStats.speed = 1;
        }
        player.setBaseStats(playerStats);
        ///Regeneration
        Effect effect = EffectsFactory.getHealAllTimeEffect(1);
        player.applyEffect(effect);
        creaturesManager.addCreature(player);//TODO заменить на spawnCreatureAt
    }

    public void generateMap() {
        System.out.println("Map generation");
        Generator g = new Generator(mapSizeX, mapSizeY);
        g.generate();
        preGeneratedMap = g.getPreGeneratedMap();
        mapGeneratorThread = new MapGeneratorThread(this, preGeneratedMap);
        map = new Map(this);
        mapGeneratorThread.generateFirstTime();
        mapGeneratorThread.start();
    }

    public void loadFromDB() {
        tileTypesLoader = new TileTypesLoader();
        fractionsLoader = new FractionsLoader();
        statsLoader = new StatsLoader();
        materialsLoader = new MaterialsLoader(this);
        damagesLoader = new DamagesLoader();
        itemPrototypesLoader = new ItemPrototypesLoader(this);
        lootListLoader = new LootListLoader(this);
        worldObjectsLoader = new WorldObjectsLoader(this);
        bodyPartsLoader = new BodyPartsLoader();
        bodiesLoader = new BodiesLoader();

        creaturesManager = new CreaturesManager(this);

        System.out.println("Tiles loading");
        try {
            tileTypesLoader.loadTileTypesFromDB("RogueLikeDB.s3db");
        } catch (Exception ex) {
            System.out.println("ERROR Tiles not loaded " + ex.getMessage());
            ex.printStackTrace();
        }


        ///fractions
        System.out.println("Fractions loading");
        try {
            fractionsLoader.loaFractionsFromDB("RogueLikeDB.s3db", "Fractions");
        } catch (Exception ex) {
            System.out.println("ERROR Fractions not loaded " + ex.getMessage());
            ex.printStackTrace();
        }

        ///fractions
        System.out.println("Materials loading");
        try {
            materialsLoader.loadMaterialsFromDB("RogueLikeDB.s3db");
        } catch (Exception ex) {
            System.out.println("ERROR materials not loaded " + ex.getMessage());
            ex.printStackTrace();
        }
        //stats
        System.out.println("Stats loading");
        try {
            statsLoader.loadStatsFromDB("RogueLikeDB.s3db", "Stats");
        } catch (Exception ex) {
            System.out.println("ERROR Stats not loaded " + ex.getMessage());
            ex.printStackTrace();
        }
        //damages
        System.out.println("Damages loading");
        try {
            damagesLoader.loadDamagesFromDB("RogueLikeDB.s3db", "Damages");
        } catch (Exception ex) {
            System.out.println("ERROR Damages not loaded " + ex.getMessage());
            ex.printStackTrace();
        }
        //items
        System.out.println("Items loading");
        try {
            itemPrototypesLoader.loadItemPrototypeFromDB("RogueLikeDB.s3db", "Items");
        } catch (Exception ex) {
            System.out.println("ERROR Items not loaded " + ex.getMessage());
            ex.printStackTrace();
        }
        //lootLists
        System.out.println("LootList loading");
        try {
            lootListLoader.loadLootListsFromDB("RogueLikeDB.s3db");
        } catch (Exception ex) {
            System.out.println("ERROR loot list not loaded");
            ex.printStackTrace();
        }

        //world objects
        System.out.println("world objects loading");
        try {
            worldObjectsLoader.loadWorldObjectPrototypesFromDB("RogueLikeDB.s3db", "WorldObjects");
        } catch (Exception ex) {
            System.out.println("ERROR World objects not loaded " + ex.getMessage());
            ex.printStackTrace();
        }

        //monsters
        System.out.println("BodyParts loading");
        try {
            bodyPartsLoader.loadBodyPartsTypesFromDB("RogueLikeDB.s3db");
        } catch (Exception ex) {
            System.out.println("ERROR Body parts not loaded " + ex.getMessage());

        }
        System.out.println("Bodies loading");
        try {
            bodiesLoader.loadBodiesFromDB("RogueLikeDB.s3db");
        } catch (Exception ex) {
            System.out.println("ERROR Bodies not loaded " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("Creatures loading");
        try {
            creaturesManager.loadCreatureTypesFromDB("RogueLikeDB.s3db", "Creatures");
        } catch (Exception ex) {
            System.out.println("ERROR Creatures not loaded " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * обновление всех существ и объектов, вызывается каждую итерацию
     * главного игрового цикла
     */
    public void update(long deltaTime) {

        //update all
        //movePlayer();//player movement
        //testCreature.update();
        date.update(deltaTime);
        creaturesManager.updateAllCreatures((int) deltaTime);
        mapGeneratorThread.update();//пока ничего не делает

    }

    public CreaturePlayer getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    /**
     * Получить тайл находящийся в tCord
     */
    public int getTileAt(TileCordinates tCord) {
        return map.getTileAt(tCord);
    }

    public Chunk getChuckAt(TileCordinates tCord) {
        return map.getChunkAt(tCord);
    }

    public DamageValue getDamage(String name) {
        return damagesLoader.getDamageByName(name);
    }

    public Stats getStats(String name) {
        return statsLoader.getStats(name);
    }

    public Fraction getFraction(String name) {
        return fractionsLoader.getFraction(name);
    }

    /**
     * Получить класс типа тайла
     */
    public TileType getTileType(int id_) {
        return tileTypesLoader.getTileType(id_);
    }

    /**
     * является ли тайл в tCord проходимым
     */
    public boolean isWalkableAt(TileCordinates tCord) {
        int t = getTileAt(tCord);
        if (t == -1) {
            return false;
        }
        if (getTileType(t).isWalkable) {
            if (t != 0) {
                return true;
            } else {
                TileCordinates cord = new TileCordinates(tCord);
                cord.setZ(cord.getZ() - 1);
                if (getTileType(getTileAt(cord)).isWalkableOver) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Находятся ли 2 точки на линии видимости
     */
    public boolean isInLOS(TileCordinates start, TileCordinates end) {

        //TODO write this function
        return true;
    }

    /**
     * находится ли тайл в радиусе видимости существа
     */
    public boolean canSeeTile(Creature creature, TileCordinates tileCord) {

        if (isInLOS(creature.getCordinates(), tileCord)
                && Tools.getDistance(creature.getCordinates(), tileCord) < 7) {
            //getDistanse<creature.vievDistance
            return true;
        }
        return false;
    }

    /**
     * найти сухопутный путь мнжду двумя ьайлами
     */
    public Path findPath(TileCordinates start, TileCordinates end) {

        return map.findPath(start, end);
    }

    /**
     * можели одно существо атаковать другое с текущим оружием
     */
    public boolean canAttack(Creature attCr, Creature deffCr) {

        if (Tools.getDistance(attCr.getCordinates(), deffCr.getCordinates()) < 1.1) {
            return true;
        }
        return false;
    }

    public ArrayList<Creature> getVisibleCreatures(Creature c) {
        return creaturesManager.getVisibleCreatures(c);
    }

    public LinkedList<Creature> getVisibleEnemies(Creature c) {
        LinkedList<Creature> enemies = new LinkedList<Creature>();
        for (Creature creature : getVisibleCreatures(c)) {
            if (c.isEnemy(creature)) {
                enemies.add(creature);
            }
        }
        return enemies;
    }

    public void movePlayerByDirection(int m) {
        getPlayer().setMovementDirection(m);
        getPlayer().playerMove();
    }

    public static TileCordinates getTileCordinatesByMovementDirection(Creature creature) {
        TileCordinates endCord = new TileCordinates(creature.getCordinates());
        switch (creature.getMovementDirection()) {
            case 1: {
                endCord.setX(endCord.getX() - 1);
                break;
            }
            case 2: {
                endCord.setX(endCord.getX() + 1);
                break;
            }
            case 3: {
                endCord.setY(endCord.getY() - 1);
                break;
            }
            case 4: {
                endCord.setY(endCord.getY() + 1);
                break;
            }
        }
        return endCord;
    }

    /**
     * Можно ли переместится с одного тайла на другой WARNING функция может
     * менять end
     */
    public boolean noMovementCollisions(TileCordinates start, TileCordinates end, MovementType type) {

        //TODO проверять рассотояние до end
        //TODO учитывать movement type
        if (isWalkableAt(end)) {
            return true;
        } else {
            ////проверка на возможность подъема
            //вверх

            TileCordinates newEndTileCord = new TileCordinates(end.getX(), end.getY(), end.getZ() + 1);
            int upTile = getTileAt(new TileCordinates(start.getX(), start.getY(), start.getZ() + 1));
            //для проверки
            //  #_   _
            //  /#  /# случаев
            if ((getTileAt(newEndTileCord) != -1) && isWalkableAt(newEndTileCord) && getTileType(upTile).isWalkable) {
                end.setZ(end.getZ() + 1);
                return true;
            }

            //вниз
            newEndTileCord = new TileCordinates(end.getX(), end.getY(), end.getZ() - 1);
            int newEndTile = getTileAt(newEndTileCord);
            int endTile = getTileAt(end);
            //для проверки
            //  _#  _
            //  #\  #\ случаев
            if ((newEndTile != -1) && isWalkableAt(newEndTileCord) && getTileType(endTile).isWalkable) {
                end.setZ(end.getZ() - 1);
                return true;
            }
        }
        return false;
    }

    public void sendEvent(Event event) {
        //objects e.t.c.
        creaturesManager.event(event);
    }

    public TileCordinates getRandomCordinates() {
        ChunkAggregation c = map.getChunkAggregation();
        TileCordinates min = c.getCordinates().toTileCordinates();
        TileCordinates max = new TileCordinates(min);
        max.setX(max.getX() + c.getSizeX() * Chunk.chunkSizeX);
        max.setY(max.getY() + c.getSizeY() * Chunk.chunkSizeY);
        max.setZ(max.getZ() + c.getSizeZ() * Chunk.chunkSizeZ);
        int sizeX = max.getX() - min.getX();
        int sizeY = max.getY() - min.getY();
        int sizeZ = max.getZ() - min.getZ();
        TileCordinates tempCord = null;
        Random r = new Random();
        tempCord = new TileCordinates(min.getX() + r.nextInt(sizeX), min.getY() + r.nextInt(sizeY), min.getZ() + r.nextInt(sizeZ));
        return tempCord;
    }

    /**
     * Возвращает кординаты верхнего проходимого тайла
     */
    public TileCordinates dropUpToGround(TileCordinates t) {

        for(int i = map.getMaxHeight(); i > map.getMinHeight(); i--){
            TileCordinates tempCord = new TileCordinates(t.getX(), t.getY(), i);
            if(isWalkableAt(tempCord)){
                return tempCord;
            }

        }
        return null;
    }

    /**
     * @return РАсстояние до воздушного тайла вверх, -1 если такого нет
     */
    public int getRelativeHeight(TileCordinates tCord){
        for(int i = tCord.getZ(); i < getMap().getMaxHeight(); i++){
            if(getTileAt(new TileCordinates(tCord.getX(), tCord.getY(), i)) == 0){
                return  i - tCord.getZ();
            }
        }
        return -1;
    }

    /**
     * @return Расстояние до не воздушного тайла вниз, -1 если такого нет
     */
    public int getRelativeDepth(TileCordinates tCord){
        for(int i = tCord.getZ(); i >= getMap().getMinHeight(); i--){
            if(getTileAt(new TileCordinates(tCord.getX(), tCord.getY(), i)) != 0){
                return tCord.getZ() - i;
            }
        }
        return -1;
    }

    /**
     * получить случайые проходимые кординаты
     */
    public TileCordinates getRandomWalkableCordinates() {
        TileCordinates temp = null;
        while (temp == null) {
            temp = getRandomCordinates();
            temp = dropUpToGround(temp);
        }
        return temp;
    }

    void exit() {
        //TODO save all here
    }

    public CreaturesManager getCreaturesManager() {
        return creaturesManager;
    }

    public TileTypesLoader getTileTypesLoader() {
        return tileTypesLoader;
    }

    public BodiesLoader getBodiesLoader() {
        return bodiesLoader;
    }

    public BodyPartsLoader getBodyPartsLoader() {
        return bodyPartsLoader;
    }

    public GlobalDate getGlobalDate() {
        return date;
    }

    public ItemPrototypesLoader getItemPrototypesLoader() {
        return itemPrototypesLoader;
    }

    public LootListLoader getLootListLoader() {
        return lootListLoader;
    }

    public MaterialsLoader getMaterialsLoader() {
        return materialsLoader;
    }

    public ChunkPrototype2D getChuckPrototypeAtChunkXY(int x, int y) {
        return getMap().getChunkAggregation().getChunk(new ChunkCordinates(x, y, 0)).getProtototype();
    }
}
