package roguelike.DAO.prototypes;

import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;
import roguelike.DAO.HasId;

/**
 *
 * @author azu
 */
@PersistentEntity(
        sourceDomain = "TIlE_TYPES",
        targetDomain = "TILE_TYPES",
        primaryKey = "id"
)
public class TileType implements HasId {
    @PersistentField
    public int id;

    @PersistentField
    public String name;

    @PersistentField
    public String symbol;

    @PersistentField
    public String color;

    @PersistentField
    public String charColor;

    @PersistentField
    public boolean isWalkable;//можно ли находится в этом тайле

    @PersistentField
    public boolean isWalkableOver;//можно ли ходить сверху по тайлу

    /*@PersistentField
    public boolean isViewed;     */

    @PersistentField
    public boolean isLadder;
    
    public TileType(){
        id = 1;
        symbol = " ";
        isWalkable = true;
        //isViewed = true;
        isLadder = false;
        name = "Some Name";
        
    }

    @Override
    public int getID() {
        return id;
    }
    public String getCharColor() {
        return charColor;
    }

    public void setCharColor(final String charColor) {
        this.charColor = charColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public Character getSymbol() {
        return symbol.charAt(0);
    }

    public void setSymbol(final char symbol) {
        this.symbol = String.valueOf(symbol);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    public void setId(final int id) {
        this.id = id;
    }

    public boolean isIsWalkable() {
        return isWalkable;
    }

    public void setIsWalkable(final boolean isWalkable) {
        this.isWalkable = isWalkable;
    }

    public boolean isIsWalkableOver() {
        return isWalkableOver;
    }

    public void setIsWalkableOver(final boolean isWalkableOver) {
        this.isWalkableOver = isWalkableOver;
    }

    public boolean isIsLadder() {
        return isLadder;
    }

    public void setIsLadder(final boolean isLadder) {
        this.isLadder = isLadder;
    }

    public String toString(){
        return name;
    }
}
