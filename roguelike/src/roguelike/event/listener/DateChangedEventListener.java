package roguelike.event.listener;

import roguelike.datetime.Date;
import roguelike.datetime.GlobalDate;
import roguelike.event.EventType;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 1:38
 * To change this template use File | Settings | File Templates.
 */
public abstract class DateChangedEventListener extends EventListener{

    GlobalDate date;

    public DateChangedEventListener(GlobalDate date){
        super(EventType.DATE_CAHNGED);
        date.addListener(this);
        this.date = date;
    }

    public void onRemove(){
        date.removeListener(this);
    }

}
