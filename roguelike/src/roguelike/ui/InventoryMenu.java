package roguelike.ui;


import roguelike.item.EquipmentSlot;
import roguelike.item.Item;

/**
 *
 * @author nik
 */
public class InventoryMenu {
    
    GraphicsUI ui;
    DrawableObjectsList playerEquipmentList;
    DrawableObjectsList playerInventoryItemsList;
    InventoryMenuPointerState pointerState = InventoryMenuPointerState.INVENTORY;
    int pointer = 0;

    public InventoryMenu(GraphicsUI ui){
        this.ui = ui;
        playerEquipmentList = new DrawableObjectsList(ui);        
        playerEquipmentList.setList(ui.game.getPlayer().getEquipmentSlots());
        playerEquipmentList.setIterator(0);
        playerEquipmentList.setDrawPointer(false);
        playerEquipmentList.setName("Equipment");
        playerInventoryItemsList = new DrawableObjectsList(ui);
        playerInventoryItemsList.setList(ui.game.getPlayer().getInInventoryItems());
        playerInventoryItemsList.setIterator(0);
        playerInventoryItemsList.setName("Inventory"); 
        playerInventoryItemsList.setDrawPointer(true);
    }

    public void draw(){
        ui.drawPlayerStatus(0,0, 256, 128, ui.game.getPlayer());
        playerEquipmentList.setList(ui.game.getPlayer().getEquipmentSlots());
        playerEquipmentList.draw(256, 0, 400, 512, 32);
        playerInventoryItemsList.setList(ui.game.getPlayer().getInInventoryItems());
        playerInventoryItemsList.draw(656, 0, 400, 512, 32);
        ui.drawItemStats(256, 512, 786, 256, getPointedItem());
    }

    public void switchPointer(){
        if(pointerState == InventoryMenuPointerState.INVENTORY){
            pointerState = InventoryMenuPointerState.EQUIPMENT;
            playerEquipmentList.setDrawPointer(true);
            playerInventoryItemsList.setDrawPointer(false);
        } else if(pointerState == InventoryMenuPointerState.EQUIPMENT){
            pointerState = InventoryMenuPointerState.INVENTORY;
            playerEquipmentList.setDrawPointer(false);
            playerInventoryItemsList.setDrawPointer(true);
        }
    }

    public Item getPointedItem(){
        if(pointerState == InventoryMenuPointerState.EQUIPMENT){
            EquipmentSlot tempSlot = (EquipmentSlot)playerEquipmentList.getPointedObject();
            return tempSlot.getItem();
        } else {
            return (Item)playerInventoryItemsList.getPointedObject();
        }
    }

    public InventoryMenuPointerState getCurrentState(){
        return pointerState;
    }

    public void increasePointer(){
        DrawableObjectsList increasedList = null;
        if(pointerState == InventoryMenuPointerState.INVENTORY){
            increasedList = playerInventoryItemsList;
        } else if(pointerState == InventoryMenuPointerState.EQUIPMENT){
            increasedList = playerEquipmentList;
        } 
        increasedList.increaseIterator(); 
    }

    public void decreasePointer(){
        DrawableObjectsList decreasedList = null;
        if(pointerState == InventoryMenuPointerState.INVENTORY){
            decreasedList = playerInventoryItemsList;
        } else if(pointerState == InventoryMenuPointerState.EQUIPMENT){
            decreasedList = playerEquipmentList;
        } 
        decreasedList.decreaseIterator(); 
    }

    public EquipmentSlot getPointedEquipmentSlot(){
       if(pointerState == InventoryMenuPointerState.EQUIPMENT){
           return (EquipmentSlot)playerEquipmentList.getPointedObject();
       } 
       return null;
    }

    public Item getPointedInventoryItem(){
        if(pointerState == InventoryMenuPointerState.INVENTORY){
           return (Item)playerInventoryItemsList.getPointedObject();
       } 
       return null;
    }
    
}
