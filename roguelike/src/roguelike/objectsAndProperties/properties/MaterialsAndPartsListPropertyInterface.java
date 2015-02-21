package roguelike.objectsAndProperties.properties;

import roguelike.Pair;
import roguelike.objectsAndProperties.Material;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 07.10.13
 * Time: 1:17
 * To change this template use File | Settings | File Templates.
 */
public interface MaterialsAndPartsListPropertyInterface extends WorldObjectPropertyInterface{



    public Map<String, Pair<Material, Double>> getMaterials();

    public void addPartWithMaterial(Material m, String name, double size);

    public boolean removePart(String partName);

    Pair<Material, Double> getMaterialByName(String name);

    double getWeight();
}
