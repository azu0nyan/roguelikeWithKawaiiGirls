package roguelike.event;

import roguelike.datetime.Date;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 1:53
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedEvent extends Event implements DateChangedEventInterface {

    Date date;

    public DateChangedEvent(Date date){
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.DATE_CAHNGED);
        return res;
    }

    @Override
    public String getEventDescription() {
        return "Now:" +  date.toString();
    }
}
