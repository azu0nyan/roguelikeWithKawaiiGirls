package roguelike.event;

import roguelike.creature.BodyPart;
import roguelike.creature.Creature;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 15.08.13
 * Time: 2:55
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureUsedBodyPartsEventInterface extends EventInterface{

    public Creature getCreatureUsedBodyParts();

    public List<BodyPart> getBlockedBodyParts();

    public List<BodyPart> getUsedBodyParts();

    public double getActionWeight();

}
