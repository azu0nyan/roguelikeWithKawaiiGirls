package roguelike.DAO.prototypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.sun.istack.internal.Nullable;
import roguelike.DAO.ItemPrototypesLoader;
import roguelike.DAO.ItemTypes;
import roguelike.DAO.PropertyPrototype;
import roguelike.Game;
import roguelike.TileCordinatesLinkedToChunk;
import roguelike.item.EquipmentSlotType;
import roguelike.item.Item;
import roguelike.item.itemTemplates.ItemTemplates;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.Material;

/**
 *
 * @author nik
 */
public class ItemPrototype {
    int id = 1;
    public String name = "default itemPrototype name";
    public String description = "default itemPrototype description";
    public char symbol;
    public List<PropertyPrototype> properties;
    /**
     * Гарантированно работает только у SimpleItem
     */
    double weight = 0.1;



    public List<EquipmentSlotType> slotTypes;
    public Map<String, Material> materials;
    public String stats;

    public ItemTemplates template;

    @Deprecated
    ItemTypes type;
    @Deprecated
    String damage = "";
    @Deprecated
    String onEquipSpell = "";
    @Deprecated
    String onAttackSpell = "";

    public ItemPrototype(){
        properties = new ArrayList<>();
        slotTypes = new ArrayList<EquipmentSlotType>();
        materials = new HashMap<>();
    }

    public Item createInstance(Game game, TileCordinatesLinkedToChunk tCord, @Nullable LocatedObjectWithPropertiesInterface owner){
        //Item item = new Item(tCord, owner);
        /*item.setName(name);
        item.setDescription(description);
        item.setSymbol(symbol);
        if(type.equals(ItemTypes.EQUIPMENT) || type.equals(ItemTypes.WEAPON)){
            item.addProperty(new EquipableItemProperty(item, slotTypes, stats));
        }
        if(type.equals(ItemTypes.WEAPON)){
            item.addProperty(new WeaponItemProperty(item, game.getDamage(damage)));
        }      */
        return template.getItemTemplate().createItem(game, tCord, owner, this, materials);
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }

    public Map<String, Material> getMaterials() {
        return materials;
    }

    public void addProperty(PropertyPrototype property){
        properties.add(property);
    }

    public void addMaterial(String name, Material material){
        materials.put(name, material);
    }

    public static ItemPrototype parse(ResultSet rs) throws SQLException {
        int slotsToRead = ItemPrototypesLoader.slotsToRead;
        ItemPrototype type = new ItemPrototype();
        type.id = rs.getInt("itemID");
        type.name = rs.getString("name");
        type.description = rs.getString("description");
        type.symbol = rs.getString("Symbol").charAt(0);
        type.weight = rs.getDouble("weight");
        type.template = ItemTemplates.valueOf(rs.getString("template"));
        type.type = ItemTypes.valueOf(rs.getString("itemType"));
        if(rs.getBoolean("isEquipment")){
            for(int i = 1; i <= slotsToRead; i++){
                String tempType = rs.getString("slotType" + String.valueOf(i));
                try {
                    type.slotTypes.add(EquipmentSlotType.valueOf(tempType));
                } catch (Exception ignore) {
                    if(tempType != null && !tempType.trim().isEmpty() && !tempType.equals("null")){
                        System.out.println("Unknown EquipmentSlotType:" + tempType);
                    }
                }

            }
            type.stats =rs.getString("stats");

        }
        return type;  //To change body of created methods use File | Settings | File Templates.
    }
}
