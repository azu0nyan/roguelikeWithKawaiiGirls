package roguelike.event;

import roguelike.Game;

import java.util.EventListener;
import java.util.LinkedList;

/**
 *
 * @author azu
 */
@Deprecated
public class EventsManager implements EventManagerInterface{
    
    private int maxThreads = 3;
    private int currentId = 0;
    
    private LinkedList<ThreadEventManager> threads;
    private Game game;
    
    public EventsManager(Game game){
        this.game = game;
        threads = new LinkedList<ThreadEventManager>();
        for(int i = 0; i < maxThreads; i++){
            addNewThreadEventManager();
        }
        
    }

    public void setMaxThreadsCount(int threadsCount){
        maxThreads = threadsCount;
    }
   
    @Override
    public void sendEvent(Event event){
        ThreadEventManager temp = threads.getFirst();
        //ищем наиболее свободный менеджер
        synchronized(threads){
            for(ThreadEventManager manager : threads){
                if(temp.getEventsCount()  > manager.getEventsCount()){
                   temp = manager;
                    if(temp.getEventsCount() == 0){
                        break;
                    }
                }
            }
        }
        temp.addEvent(event); //Единственное место где должен вызываться этот метод
        //temp.notify();
    }
    private void addNewThreadEventManager(){        
        ThreadEventManager temp = new ThreadEventManager(game, currentId);
        currentId++;
        System.out.println("Creating new ThreadEventManager");
        threads.add(temp);
        temp.start();
    }
    
    public void registerListener(EventListener listener){
        if(listener == null){
            return;
        }
        //blah-blah-blah
    }
    public void pause_(){
        for(ThreadEventManager manager : threads){
            manager.pause_();
        }
    }
    public void continue_(){
        for(ThreadEventManager manager : threads){
            manager.continue_();
        }
    }
}
