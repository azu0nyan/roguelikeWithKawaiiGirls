package roguelike.stats;

/**
 *
 * @author nik
 */
public interface ItemDefenseStatsInterface {
    //ограничение полномочий класса bodyPart
    public int getNatureResist();

    public int getFrostResist();

    public int getFireResist();

    public int getHolyResist();

    public int getUnholyResist();

    public int getPhusicalResist();
}
