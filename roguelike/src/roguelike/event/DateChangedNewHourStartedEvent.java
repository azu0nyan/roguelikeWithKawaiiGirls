package roguelike.event;

import roguelike.datetime.Date;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 22.08.13
 * Time: 2:32
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedNewHourStartedEvent extends DateChangedEvent implements DateChangedNewHourStartedEventInterface {

    public DateChangedNewHourStartedEvent(Date date) {
        super(date);
    }

    @Override
    public String getEventDescription() {
        return super.getEventDescription() + " new hour started!";
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.NEW_HOUR_STARTED);
        return res;
    }
}
