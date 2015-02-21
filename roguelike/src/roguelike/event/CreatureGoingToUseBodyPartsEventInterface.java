package roguelike.event;

import roguelike.creature.BodyPart;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 14.08.13
 * Time: 0:08
 * To change this template use File | Settings | File Templates.
 */
public interface CreatureGoingToUseBodyPartsEventInterface extends CancellableEventInterface{

    public List<BodyPart> getUsingBodyParts();

    public List<BodyPart> getBlockedBodyParts();

    public List<BodyPart> getUnBlockedBodyParts();

    public void blockBodyParts(List<BodyPart> bodyParts);

}
