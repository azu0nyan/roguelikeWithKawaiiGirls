package roguelike.worldgenerator;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 18.08.13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public class Generator {

    PreGeneratedMap map;

    int maxX;
    int maxY;
    int seed = 0;
    double koeff = 1;
    double bordersKoeff = 0;
    Random random;
    double minAvTemperature = -30;
    double maxAvTemperature = 50;
    double avTemperatureDifference = maxAvTemperature - minAvTemperature;
    int maxHeight = 256;

    /**
     *
     * @param x 2^n+1
     * @param y 2^n+1
     */
    public Generator(int x, int y){
        map = new PreGeneratedMap(x - 1, y - 1);
        maxX = x;
        maxY = y;

    }

    public void generate(int seed){
        System.out.println("Seed:" + seed);
        koeff = (double)maxHeight / (double)maxX;
        random = new Random(seed);
        diamondSquare();
        setAvTemperature();
    }

    public void generate(){
        Random r = new Random();
        generate(r.nextInt());
    }

    public PreGeneratedMap getPreGeneratedMap(){
        return map;
    }
     //DIAMOND SQUARE
    public void diamondSquare(){
        System.out.println("diamondSquare generation started size:" + maxX + "x" + maxY);
        long startTime = System.currentTimeMillis();
        map.chunkPrototypes2D = new ChunkPrototype2D[maxX][maxY];
        setXYHeight(0, 0, 0);
        setXYHeight(0, maxY - 1, 0);
        setXYHeight(maxX - 1, 0, 0);
        setXYHeight(maxX - 1, maxY - 1, 0);
        //5,9,17,33,65,129,257
        square(maxX - 1);//2,4,8,16
        System.out.println("Generation ended:"  + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void square(int stepSize){
        for(int tempX = 0; tempX + stepSize < maxX; tempX += stepSize){
            for(int tempY = 0; tempY + stepSize < maxY; tempY += stepSize){
                double middle = getXYHeight(tempX, tempY) +
                                getXYHeight(tempX + stepSize, tempY) +
                                getXYHeight(tempX, tempY + stepSize) +
                                getXYHeight(tempX + stepSize, tempY + stepSize);
                middle /= 4;
                middle += (random.nextDouble() - 0.5) * 2 * stepSize * koeff;
                setXYHeight(tempX + stepSize / 2, tempY + stepSize / 2, middle);
            }
        }
        diamond(stepSize);
    }

    private void diamond(int stepSize){
        for(int tempX = 0; tempX < maxX; tempX += stepSize){
            for(int tempY = 0; tempY < maxY; tempY += stepSize){
                double top = getXYHeight(tempX + stepSize / 2, tempY - stepSize / 2) +
                        getXYHeight(tempX + stepSize / 2, tempY + stepSize / 2) +
                        getXYHeight(tempX, tempY) +
                        getXYHeight(tempX + stepSize, tempY);
                top /= 4;
                top += (random.nextDouble() - 0.5) * 2 * stepSize * koeff;
                setXYHeight(tempX + stepSize / 2, tempY, top);
                double left = getXYHeight(tempX, tempY) +
                              getXYHeight(tempX, tempY + stepSize) +
                              getXYHeight(tempX - stepSize / 2, tempY) +
                              getXYHeight(tempX + stepSize / 2, tempY);
                left /= 4;
                left += (random.nextDouble() - 0.5) * 2 * stepSize * koeff;
                setXYHeight(tempX, tempY + stepSize / 2, left);
            }
        }

        if(stepSize >= 3){
            square(stepSize / 2);
        }
    }

    /**0
     *     N
     *  W-----E
     *     S
     * y
     */
    public void setAvTemperature(){
        for(int i = 0; i < maxY; i++){
            double avParallelTemperature =  (i / maxY) * (avTemperatureDifference) + minAvTemperature;
            for(int j = 0; j < maxX; j++){
                double currentHeight = getXYHeight(j, i);
                double heightTemperatureReductionRate = currentHeight / maxHeight;
                double currentTemperature = avParallelTemperature - heightTemperatureReductionRate * (avTemperatureDifference / 2);
                map.getChunkPrototype(j, i).setAverageTemperature(currentTemperature);
            }
        }
    }

    public void setRainfall(){

    }

    public void setDrainage(){

    }

    public void setMagicCapacity(){

    }

    public void setXYHeight(int x, int y, double height) {
        map.setXYHeight(x, y, height);
    }

    public double getXYHeight(int x, int y) {
        return map.getXYHeight(x, y);
    }
}
