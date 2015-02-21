package roguelike.item.itemTemplates;

import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.Material;
import roguelike.objectsAndProperties.properties.*;
import roguelike.stats.DamageElement;
import roguelike.stats.DamageType;
import roguelike.stats.DamageValue;
import roguelike.stats.Stats;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 08.10.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */

/**
 * steel sword ~1.5 kg weight
 */
public class SwordItemTemplate extends ItemTemplate {

    public static double bladeVolume = 0.2;//liters lol
    public static double gripVolume = 0.1;
    public static double renderMultiplier = 10;
    public static double weightBluntMultiplier = 5;

    public SwordItemTemplate(){

    }

    @Override
    public String getName(Map<String, Material> materials){
        return "Short" + ((materials.get("blade") != null)?materials.get("blade").getName():"") + " sword";
    }



    @Override
    public String getNonMainPartsDescription(Map<String, Material> materials){
        return  ((materials.get("pommel") != null || materials.get("guard") != null || materials.get("grip") != null)?" with ":"") +
                ((materials.get("grip") != null)?materials.get("grip").getName() + " grip ":"") +
                ((materials.get("guard") != null)?materials.get("guard").getName() + " guard ":"") +
                ((materials.get("pommel") != null)?materials.get("pommel").getName() + " pommel":"");
    }

    @Override
    public MaterialsAndPartsListPropertyInterface setMaterialsAndSizes(Item item, Map<String, Material> materials){
        MaterialsAndPartsListPropertyInterface materialsProperty = new MaterialsAndPartsListProperty(item, null);
        for(String partName : materials.keySet()){
            switch (partName){
                case "blade":{
                    materialsProperty.addPartWithMaterial(materials.get(partName), partName, materials.get(partName).getDensity() * bladeVolume);
                    break;
                }
                case "grip":{
                    materialsProperty.addPartWithMaterial(materials.get(partName), partName, materials.get(partName).getDensity() * gripVolume);
                    break;
                }
                default:{
                    materialsProperty.addPartWithMaterial(materials.get(partName), partName, 0);
                }
            }
        }
        item.addProperty(materialsProperty);
        return materialsProperty;
    }

    @Override
    public void setAdditionalProperties(Item item, MaterialsAndPartsListPropertyInterface materialsProperty){
        DamageValue slashDmg = new DamageValue();
        slashDmg.addDamageByType((int) (((materialsProperty.getMaterialByName("blade") != null) ? materialsProperty.getMaterialByName("blade").first().getRenderCoefficient() : 1) * renderMultiplier),
                DamageType.CUTTING, DamageElement.PHUSICAL);
        slashDmg.addDamageByType((int)(materialsProperty.getWeight() * weightBluntMultiplier), DamageType.CRUSHING, DamageElement.PHUSICAL);
        WeaponPropertyInterface weaponProperty = new WeaponItemProperty(item, slashDmg);
        item.addProperty(weaponProperty);
    }

    @Override
    public String getMainDescriptionPart(Map<String, Material> materials){
        return getName(materials);
    }

    @Override
    public void setStatsEquipmentEtc(Item item,  List<EquipmentSlotType> slotTypes, Stats stats){
        slotTypes = newArrayList(EquipmentSlotType.RIGHTHAND);
        if(slotTypes != null && slotTypes.size() > 0){
            item.addProperty(new EquipableItemProperty(item, slotTypes, stats));
        }
    }

}
