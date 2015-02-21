package roguelike.event;

import roguelike.datetime.Date;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 2:30
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedNewDayStartedEvent extends DateChangedEvent implements DateChangedNewDayStartedEventInterface {
    public DateChangedNewDayStartedEvent(Date date) {
        super(date);
    }

    @Override
    public String getEventDescription() {
        return super.getEventDescription() + " new day started!";
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.NEW_DAY_STARTED);
        return res;
    }
}
