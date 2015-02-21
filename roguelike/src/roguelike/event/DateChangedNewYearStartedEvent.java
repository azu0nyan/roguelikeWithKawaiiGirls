package roguelike.event;

import roguelike.datetime.Date;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 2:24
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedNewYearStartedEvent extends DateChangedEvent implements DateChangedNewYearStartedEventInterface{

    public DateChangedNewYearStartedEvent(Date date) {
        super(date);
    }

    @Override
    public String getEventDescription() {
        return super.getEventDescription() + " new year started!";
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.NEW_YEAR_STARTED);
        return res;
    }

}
