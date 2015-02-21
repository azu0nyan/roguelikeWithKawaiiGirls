package roguelike.event;

import roguelike.datetime.Date;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 22.08.13
 * Time: 2:31
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedNewMinuteStartedEvent extends DateChangedEvent implements DateChangedNewMinuteStartedEventInterface{

    public DateChangedNewMinuteStartedEvent(Date date) {
        super(date);
    }

    @Override
    public String getEventDescription() {
        return super.getEventDescription() + " new minute started!";
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.NEW_MINUTE_STARTED);
        return res;
    }
}
