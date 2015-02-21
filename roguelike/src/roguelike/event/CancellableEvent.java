package roguelike.event;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 18.06.13
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class CancellableEvent extends Event implements CancellableEventInterface {

    boolean canceled = false;

    @Override
    public void cancel() {
        canceled = true;
    }

    @Override
    public void unCancel(){
        canceled = false;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
