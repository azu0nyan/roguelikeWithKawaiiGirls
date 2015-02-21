package roguelike.event;

import roguelike.Tools;
import roguelike.datetime.Date;
import roguelike.datetime.Season;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 22.08.13
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class DateChangedSeasonsChangedStartedEvent extends DateChangedEvent implements DateChangedSeasonsChangedStartedEventInterface {

    private List<Season> seasonsStarted;
    private List<Season> seasonsEnded;

    public DateChangedSeasonsChangedStartedEvent(Date date, List<Season> seasonsStarted, List<Season> seasonsEnded) {
        super(date);
        this.seasonsStarted = seasonsStarted;
        this.seasonsEnded = seasonsEnded;
    }

    @Override
    public List<Season> getStartedSeasons() {
        return seasonsStarted;
    }

    @Override
    public List<Season> getEndedSeasons() {
        return seasonsEnded;
    }

    @Override
    public String getEventDescription() {
        return super.getEventDescription()  + " seasons started:"+ Tools.getSeparatedStringList(seasonsStarted, "[", "]", "|") +
                " seasons ended:" + Tools.getSeparatedStringList(seasonsEnded, "[", "]", "|");
    }

    @Override
    public List<EventType> getTypes(){
        List<EventType> res = super.getTypes();
        res.add(EventType.SEASONS_CHANGED);
        return res;
    }
}
