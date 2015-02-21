package roguelike.event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author azu
 */
public class Event implements EventInterface{   
    




    @Override
    public boolean isActionEvent(){
        return false;
    }

    @Override
    public void onStartAction(){
        //вызывается перед рассылкой события 
    }

    @Override
    public void onEndAction(){
        //вызывается после рассылки события
    }
    @Override
    public boolean hasType(EventType type){
        return getTypes().contains(type);
    }
    /**
     * нужно переопределять в КАЖДОМ наследнике
     */
    @Override
    public List<EventType> getTypes(){  //обязательно к перегрузке
        //Вероятно стоит переделать, и не создавать каждый раз заново список
        ArrayList<EventType> temp = new ArrayList<EventType>();
        temp.add(EventType.NONE);
        return temp;
    }
    /**
     * нужно переопределять в КАЖДОМ наследнике
     */
    @Override
    public String getEventDescription() {
        return "Nothing happened yet";
    }
    
}
