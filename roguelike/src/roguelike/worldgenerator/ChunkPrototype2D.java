package roguelike.worldgenerator;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 18.08.13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public class ChunkPrototype2D {

    private double height;

    private double averageTemperature;

    private double rainfall;

    private double drainage;

    private double magicCapacity;

    PreGeneratedMap map;

    public ChunkPrototype2D(PreGeneratedMap map, double height){
        this.map = map;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String toString(){
        return "height:" + height;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getRainfall() {
        return rainfall;
    }

    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
    }

    public double getDrainage() {
        return drainage;
    }

    public void setDrainage(double drainage) {
        this.drainage = drainage;
    }

    public double getMagicCapacity() {
        return magicCapacity;
    }

    public void setMagicCapacity(double magicCapacity) {
        this.magicCapacity = magicCapacity;
    }
}
