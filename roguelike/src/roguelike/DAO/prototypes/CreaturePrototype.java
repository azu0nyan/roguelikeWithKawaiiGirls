package roguelike.DAO.prototypes;

import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;
import roguelike.DAO.HasId;
import roguelike.Game;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.ai.AIAttackEnemyIfSee;
import roguelike.ai.AIRandomMovement;
import roguelike.creature.Body;
import roguelike.creature.Creature;
import roguelike.creature.CreatureHumanoid;

/**
 *
 * @author azu
 */
@PersistentEntity(
        sourceDomain = "CREATURES",
        targetDomain = "CREATURES",
        primaryKey = "id"
)
public class CreaturePrototype implements HasId {
    @PersistentField
    public int id;

    @PersistentField
    public String name;

    @PersistentField
    public int body;

    @PersistentField
    public double size = 0.7;

    @PersistentField
    public String fraction;

    @PersistentField
    public String aiType;

    @PersistentField
    public String symbol;

    @PersistentField
    public String color;

    @PersistentField
    public boolean isHumanoid;

    @PersistentField
    public boolean isDead;

    @PersistentField
    public String stats;

    public Creature createInstance(Game game, TileCordinatesLinkedToChunk tCord){
        Creature c;
        if(isHumanoid){
            c = new CreatureHumanoid(tCord);
            //TODO замнить на CreatureHumanoid или что-то похожее
        } else {
            c = new Creature(tCord);
        }
        c.setType(id);
        c.setName(name);
        c.setBaseStats(game.getStats(stats));
        c.setFraction(game.getFraction(fraction));
        c.setIsDead(isDead);
        c.setSymbol("".equals(symbol) ? ' ' : symbol.charAt(0));
        c.setSize(size);
        Body b = game.getBodiesLoader().getBodyById(body, c);
        if(b != null){
            c.setBody(b);
        }
        //TODO загружать класс AI из таблицы
        if(aiType.equalsIgnoreCase("AIAttackEnemyIfSee")){
            c.setAI(new AIAttackEnemyIfSee(c, game));
        } else if(aiType.equalsIgnoreCase("AIRandomMovement")){
            c.setAI(new AIRandomMovement(c, game));
        }//else id ....
        return c;
    }

    @Override
    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getAiType() {
        return aiType;
    }

    public void setAiType(String aiType) {
        this.aiType = aiType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public boolean isHumanoid() {
        return isHumanoid;
    }

    public void setHumanoid(boolean humanoid) {
        isHumanoid = humanoid;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
    @Override
    public String toString(){
        return id + ":" + name;
    }
}
