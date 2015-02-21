package roguelike.datetime;

import roguelike.event.listener.DateChangedEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  azu
 */
public class Date {

    public static int millisPerMinute = 60000;
    public static int minutesPerHour = 6;
    public static int hourPerDay = 24;
    public static int dayPerMonth = 6;
    public static int monthPerYear = 12;

    protected int minute;
    protected int hour;
    protected int day;
    protected int month;
    protected int year;


    public long timeRest = 0;

    public Date(int minute, int hour, int day, int month, int year){
        this.minute = Math.min(minute, minutesPerHour - 1);
        this.hour = Math.min(hour, hourPerDay - 1);
        this.day = Math.min(day, dayPerMonth);
        this.month = Math.min(month, monthPerYear);
        this.year = year;
    }

    public Date newInstance(){
        return new Date(minute, hour, day, month, year);
    }

    public String toString(){
        return minute + "m " + hour + "h " + day + "d " + month + "m " + year + "y";
    }

    public int getMinute(){
        return minute;
    }

    public int getHour(){
        return hour;
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    public int getYear(){
        return year;
    }

    public List<Season> getSeasons(){
        return Season.getSeasonsByMonth(getMonth());
    }

    //хз работает ли
    public int getDaysDifference(Date date){
        return (date.getMonth() - getMonth()) * dayPerMonth + (date.getYear() - getYear()) * dayPerMonth * monthPerYear + date.getDay() - getDay();
    }
}

