package roguelike;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 02.10.13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */

/**
 * Биндимся к кординатам
 */
public class TileCordinatesLinkedToChunkBindListener implements TileCordinatesChangedListener{

    private TileCordinates owner;
    private TileCordinatesLinkedToChunk cordsToBind;

    public TileCordinatesLinkedToChunkBindListener(TileCordinates owner, TileCordinatesLinkedToChunk cordsToBind){
        this.owner = owner;
        this.cordsToBind = cordsToBind;
        cordsToBind.addListener(this);
    }

    public void unBind(){
        cordsToBind.removeListener(this);
    }

    public TileCordinatesLinkedToChunk getBindedCordinates(){
    return cordsToBind;
    }

    @Override
    public void cordinatesChanged(TileCordinatesLinkedToChunk tCord, int dx, int dy, int dz) {
        System.out.println("changeddx:" + dx);
        owner.add(dx, dy, dz);
    }
}
