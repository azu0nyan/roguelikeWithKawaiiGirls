package roguelike.event;

import roguelike.datetime.Date;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 22.08.13
 * Time: 1:51
 * To change this template use File | Settings | File Templates.
 */
public interface DateChangedEventInterface extends EventInterface {

    public Date getDate();

}
