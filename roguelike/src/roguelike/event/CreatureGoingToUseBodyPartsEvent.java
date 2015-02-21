package roguelike.event;

import roguelike.Pair;
import roguelike.Tools;
import roguelike.creature.BodyPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 14.08.13
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public class CreatureGoingToUseBodyPartsEvent extends CancellableEvent implements CreatureGoingToUseBodyPartsEventInterface {
    /**
     * Части тела можно блокировать, если ВСЕ части тела заблокированы, то ивент отменяется, если частей тела 0 ивет отменяется
     */
    List<Pair<Boolean, BodyPart>> bodyParts;    //TRUE - unBlocked FALSE - blocked

    public CreatureGoingToUseBodyPartsEvent(List<BodyPart> bodyParts){
        List<Pair<Boolean, BodyPart>> parts = new ArrayList<>();
        for(BodyPart bodyPart : bodyParts){
            parts.add(new Pair<Boolean, BodyPart>(Boolean.TRUE, bodyPart));
        }
        this.bodyParts = parts;
        setCanceled();//Если частей тела 0 ивент отменится
    }

    @Override
    public List<BodyPart> getUsingBodyParts() {
        List<BodyPart> res = new ArrayList<>();
        for(Pair<Boolean, BodyPart> bodyPart : bodyParts){
            res.add(bodyPart.second());
        }
        return res;
    }

    @Override
    public List<BodyPart> getBlockedBodyParts() {
        List<BodyPart> res = new ArrayList<>();
        for(Pair<Boolean, BodyPart> bodyPart : bodyParts){
            if(bodyPart.first().equals(Boolean.FALSE)){
                res.add(bodyPart.second());
            }
        }
        return res;
    }

    @Override
    public List<BodyPart> getUnBlockedBodyParts() {
        List<BodyPart> res = new ArrayList<>();
        for(Pair<Boolean, BodyPart> bodyPart : bodyParts){
            if(bodyPart.first().equals(Boolean.TRUE)){
                res.add(bodyPart.second());
            }
        }
        return res;
    }

    @Override
    public void blockBodyParts(List<BodyPart> bodyParts) {
        for(Pair<Boolean, BodyPart> bodyPart : this.bodyParts){
            if(bodyParts.contains(bodyPart.second())){
                bodyPart.setFirst(Boolean.FALSE);
            }
        }
        setCanceled();
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.CREATURE_GOING_TO_USE_BODY_PARTS);
        return res;
    }

    private void setCanceled(){
        boolean b = false;
        for(Pair<Boolean, BodyPart> bodyPart : bodyParts){
            b = b ||  bodyPart.first();
        }
        if(!b){
            cancel();
        }
    }
}
