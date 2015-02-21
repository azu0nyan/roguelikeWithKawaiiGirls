package roguelike.event.listener;

import roguelike.TileCordinatesLinkedToChunk;
import roguelike.event.EventInterface;
import roguelike.event.EventType;
import roguelike.event.listener.conditions.Condition;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 16.06.13
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConditionLocatedEventListener extends LocatedEventListener{

    Condition condition;

    public ConditionLocatedEventListener(EventType type, TileCordinatesLinkedToChunk tCord, int tilesRadius, Condition condition) {
        super(type, tCord, tilesRadius);
        this.condition = condition;
    }

    @Override
    public final String processEvent(EventInterface event) {
        if(condition.correct()){
            return processEventWithoutConditions(event);
        }
        return "[INCORRECT CONDITION]" + toString() + " event:" + toString();
    }

    public abstract String processEventWithoutConditions(EventInterface event);



}
