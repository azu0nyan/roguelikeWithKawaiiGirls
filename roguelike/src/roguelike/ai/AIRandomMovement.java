package roguelike.ai;

import roguelike.Game;
import roguelike.creature.Creature;



/**
 *
 * @author azu
 */
public class AIRandomMovement extends AI{
    
    private AIGoToXYZ movementAI;
    
    public AIRandomMovement(Creature c, Game game){
        super(c,game);
        movementAI = new AIGoToXYZ(c,game);
    }

    @Override
    public void doActions(int deltaTime){
        if(movementAI.isTargetAchieved || !movementAI.isCorrectCordinates){
            // r = getRandomCordinates()
            //movementAI.setTargetCordinates(r);
            //temp
            movementAI.setTargetCordinates(game.getRandomWalkableCordinates());
            movementAI.doActions(deltaTime);
        } else {
            movementAI.doActions(deltaTime);
        }
    }
    
}
