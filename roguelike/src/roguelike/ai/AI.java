package roguelike.ai;

import roguelike.Game;
import roguelike.Tools;
import roguelike.creature.Creature;
import roguelike.event.Event;

import java.util.List;
/**
 * это базовый класс для всего AI монстров, т.к. для каждого монстра
 * создается отдельный его экземпляр, то наследники могут хранить какие-то
 * данные, в том числе и другие экземпляры этого класса
 * @author azu
 */
@Deprecated //TODO заменить на state machine
public class AI {

    Game game;
    Creature creature;
    
    public AI(Creature c,Game game_){
        creature = c;
        game = game_;
    }  

    public AI() {
        
    }

    public void doActions(int deltaTime){
        
    } 
    
    public void event(Event event){
        
    }
    //Служебные функции
    /**
     * чем меньше счет тем вероятнее нападение
     * гораздо больший приоритет отдается отношению с фракцией нежели расстоянию
     */
    public double getAgroScore(Creature cr){

        int relationship = creature.getFraction().getRelationship(cr.getFraction()); 
        double distance = Tools.getDistance(creature.getCordinates(), cr.getCordinates());
        double score = (relationship * 4) / distance;
        return score;
    }

    public Creature getEnemyToAttack(List<Creature> creatures){
        if(creatures.size() == 0){
            return null;
        }
        Creature c = creatures.get(0);
        for(Creature cr : creatures){
            if(getAgroScore(c) > getAgroScore(cr)){
                c = cr;
            }
        }
        return c;
    }

    
}
