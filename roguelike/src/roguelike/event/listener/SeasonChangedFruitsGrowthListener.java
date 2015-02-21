package roguelike.event.listener;

import roguelike.datetime.Date;
import roguelike.datetime.GlobalDate;
import roguelike.datetime.Season;
import roguelike.event.DateChangedSeasonsChangedStartedEventInterface;
import roguelike.event.EventInterface;
import roguelike.event.EventType;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectProperty;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 30.09.13
 * Time: 20:47
 * To change this template use File | Settings | File Templates.
 */

/**
 *
 */
public class SeasonChangedFruitsGrowthListener extends DateChangedEventListener{

    private Boolean growthStarted;
    private GatheringWorldObjectProperty worldObject;
    private Season growthStartSeason;
    private Season gatherEndSeason;
    private int daysToGrown;
    private int currentDay = 0;
    private Date start;


    public SeasonChangedFruitsGrowthListener(GatheringWorldObjectProperty worldObject, GlobalDate date, Season growthStartSeason, Season gatherEndSeason, int daysToGrown) {
        super(date);
        this.worldObject = worldObject;
        this.growthStartSeason = growthStartSeason;
        this.gatherEndSeason = gatherEndSeason;
        this.daysToGrown = daysToGrown;
        growthStarted = false;

    }



    //говнокод
    @Override
    public String processEvent(EventInterface event) {
        String res = "SeasonChangedFruitsGrowthListener owner:" + worldObject.toString();
        if(!growthStarted && event.hasType(EventType.SEASONS_CHANGED)){
            DateChangedSeasonsChangedStartedEventInterface ev = (DateChangedSeasonsChangedStartedEventInterface) event;
            if(ev.getStartedSeasons().contains(growthStartSeason)){
                growthStarted = true;
                start = ev.getDate();
                currentDay = 0;
                res += " action:growthStarted";
            }
        }

        if(event.hasType(EventType.SEASONS_CHANGED)){
            DateChangedSeasonsChangedStartedEventInterface ev = (DateChangedSeasonsChangedStartedEventInterface) event;
            if(ev.getStartedSeasons().contains(gatherEndSeason)){
                worldObject.removeLoot();
                res += " action:lootRemoved";
            }
        }

        if(growthStarted && event.hasType(EventType.NEW_DAY_STARTED)){//может считать один день за несколько
            currentDay++;
            if(currentDay >= daysToGrown){
                worldObject.generateLoot();
                growthStarted = false;
                res += " action:lootGenerated";
            }
        }
        return "";
    }
}
