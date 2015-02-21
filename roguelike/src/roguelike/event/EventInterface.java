package roguelike.event;

import java.util.List;

/**
 *
 * @author nik
 */
public interface EventInterface {

    public List<EventType> getTypes();

    public boolean hasType(EventType type);

    public boolean isActionEvent();

    public void onStartAction();

    public void onEndAction();

    public String getEventDescription();
}
