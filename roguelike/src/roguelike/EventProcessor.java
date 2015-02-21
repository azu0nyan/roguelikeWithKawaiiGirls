package roguelike;

import roguelike.event.EventInterface;
import roguelike.event.listener.EventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 2:06
 * To change this template use File | Settings | File Templates.
 */
public class EventProcessor {

    private List<EventListener> eventListeners = new CopyOnWriteArrayList<EventListener>();

    public EventProcessor(){

    }

    public void addEventListener(EventListener eventListener){
        eventListeners.add(eventListener);
    }

    public void removeEventListener(EventListener eventListener){
        eventListeners.remove(eventListener);
    }

    public void processEvent(EventInterface event){
        //TODO здесь могут быть всякие оптимизации за счет разделения слушателей событий по типам и т.п.
        event.onStartAction();
        for(EventListener eventListener : eventListeners){
            eventListener.event(event);
        }
        if(CONFIGURATION.logEvents){
            System.out.println(event.getEventDescription());
        }
        event.onEndAction();
    }


    /**
     * DEBUG ONLY
     */
    public List<EventListener> getEventListeners(){
        return eventListeners;
    }
}
