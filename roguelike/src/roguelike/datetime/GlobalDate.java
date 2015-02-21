package roguelike.datetime;

import roguelike.DateEventProcessor;
import roguelike.event.*;
import roguelike.event.listener.DateChangedEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 22.08.13
 * Time: 2:08
 * To change this template use File | Settings | File Templates.
 */
public class GlobalDate extends Date {

    private DateEventProcessor eventProcessor;

    public GlobalDate(int minute, int hour, int day, int month, int year) {
        super(minute, hour, day, month, year);
        eventProcessor = new DateEventProcessor();
    }

    public void addListener(DateChangedEventListener dateChangedEventListener) {
        eventProcessor.addEventListener(dateChangedEventListener);
    }

    public void removeListener(DateChangedEventListener dateChangedEventListener) {
        eventProcessor.removeEventListener(dateChangedEventListener);
    }

    public synchronized void update(long millis){
        long minutesToUpdate = (millis + timeRest) / millisPerMinute;
        timeRest = (millis + timeRest) % millisPerMinute;
        for(int i = 0; i < minutesToUpdate; i++){
            incMinute();
        }
        if(minutesToUpdate > 0){
            eventProcessor.processEvent(new DateChangedEvent(this.newInstance()));
        }
    }

    private void incMinute(){
        if(minute + 1 >= minutesPerHour){
            minute = 0;
            incHour();
        } else {
            minute++;
        }
        eventProcessor.processEvent(new DateChangedNewMinuteStartedEvent(this.newInstance()));
    }

    private void incHour(){
        if(hour + 1 >= hourPerDay){
            hour = 0;
            incDay();
        } else {
            hour++;
        }
        eventProcessor.processEvent(new DateChangedNewHourStartedEvent(this.newInstance()));
    }

    private void incDay(){
        if(day + 1 > dayPerMonth){
            day = 1;
            incMonth();
        } else {
            day++;
        }
        eventProcessor.processEvent(new DateChangedNewDayStartedEvent(this.newInstance()));
    }

    private void incMonth(){
        int oldMonth = month;
        if(month + 1 > monthPerYear){
            month = 1;
            incYear();
        } else {
            month++;
        }
        eventProcessor.processEvent(new DateChangedNewMonthStartedEvent(this.newInstance()));

        List<Season> oldSeasons = Season.getSeasonsByMonth(oldMonth);
        List<Season> newSeasons = Season.getSeasonsByMonth(month);

        List<Season> startedSeasons = new ArrayList<>();
        for(Season season : newSeasons){
            if(!oldSeasons.contains(season)){
                startedSeasons.add(season);
            }
        }
        List<Season> endedSeasons = new ArrayList<>();
        for (Season season : oldSeasons){
            if(!newSeasons.contains(season)){
                endedSeasons.add(season);
            }
        }
        if(startedSeasons.size() > 0 || endedSeasons.size() > 0 ){
            eventProcessor.processEvent(new DateChangedSeasonsChangedStartedEvent(this.newInstance(), startedSeasons, endedSeasons));
        }
    }

    private void incYear(){
        year++;
        eventProcessor.processEvent(new DateChangedNewYearStartedEvent(this.newInstance()));

    }

}
