package roguelike;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 04.05.13
 * Time: 4:15
 * To change this template use File | Settings | File Templates.
 */
public interface TileCordinatesChangedListener {

    /*
    * Вызывается при изменении кординат
    * */
    public void cordinatesChanged(TileCordinatesLinkedToChunk tCord, int dx, int dy, int dz);

}
