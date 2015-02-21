package roguelike.event;

import java.util.LinkedList;

import roguelike.CONFIGURATION;
import roguelike.Game;

/**
 *
 * @author nik
 */
@Deprecated
public class ThreadEventManager extends Thread{
    private Game game;
    private LinkedList<Event> events;
    private boolean isPause = false;   
    private int eventsCount = 0;
    private int id;

    public ThreadEventManager(Game game, int id){
        events = new LinkedList<Event>();
        this.game = game;
        this.id = id;
    }

    @Override
    public void run() {
        while(!game.isExit){
            if(!isPause && eventsCount > 0){
                synchronized(events){
                    Event temp = events.poll();
                    eventsCount--;
                    temp.onStartAction();
                    game.sendEvent(temp);//TODO заменить на систему слушателей
                    temp.onEndAction();
                    if(CONFIGURATION.logEvents){
                        System.out.println("mrg:" + id +  " ev:" + temp.getEventDescription());
                    }
                }
            } 
            synchronized(this){
                if(isPause || eventsCount == 0){
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("ThreadEventManager Interrupted");
                    }
                }
            }
            
        }
    }
    public void pause_(){
        isPause = true;
    }
    public void continue_(){
        isPause = false;
        this.notify();
    }
    public synchronized void addEvent(Event event){
        if(event != null){
            synchronized(events){
                events.addLast(event);
                eventsCount++;
            }         
            synchronized(this){
                this.notify();
            }
        }
    }
    public int getEventsCount(){
        return eventsCount; 
    }
}
