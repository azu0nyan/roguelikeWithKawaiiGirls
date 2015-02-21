package roguelike.ai;

import roguelike.Game;
import roguelike.creature.Creature;

/**
 *
 * @author azu
 */
public class AIAttackEnemyIfSee extends AI{
    
    AI idleAI;
    AIGoToXYZ walkAI;
    Creature target;
    
    public AIAttackEnemyIfSee(Creature c, Game game_){
        super(c, game_);
        idleAI = new AIRandomMovement(c, game_);
        walkAI = new AIGoToXYZ(c, game_);
        target = game.getPlayer();
    }
    public AIAttackEnemyIfSee(Creature c, Game game_, AI idleAI_){
        super(c, game_);
        idleAI = idleAI_;
        walkAI = new AIGoToXYZ(c, game);
        target = game.getPlayer();
    }

    
    @Override
    public void doActions(int deltaTime){//TODO как-нибуть работать со временем
        target = getEnemyToAttack(game.getVisibleEnemies(creature));
        if((target != null) && game.canSeeTile(creature, target.getCordinates())){            
            if(creature.canAttack(target)){
                //TODO заменить на флаг attack, вызаов game.attack перенесни в 
                //game.update
                creature.attack(target);
            } else {
                walkAI.setTargetCordinates(target.getCordinates());
                walkAI.doActions(deltaTime);
            }
        } else {
            idleAI.doActions(deltaTime);
        }
    }    
}
