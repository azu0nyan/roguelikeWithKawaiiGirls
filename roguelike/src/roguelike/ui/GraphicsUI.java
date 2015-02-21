package roguelike.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import roguelike.*;

import roguelike.DAO.prototypes.TileType;
import roguelike.creature.Creature;
import roguelike.item.Item;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.ObjectType;
import roguelike.objectsAndProperties.properties.StatsObjectPropertyInterface;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;
import roguelike.stats.Stats;
import roguelike.worldobject.WorldObject;
import roguelike.worldobject.WorldObjectPropertyType;

import static roguelike.ui.GameInterfaceState.*;

/**
 *
 * @author nik
 */
public class GraphicsUI extends JFrame{

    public Game game;
    private Font font;
    public int charInterval;

    public int fps = 0;

    public Graphics g;
    private InventoryMenu inventoryMenu;
    private BufferedImage image;

    public GameInterfaceState interfaceState;
    private TileCordinates viewDeltaCord;

    public DrawableObjectsList pickupableItems;
    public DrawableObjectsList listeners;

    public DrawableGlobalMap drawableGlobalMap;

    public GraphicsUI(Game game) throws InterruptedException {
        super();
        //Frame settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        createBufferStrategy(2);

        //Game class setup
        this.game = game;
        //загрузка графики TODO сделать нормальную
        /*try {
            image = ImageIO.read(new File("data//playerTile.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //Настройка отрисовки/шрифта/e.t.c
        font = new Font("Lucida Console", Font.BOLD, 16);
        charInterval = 16;
        try {
            KeyListenerLoader.loadAndSetKeyBindingsFromDB("RogueLikeDB.s3db", "KeyBindings", this);
        } catch (Exception ex) {
            Logger.getLogger(GraphicsUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Создание интерфейса
        interfaceState = GLOBAL_MAP;

        drawableGlobalMap = new DrawableGlobalMap(this);
        //Drawing loop
        new CordinatesEnterFrame(game, this);
        loop();
    }

    public void gameStarted() throws InterruptedException {

        viewDeltaCord = new TileCordinates(0, 0, 0);

        pickupableItems = new DrawableObjectsList(this);
        pickupableItems.setList(game.getPlayer().getPickUpableItems());
        pickupableItems.setName("pick up");

        listeners = new DrawableObjectsList(this);
        listeners.setList(game.getPlayer().getLinkedCordinates().getChunk().getEventListeners());
        listeners.setName("listeners");

        inventoryMenu = new InventoryMenu(this);



    }
    private void loop() throws InterruptedException {
        //ptime = System.currentTimeMillis();
        int globalFramesCount = 0;
        int fpsCounter = 0;
        long sumTime = 0;
        long prevTime = System.currentTimeMillis();
        while (true) {
            globalFramesCount++;
            fpsCounter++;
            //resourse loading
            //fps cap
            draw();

            //FpsCalc
            long time = System.currentTimeMillis();
            sumTime += time - prevTime;
            prevTime = time;
            long remainingTime  = (1000 / UICONFIGURATION.FPS_CAP) * fpsCounter - sumTime;
            if(remainingTime > 0){
                Thread.currentThread().sleep(remainingTime);
            }
            if (sumTime >= 1000) {
                fps = fpsCounter;
                if (fps != 0 && UICONFIGURATION.LOG_FPS) {
                    System.out.println("fps:" + String.valueOf(fps)
                            + " av. drawing time:" + String.valueOf(sumTime / fps)
                            + "ms");
                }
                fpsCounter = 0;
                sumTime = 0;

            }
            //end of fps calc
        }
    }

    private void draw() {
        BufferStrategy bs = getBufferStrategy();
        //Graphics g = null;
        try {
            //g.setFont();
            g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, UICONFIGURATION.WIDTH, UICONFIGURATION.HEIGHT);
            g.setFont(font);
            switch (interfaceState){
                case GAME:{
                    TileCordinates tempDrawingCord = new TileCordinates(game.getPlayer().getCordinates());
                    tempDrawingCord.add(viewDeltaCord);
                    drawMap(0, 0, 768, 768, tempDrawingCord);
                    drawPlayerStatus(768, 0, 256, 128, game.getPlayer());

                    pickupableItems.setList(game.getPlayer().getPickUpableItems());
                    pickupableItems.draw(768, 128, 256, 256, 10);

                    listeners.setList(game.getPlayer().getLinkedCordinates().getChunk().getEventListeners());
                    listeners.draw(1024, 0, 512, 512, 10);
                    drawString("view delta" + viewDeltaCord.toString(), 1024, 512, Color.ORANGE);
                    //target status
                    DrawableBody tempDrawableBody = new DrawableBody(this);
                    if(game.getPlayer().getTarget() != null){
                        tempDrawableBody.setBody(game.getPlayer().getTarget().getBody());
                        tempDrawableBody.draw(768, 256, 256, 256);
                    }
                    break;
                }
                case INVENTORY:{
                    inventoryMenu.draw();
                    break;
                }
                case BODY:{
                    DrawableBody tempDrawableBody = new DrawableBody(this);
                    tempDrawableBody.setBody(game.getPlayer().getBody());
                    tempDrawableBody.draw(0, 0, 1024, 600);
                    break;
                }
                case GLOBAL_MAP:{
                    drawableGlobalMap.draw(0,0, 1024,1024);
                    break;
                }
            }

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, charInterval * 3, charInterval);
            drawString(String.valueOf(fps), 0, 0, Color.ORANGE);
            g.setColor(Color.GRAY);
            g.fillRect(charInterval * 3, 0, charInterval * 15, charInterval);
            drawString(game.getGlobalDate().toString() + Tools.getSeparatedStringList(game.getGlobalDate().getSeasons(), "[", "]", ","), charInterval * 3, 0, Color.ORANGE);
        } finally {
            g.dispose();
        }
        bs.show();
    }

    //Рисование интерфейса

    /**
     * если тайл в drawTCord прохидим то рисует его
     *  если на тайле есть существа то рисутет их
     *  если на тайле есть объекты рисует их
     *  если на тайле есть предметы рисует их
     *
     * если это тайл-пропасть
     *  рисует символ нижнего тайла с яркостью зависящией глубины 1-5
     *  рисует " " при глубине больше 5
     *
     * если это тайл-гора
     *  рисует размер относительной высоты 1-5
     *  рисует белый квадрат, если высота больше 5
     */
    public void drawTile(int x, int y, int drawingTile, TileCordinates drawTCord) {
        char drawingChar = ' ';
        Color drawingColor = Color.BLACK;
        int drawingAlpha = 255;
        Color drawingCharColor = Color.WHITE;

        if(game.isWalkableAt(drawTCord)){  //Тайл проxодим, рисуем его
            drawingChar = ' ';
            drawingColor = Color.GREEN;//todo getTileType.getColor; getTileType.getImage
            drawingCharColor = Color.WHITE;

            List<LocatedObjectWithPropertiesInterface> creatures = game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.CREATURE);
            List<LocatedObjectWithPropertiesInterface> worldObjects = game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.WORLDOBJECT);
            List<LocatedObjectWithPropertiesInterface> items = game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.ITEM);

            if (creatures.size() > 0) {
                Creature drawingCreature = (Creature)creatures.get(0);
                if (drawingCreature.isDead()) {
                    drawingChar = drawingCreature.getSymbol();
                    drawingCharColor = Color.GRAY;
                } else if (drawingCreature == game.getPlayer().getTarget()) {
                    drawingColor = Color.YELLOW;
                    drawingChar = drawingCreature.getSymbol();
                    drawingCharColor = Color.RED;
                } else {
                    drawingChar = drawingCreature.getSymbol();
                    drawingCharColor = Color.RED;
                }
            } else if (worldObjects.size() != 0){
                WorldObject wo = (WorldObject)worldObjects.get(0);
                drawingColor = Color.BLACK;
                drawingChar = wo.getSymbol();
            } else if (items.size() != 0) {
                Item item = null;
                for(LocatedObjectWithPropertiesInterface i : items){
                    if(!((Item)i).hasOwner()){
                        item = (Item)i;
                        drawChar(item.getSymbol(), x, y, Color.BLUE);
                        drawingChar = item.getSymbol();
                        drawingColor = Color.BLUE;
                        break;
                    }
                }
            }

        } else if (drawingTile == 0){
            int depth = game.getRelativeDepth(drawTCord);
            if(depth  <= 5){
                TileType depthTileType = game.getTileType(game.getTileAt(new TileCordinates(drawTCord.getX(), drawTCord.getY(), drawTCord.getZ() - depth)));
                drawingChar = depthTileType.getSymbol();
                if(depthTileType.id == CONFIGURATION.waterID){
                    drawingColor = Color.BLUE;
                }
                drawingAlpha = Math.min(255 - (depth * 255) / 5, 255);
            }
        } else if (drawingTile == -1){
            fillRect(x, y, charInterval, charInterval, Color.black);
            drawChar('N' ,x ,y, Color.WHITE);
        } else {
            int height = game.getRelativeHeight(drawTCord);
            if(height <= 5){
                drawingChar = String.valueOf(height).charAt(0);
            } else {
                drawingColor = Color.WHITE;
            }
        }

        fillRect(x, y, charInterval, charInterval, drawingColor);
        drawChar(drawingChar, x, y, drawingCharColor, drawingAlpha);

        /*if (drawingTile != -1) {
            //Подготовка рисуемого тайла
            TileType drawingTileType;
            if (drawingTile == 0) {
                //рисуем тайлы по которым можно перемещатся
                //TODO ввести отображение глубины
                if (game.isWalkableAt(drawTCord)) {
                    drawingTileType = game.getTileType(1);
                } else {
                    //отображение лестницы на уровень ниже
                    TileCordinates newDrawingCord = new TileCordinates(drawTCord);
                    newDrawingCord.setZ(newDrawingCord.getZ() - 1);
                    if (game.getTileType(game.getTileAt(newDrawingCord)).isLadder) {
                        drawingTileType = game.getTileType(5);
                        //"несуществующий" тайл опускающейся лестницы
                    } else {
                        drawingTileType = game.getTileType(drawingTile);
                    }
                }
            } else {
                drawingTileType = game.getTileType(drawingTile);
            }

            //TEMP
            if(drawingTile == 2 || !game.isWalkableAt(drawTCord)){
                fillRect(x, y, charInterval, charInterval, Color.black);
            } else {
                fillRect(x, y, charInterval, charInterval, Color.green);
            }
            drawChar(drawingTileType.symbol,x ,y, Color.WHITE);
            ///TileType.tileImage

            if (game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.CREATURE).size() > 0) {
                Creature drawingCreature = (Creature)game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.CREATURE).get(0);
                if (drawingCreature.isDead()) {
                    drawChar(drawingCreature.getSymbol(), x, y, Color.GRAY);
                } else if (drawingCreature == game.getPlayer().getTarget()) {
                    fillRect(x, y, charInterval, charInterval, Color.YELLOW);
                    drawChar(drawingCreature.getSymbol(), x, y, Color.RED);
                } else {
                    drawChar(drawingCreature.getSymbol(), x, y, Color.RED);
                }
            } else if (game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.WORLDOBJECT).size() != 0){
                WorldObject wo = (WorldObject)game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.WORLDOBJECT).get(0);
                drawChar(wo.getSymbol(), x, y, Color.BLACK);
            } else if (game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.ITEM).size() != 0) {
                Item item = null;
                for(LocatedObjectWithPropertiesInterface i : game.getChuckAt(drawTCord).getObjectsInTile(drawTCord, ObjectType.ITEM)){
                    if(!((Item)i).hasOwner()){
                        item = (Item)i;
                        drawChar(item.getSymbol(), x, y, Color.BLUE);
                        break;
                    }
                }
            }
        } else {
            //TileType t = game.getTileType(2);
            fillRect(x, y, charInterval, charInterval, Color.black);
            drawChar('N' ,x ,y, Color.WHITE);
        }  */
    }

    public void drawMap(int x, int y, int width, int height, TileCordinates tCord) {
        int left = x;
        int right = x + width;
        int top = y;
        int bottom = y + height;//Надеемся что кординаты кратны charInterval
        int xTilesCount = width / charInterval;
        int yTilesCount = height / charInterval;
        int dx = xTilesCount / 2;//Расстояние от центра до края карты в тайлах
        int dy = yTilesCount / 2;
        for(int i = 0; i < xTilesCount; i++ ){
            for(int j = 0; j < yTilesCount; j++){
                int drawAtX = tCord.getX() - dx + i;
                int drawAtY = tCord.getY() - dy + j;
                TileCordinates drawTCord = new TileCordinates(drawAtX, drawAtY, tCord.getZ());
                int drawingTile = game.getMap().getTileAt(drawTCord);
                drawTile(i * charInterval, j * charInterval, drawingTile, drawTCord);
            }

        }
    }
    /**
     * рисует окошко статуса существа
     */
    public void drawPlayerStatus(int x, int y, int width, int height, Creature creature) {

        TileCordinates creatureCord = new TileCordinates(creature.getCordinates());
        fillRect(x, y,  width, height,Color.BLACK);
        int left = x + charInterval / 2;
        int top = y + charInterval / 2;
        drawString("x:" + creatureCord.getX() + "-"  + creature.getChunk().getCordinates().getX(), left, top, Color.WHITE);
        top += charInterval;
        drawString("y:" + creatureCord.getY() + "-"  + creature.getChunk().getCordinates().getY(), left, top, Color.WHITE);
        top += charInterval;
        drawString("z:" + creatureCord.getZ() + "-"  + creature.getChunk().getCordinates().getZ(), left, top, Color.WHITE);
        top += charInterval;
        drawString("hp:" + String.valueOf(game.getPlayer().getHP()) +
                "/" + String.valueOf(game.getPlayer().getMaxHP()), left, top, Color.WHITE);
        top += charInterval;
        drawString("mana:" + String.valueOf(game.getPlayer().getMP()) +
                "/" + String.valueOf(game.getPlayer().getMaxMP()), left, top, Color.WHITE);
        top += charInterval;
        drawString("weight:" + String.valueOf(game.getPlayer().getItemsWeight()),
                left, top, Color.WHITE);

    }

    public void drawCreatureStats(int x, int y, int width, int height, Creature creature) {
        fillRect(x, y, width, height,Color.BLACK);
        int left = x + charInterval / 2;
        int top = y + charInterval / 2;
        drawString("HP:" + String.valueOf(creature.getHP()) + "/" + String.valueOf(creature.getMaxHP()), left, top, Color.WHITE);
        top += charInterval;
        drawString("MP:" + String.valueOf(creature.getMP()) + "/" + String.valueOf(creature.getMaxMP()), left, top, Color.WHITE);
        top += charInterval;
        /*drawString("Armor:" + String.valueOf(creature.getArmor()), left, top, Color.WHITE);
        top += charInterval;*/
        drawString("Stamina:" + String.valueOf(creature.getStamina()), left, top, Color.WHITE);
        top += charInterval;
        drawString("Intellect:" + String.valueOf(creature.getIntellect()), left, top, Color.WHITE);
        top += charInterval;
        drawString("Agility:" + String.valueOf(creature.getAgility()), left, top, Color.WHITE);
        top += charInterval;
        drawString("Strength:" + String.valueOf(creature.getStrength()), left, top, Color.WHITE);
        top += charInterval;
    }

    public void drawItemStats(int x, int y, int width, int height, Item item) {
        fillRect(x, y, width, height, Color.BLACK);
        int left = x + charInterval / 2;
        int top = y + charInterval / 2;

        if (item == null) {
            drawString("NULL", left, top, Color.WHITE);
            top += charInterval;
            drawString("NULL", left, top, Color.WHITE);
            top += charInterval;
            return;
        }
        drawString(item.getName(), left, top, Color.WHITE);
        top += charInterval;
        drawString(item.getDescription(), left, top, Color.WHITE);
        top += charInterval;
        // A.class.isAssignableFrom(B.class) == true if B extends A  A a = (A)B
        if (item.getPropertyByType(WorldObjectPropertyType.ITEM_EQUIPMENT) != null) {
            Stats stats = ((StatsObjectPropertyInterface) item.getPropertyByType(WorldObjectPropertyType.ITEM_EQUIPMENT)).getStats();
            drawString("Adds:" , left, top, Color.WHITE);
            top += charInterval;
            if (stats.hitPoints != 0) {
                drawString(String.valueOf(stats.hitPoints) + " HP" , left, top, Color.WHITE);
                top += charInterval;
            }
            if (stats.manaPoints != 0) {
                drawString(String.valueOf(stats.manaPoints) + " MP" , left, top, Color.WHITE);
                top += charInterval;
            }
            /*if (Item.getStats().armor != 0) {
                drawString(String.valueOf(Item.getStats().armor) + " Armor" , left, top, Color.WHITE);
                top += charInterval;
            }*/
            if (stats.stamina != 0) {
                drawString(String.valueOf(stats.stamina) + " Stamina" , left, top, Color.WHITE);
                top += charInterval;
            }
            if (stats.intellect != 0) {
                drawString(String.valueOf(stats.intellect) + " Intellect" , left, top, Color.WHITE);
                top += charInterval;
            }
            if (stats.agility != 0) {
                drawString(String.valueOf(stats.agility) + " Agility" , left, top, Color.WHITE);
                top += charInterval;
            }
            if (stats.strength != 0) {
                drawString(String.valueOf(stats.strength) + " Strength" , left, top, Color.WHITE);
                top += charInterval;
            }

        }

        if(item.getPropertyByType(WorldObjectPropertyType.WEAPON) != null){
            WeaponPropertyInterface weapon = (WeaponPropertyInterface) item.getPropertyByType(WorldObjectPropertyType.WEAPON);
            if (weapon.getDamage() != null && weapon.getDamage().getSumDamage() != 0) {
                drawString("Deals " + String.valueOf(weapon.getDamage()) + " damage per hit" , left, top, Color.WHITE);
                top += charInterval;
            }
        }

    }

    //Рисование/примитивы
    public void fillRect(int x,int y, int dx, int dy, Color color){
        g.setColor(color);
        g.fillRect(x, y, dx, dy);
    }
    public void drawChar(char ch, int x, int y, Color color, int alpha){
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
        g.drawString(String.valueOf(ch), x, y + charInterval);
    }

    public void drawChar(char ch, int x, int y, Color color){
        drawChar(ch, x, y, color, 255);
    }
    public void drawString(String str, int x, int y, Color color){
        g.setColor(color);
        g.drawString(str, x, y + charInterval);
    }
    public void drawImageAt(String name, int x, int y){
        //TODO ImagesManager.getImage
    }
    ///
    public void setViewDelta(TileCordinates cord){
        viewDeltaCord = new TileCordinates(cord);
    }
    public void increaseZViewCord(){
        viewDeltaCord.setZ(viewDeltaCord.getZ() + 1);
    }
    public void decreaseZViewCord(){
        viewDeltaCord.setZ(viewDeltaCord.getZ() - 1);
    }
    public InventoryMenu getInventoryMenu(){
        return inventoryMenu;
    }

    public Game getGame() {
        return game;
    }

}
