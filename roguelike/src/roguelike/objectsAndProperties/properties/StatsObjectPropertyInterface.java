package roguelike.objectsAndProperties.properties;

import roguelike.stats.Stats;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 08.10.13
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public interface StatsObjectPropertyInterface extends  WorldObjectPropertyInterface {

    public Stats getStats();
}
