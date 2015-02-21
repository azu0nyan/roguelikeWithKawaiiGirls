package roguelike.ai;
import roguelike.*;
import roguelike.creature.Creature;
import roguelike.creature.MovementType;

/**
 *
 * @author nik
 */
public class AIGoToXYZ extends AI{
    
    Path path;
    TileCordinates target;
    boolean isTargetAchieved;
    boolean isCorrectCordinates;//false если цель не достижима
    TileCordinates nextTile = null;
    
    public AIGoToXYZ(Creature c, Game game){
        super(c, game);
        setTarget(c.getCordinates());
        isTargetAchieved = true;
        isCorrectCordinates = true;
        path = null;
        nextTile = null;
    }

    public AIGoToXYZ(Creature c, Game game, TileCordinates cord){
        super(c, game);
        if(cord != null){
            setTarget(cord);
        } else{
            setTarget(c.getCordinates());
    }
    path = null;
        isTargetAchieved = false;
        isCorrectCordinates = true;
        nextTile = null;
    }

    private void setTarget(TileCordinates target){
        this.target = new TileCordinates(target);
    }

    /**WARNING!!
     * функция сохраняет ссылку на кординаты
     * нужно следить за передаваемым значением
     */
    public void setTargetCordinates(TileCordinates cord){

        setTarget(cord);
        path = null;
        isTargetAchieved = false;
        isCorrectCordinates = true;//по умолчанию считаем цель достижимой 
        nextTile = null;
        
    }

    @Override
    public void doActions(int deltaTime) {//TODO как-нибуть следить за временем
        //проверка на достижение цели
        TileCordinates creatureCord = creature.getCordinates();
        if (creatureCord.same(target)) {
            isTargetAchieved = true;
            return;
        }
        //Строим путь если не построен
        if(path == null){
            path = game.findPath(creatureCord, target);
            if(path == null){
                isCorrectCordinates = false;
                System.out.println("Wrong path cordinates from " + creatureCord.toString() + " to" + target.toString());
                return;
            }
            nextTile = path.getNextPathPoint();
        }
        //перемещаемся
        if(creature.canMove()){
             if(creatureCord.same(nextTile)){
                nextTile = path.getNextPathPoint();
             }
            creature.actionMove(nextTile, MovementType.RUN);
        }

    }
}
