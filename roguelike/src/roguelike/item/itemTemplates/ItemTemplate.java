package roguelike.item.itemTemplates;

import org.jetbrains.annotations.Nullable;
import roguelike.DAO.prototypes.ItemPrototype;
import roguelike.Game;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.Material;
import roguelike.objectsAndProperties.properties.EquipableItemProperty;
import roguelike.objectsAndProperties.properties.MaterialsAndPartsListProperty;
import roguelike.objectsAndProperties.properties.MaterialsAndPartsListPropertyInterface;
import roguelike.stats.Stats;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 08.10.13
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class ItemTemplate {



    public Item createItem(Game game, TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner, @Nullable ItemPrototype prototype, Map<String, Material> materials){
        String name =  getName(materials);
        String description = getMainDescriptionPart(materials) + getNonMainPartsDescription(materials);

        Item item = new Item(tCord, owner);
        if(prototype != null){
            setBasic(item, prototype);
        } else {
            setBasic(item, name, description, (name == null || "".equals(name))?'N':name.charAt(0));
        }
        setStatsEquipmentEtc(item, (prototype == null)?null:prototype.slotTypes, (prototype == null)?null:game.getStats(prototype.stats));
        MaterialsAndPartsListPropertyInterface property = setMaterialsAndSizes(item, materials);
        setAdditionalProperties(item, property);


        return item;
    }

    public Item createItem(Game game, TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner, ItemPrototype prototype){
        return createItem(game, tCord, owner, prototype, prototype.getMaterials());
    }

    public Item createItem(Game game, TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner, Map<String, Material> materials){
        return createItem(game, tCord, owner, null, materials);
    }

    public String getName(Map<String, Material> materials){
        return "default name";
    }

    public String getNonMainPartsDescription(Map<String, Material> materials){
        return "";
    }

    public String getMainDescriptionPart(Map<String, Material> materials){
        return "default description";
    }

    public void setBasic(Item item, ItemPrototype prototype){
        setBasic(item, prototype.name, prototype.description, prototype.symbol);
    }

    public void setBasic(Item item, String name, String description, char symbol){
        item.setName(name);
        item.setDescription(description);
        item.setSymbol(symbol);
    }

    public MaterialsAndPartsListPropertyInterface setMaterialsAndSizes(Item item, Map<String, Material> materials){
        MaterialsAndPartsListPropertyInterface materialsProperty = new MaterialsAndPartsListProperty(item, null);
        for(String partName : materials.keySet()){
            materialsProperty.addPartWithMaterial(materials.get(partName), partName, item.getSize() / materials.size());
        }
        item.addProperty(materialsProperty);
        return materialsProperty;
    }

    public void setAdditionalProperties(Item item, MaterialsAndPartsListPropertyInterface materialsProperty){

    }

    public void setStatsEquipmentEtc(Item item,  List<EquipmentSlotType> slotTypes, Stats stats){
        if(slotTypes != null && slotTypes.size() > 0){
            item.addProperty(new EquipableItemProperty(item, slotTypes, stats));
        }
    }
}
