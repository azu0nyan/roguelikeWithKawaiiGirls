package roguelike.event;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 18.06.13
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public interface CancellableEventInterface extends EventInterface{

    public void cancel();

    public void unCancel();

    public boolean isCanceled();

}
