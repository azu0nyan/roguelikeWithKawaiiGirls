package roguelike.event.listener;

import roguelike.event.EventInterface;
import roguelike.event.EventType;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 18.06.13
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class ChunkEventLoggerListener extends EventListener{

    public ChunkEventLoggerListener() {
        super(EventType.NONE);
    }

    @Override
    public String processEvent(EventInterface event) {
        System.out.println("event:" + event.toString());
        return "";
    }
}
