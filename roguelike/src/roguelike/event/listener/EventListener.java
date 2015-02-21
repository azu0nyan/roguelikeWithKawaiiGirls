package roguelike.event.listener;

import roguelike.CONFIGURATION;
import roguelike.event.EventInterface;
import roguelike.event.EventType;

/**
 * Класс, наследники которого должны обрабатывать события, своего типа.
 * @author nik
 */
public abstract class EventListener {

    private EventType type;

    public EventListener(EventType type){
        this.type = type;
    }

    public EventType getType(){
        return type;
    }

    /**
     * Сюда вперыве поступает событие
     * @param event событие
     */
    public final void event(EventInterface event){
        if(event.hasType(type)){
            String log = processEvent(event);
            if(CONFIGURATION.logListeners && !"".equals(log)){
                System.out.println(log);
            }
        }
    }

    /**
     * Обрабатываем событие, обязательно переопределить у наследников
     * @param event событие
     */
    public abstract String processEvent(EventInterface event);



}
