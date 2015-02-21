package roguelike.creature;

import roguelike.Game;
import roguelike.TileCordinatesLinkedToChunk;

/**
 *
 * @author nik
 */
public class CreaturePlayer extends CreatureHumanoid {
    //some settings
    Creature target;
    public CreaturePlayer(TileCordinatesLinkedToChunk tCord){
        super(tCord);
        setSymbol('@');
        setName("SomePlayerName");    
        target = null;       
    }

    public void setTarget(Creature cr){
        target = cr;
    }

    public Creature getTarget(){
        return target;
    }

    public void playerMove(){
        actionMove(Game.getTileCordinatesByMovementDirection(this), MovementType.RUN);
    }

}
