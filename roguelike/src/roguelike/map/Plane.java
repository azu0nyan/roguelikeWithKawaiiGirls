package roguelike.map;

import roguelike.Triple;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 01.11.13
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class Plane {
    Triple<Double, Double, Double> coeffs;

    public Plane(Triple<Double,Double,Double> coeffs){
        this.coeffs = coeffs;
    }

    public double getZ(double x, double y){
        return x * coeffs.first() + y * coeffs.second() + coeffs.third();
    }
}
