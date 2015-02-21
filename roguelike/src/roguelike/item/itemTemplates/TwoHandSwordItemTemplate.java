package roguelike.item.itemTemplates;

import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.Material;
import roguelike.objectsAndProperties.properties.EquipableItemProperty;
import roguelike.stats.Stats;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 23.10.13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public class TwoHandSwordItemTemplate extends SwordItemTemplate {

    public static double bladeVolume = 2.0;
    public static double gripVolume = 0.5;

    @Override
    public String getName(Map<String, Material> materials){
        return "Twohand " + ((materials.get("blade") != null)?materials.get("blade").getName():"") + " sword";
    }


    @Override
    public void setStatsEquipmentEtc(Item item,  List<EquipmentSlotType> slotTypes, Stats stats){
        slotTypes = newArrayList(EquipmentSlotType.RIGHTHAND, EquipmentSlotType.LEFTHAND);
        if(slotTypes != null && slotTypes.size() > 0){
            item.addProperty(new EquipableItemProperty(item, slotTypes, stats));
        }
    }
}
