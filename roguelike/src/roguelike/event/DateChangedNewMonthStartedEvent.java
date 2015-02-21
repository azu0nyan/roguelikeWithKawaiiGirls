package roguelike.event;

import roguelike.datetime.Date;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 2:28
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedNewMonthStartedEvent extends DateChangedEvent implements DateChangedNewMonthStartedEventInterface{

    public DateChangedNewMonthStartedEvent(Date date) {
        super(date);
    }

    @Override
    public String getEventDescription() {
        return super.getEventDescription() + " new month started!";
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.NEW_MONTH_STARTED);
        return res;
    }
}
