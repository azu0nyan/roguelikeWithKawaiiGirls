package roguelike.event;

import roguelike.datetime.Season;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public interface DateChangedSeasonsChangedStartedEventInterface extends DateChangedEventInterface {

    public List<Season> getStartedSeasons();

    public List<Season> getEndedSeasons();

}
