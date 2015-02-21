package roguelike.ui;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 14.01.14
 * Time: 6:48
 * To change this template use File | Settings | File Templates.
 */
public enum DrawableGlobalMapMode {
    HEIGHT, AVERAGE_TEMPERATURE, RAINFALL, DRAINAGE, MAGIC_CAPACITY, BIOMES;

    public DrawableGlobalMapMode getNext(DrawableGlobalMapMode prev){
        switch (prev){
            case HEIGHT:{
                return AVERAGE_TEMPERATURE;
            }
            case AVERAGE_TEMPERATURE:{
                return RAINFALL;
            }
            case RAINFALL:{
                return DRAINAGE;
            }
            case DRAINAGE:{
                return MAGIC_CAPACITY;
            }
            case MAGIC_CAPACITY:{
                return BIOMES;
            }
            case BIOMES:{
                return HEIGHT;
            }
        }
        return HEIGHT;
    }
}
