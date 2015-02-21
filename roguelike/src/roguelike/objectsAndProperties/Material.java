package roguelike.objectsAndProperties;

import roguelike.DAO.PropertyPrototype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 06.10.13
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class Material {

    private int id;

    private String name;

    private double renderResistance;//0-1

    private double bluntResistance;//0-1

    private double piercingResistance;//0-1

    //any_resistances

    private double renderCoefficient;//0-inf

    private double density;//0-inf 1 - water  5 - wood

    private double durability;//0-inf 0.01 - water  0.1 - paper 1.0 - wood

    private boolean combustible;

    private double combustionTemperature;//C'

    private boolean meltable;

    private int meltTemperature;

    private List<PropertyPrototype> properties;
    //etc

    public Material(int id){
        this.id = id;
        properties = new CopyOnWriteArrayList<>();
    }

    public static Material parse(ResultSet rs) throws SQLException {
        Material m = new Material(rs.getInt("materialID"));
        m.setName(rs.getString("name"));
        m.setRenderResistance(rs.getDouble("renderResistance"));
        m.setBluntResistance(rs.getDouble("bluntResistance"));
        m.setPiercingResistance(rs.getDouble("piercingResistance"));
        m.setRenderCoefficient(rs.getDouble("renderCoefficient"));
        m.setDensity(rs.getDouble("density"));
        m.setDurability(rs.getDouble("durability"));
        m.setCombustible(rs.getBoolean("combustible"));
        m.setCombustionTemperature(rs.getInt("combustionTemperature"));
        m.setMeltable(rs.getBoolean("meltable"));
        m.setMeltTemperature(rs.getInt("meltTemperature"));
        return m;
    }
    public void addProperty(PropertyPrototype property){
        properties.add(property);
    }

    public List<PropertyPrototype> getProperties(){
        return properties;
    }

    public String getName() {
        return name;
    }

    public double getRenderResistance() {
        return renderResistance;
    }

    public double getBluntResistance() {
        return bluntResistance;
    }

    public double getPiercingResistance() {
        return piercingResistance;
    }

    public double getRenderCoefficient() {
        return renderCoefficient;
    }

    public double getDensity() {
        return density;
    }

    public double getDurability() {
        return durability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRenderResistance(double renderResistance) {
        this.renderResistance = renderResistance;
    }

    public void setBluntResistance(double bluntResistance) {
        this.bluntResistance = bluntResistance;
    }

    public void setPiercingResistance(double piercingResistance) {
        this.piercingResistance = piercingResistance;
    }

    public void setRenderCoefficient(double renderCoefficient) {
        this.renderCoefficient = renderCoefficient;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    public void setCombustible(boolean combustible) {
        this.combustible = combustible;
    }

    public boolean isCombustible() {
        return combustible;
    }

    public double getCombustionTemperature() {
        return combustionTemperature;
    }

    public void setCombustionTemperature(double combustionTemperature) {
        this.combustionTemperature = combustionTemperature;
    }

    public int getId() {
        return id;
    }


    public boolean isMeltable() {
        return meltable;
    }

    public void setMeltable(boolean meltable) {
        this.meltable = meltable;
    }

    public int getMeltTemperature() {
        return meltTemperature;
    }

    public void setMeltTemperature(int meltTemperature) {
        this.meltTemperature = meltTemperature;
    }
}
