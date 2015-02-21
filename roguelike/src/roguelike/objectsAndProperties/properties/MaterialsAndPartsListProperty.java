package roguelike.objectsAndProperties.properties;

import org.jetbrains.annotations.Nullable;
import roguelike.DAO.PropertyPrototype;
import roguelike.Pair;
import roguelike.Triple;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.Material;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 07.10.13
 * Time: 1:36
 * To change this template use File | Settings | File Templates.
 */
public class MaterialsAndPartsListProperty implements MaterialsAndPartsListPropertyInterface {


    private LocatedObjectWithProperties owner;
    private Map<String, Pair<Material, Double>> materials;

    public MaterialsAndPartsListProperty(LocatedObjectWithProperties owner, @Nullable List<Triple<Material, String, Double>> materials){
        this.owner = owner;
        this.materials = new ConcurrentHashMap<>();
        if(materials != null){
            for(Triple<Material, String, Double> m : materials){
                addPartWithMaterial(m.first(), m.second(), m.third());
            }
        }
    }

    @Override
    public void addPartWithMaterial(Material m, String name, double size){
        materials.put(name, new Pair<Material, Double>(m, size));
        for(PropertyPrototype p : m.getProperties()){
            owner.addProperty(p.createInstance(owner, m, name, size));
        }
    }

    @Override
    public boolean removePart(String partName){
        return materials.remove(partName) != null;
    }

    @Override
    public double getWeight() {
        double weight = 0;
        for(Pair<Material, Double> material : materials.values()){
            weight += material.second();
        }
        return weight;
    }

    @Override
    public Pair<Material, Double> getMaterialByName(String name) {
        return materials.get(name);
    }

    @Override
    public Map<String, Pair<Material, Double>> getMaterials() {
        return materials;
    }

    @Override
    public List<WorldObjectPropertyType> getPropertyTypes() {
        List<WorldObjectPropertyType> types = new ArrayList<>();
        types.add(WorldObjectPropertyType.MATERIALS);
        return types;
    }

    @Override
    public LocatedObjectWithPropertiesInterface getOwner() {
        return owner;
    }
}
