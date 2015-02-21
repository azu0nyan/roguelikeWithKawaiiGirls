package roguelike.DAO.prototypes;

import it.biobytes.ammentos.PersistentEntity;
import it.biobytes.ammentos.PersistentField;
import it.biobytes.ammentos.PersistentList;
import roguelike.DAO.BodyPartsLoader;
import roguelike.DAO.HasId;
import roguelike.creature.BodyPart;
import roguelike.item.EquipmentSlotType;

import java.util.List;

/**
 *
 * @author azu
 */
@PersistentEntity(
        sourceDomain = "BODY_PARTS",
        targetDomain = "BODY_PARTS",
        primaryKey = "id"
)
public class BodyPartPrototype implements HasId{
    @PersistentField(
            fieldName = "id"
    )
    public int id;

    @PersistentField
    public String name;

    @PersistentField
    public double size;

    @PersistentField
    public int maxHp;

    @PersistentField
    public boolean isMain;

    @PersistentField(
        fieldName = "EquipmentSlotType"
    )
    String equipmentSlotType;

    @PersistentList(
            query = "bodyPartId=?"
    )
    public List<BodyPartTagPrototype> tags;

    @PersistentList(
            query = "ownerId=?"
    )
    public List<ConnectedBodyPartPrototype> connectedBodyPartPrototypes;

    @PersistentField
    private boolean isLinked;

    private String equipmentSlot;

    public BodyPart getInstance(BodyPartsLoader loader){
        BodyPart part = new BodyPart(name);
        synchronized(tags){
            for(BodyPartTagPrototype tag : tags){
                part.addTag(tag.getTag());
            }
        }
        part.setSize(size);
        part.setMaxHp(maxHp);
        EquipmentSlotType eqSlot = null;
        try {
            eqSlot = EquipmentSlotType.valueOf(equipmentSlotType);
        } catch (IllegalArgumentException e){
            System.out.println("!!!Wrong equipment slot:" + equipmentSlotType);
        }
        if(eqSlot != null && !EquipmentSlotType.NONE.equals(eqSlot)){
            part.setEquipmentSlot(eqSlot);
        }

        synchronized (connectedBodyPartPrototypes){
            for(ConnectedBodyPartPrototype prototype : connectedBodyPartPrototypes){
                BodyPart tempConnectedPart = loader.getBodyPartById(prototype.partId);
                if(tempConnectedPart == null){
                    System.out.println("!!!Wrong connected body part prototypeId:" +  prototype.id);
                } else {
                    if(isLinked){
                        part.addInnerBodyPart(tempConnectedPart);
                    } else {
                        part.addInnerBodyPart(tempConnectedPart);
                    }
                }
            }
        }
        return part;
    }

    public double getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public String getEquipmentSlotType() {
        return equipmentSlotType;
    }

    public void setEquipmentSlotType(String equipmentSlotType) {
        this.equipmentSlotType = equipmentSlotType;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    @Override
    public int getID() {
        return id;
    }

    @Override
    public String toString(){
        return id + ":" + name;
    }

    public String getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(final String equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isMain() {

        return isMain;
    }

    public List<BodyPartTagPrototype> getTags() {
        return tags;
    }

    public List<ConnectedBodyPartPrototype> getConnectedBodyPartPrototypes() {
        return connectedBodyPartPrototypes;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }
}
