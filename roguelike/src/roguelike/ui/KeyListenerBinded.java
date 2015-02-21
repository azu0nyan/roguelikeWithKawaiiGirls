package roguelike.ui;

import roguelike.ui.keyAction.KeyAction;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nik
 */
public class KeyListenerBinded extends KeyAdapter{
    char key = 0;
    int code = -1;
    GraphicsUI ui;
    KeyAction action;
    GameInterfaceState interfaceState = GameInterfaceState.ALL;
    boolean isUpdate = false;
    public KeyListenerBinded(char key, GraphicsUI ui){
        super();
        setKey(key);
        setUI(ui);
    }
    public KeyListenerBinded(int code, GraphicsUI ui){
        super();
        setCode(code);
        setUI(ui);
    }
    
    public KeyListenerBinded(String actionName, String keyName, String interfaceState, boolean isUpdate, GraphicsUI ui){
        super();
        setCode(code);
        setUI(ui);
        try {
            //KeyEvent.vk_l
            setCode(KeyEvent.class.getDeclaredField(keyName).getInt(KeyEvent.class));
        } catch (NoSuchFieldException ex) {
            System.out.println("No key named " + keyName);
        } catch (SecurityException ex) {
            System.out.println("Security exception while while getting keyCode " + keyName);
        } catch (IllegalAccessException ex){
            System.out.println("IllegalAccessExceptionwhile while getting keyCode " + keyName);
        } 
        try {
            Class exitActionClass = Class.forName("roguelike.ui.keyAction." + actionName);
            KeyAction tempAction = (KeyAction)exitActionClass.newInstance();
            setAction(tempAction);
        } catch (Exception ex) {
            System.out.println("Exception while loading key action " + actionName);
            Logger.getLogger(GraphicsUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        this.interfaceState = GameInterfaceState.valueOf(interfaceState);
        this.isUpdate = isUpdate;
    }
    
    @Override
    public void keyPressed(KeyEvent event){
        if(action!= null && (
                (interfaceState == GameInterfaceState.ALL) || 
                (interfaceState == ui.interfaceState)) && 
                (event.getKeyCode() == code ||  event.getKeyChar() == key)){
            action.doAction(ui);
            if(isUpdate){//TODO ....
               // ui.game.update();
            }
        }
        
    }
    public void setKey(char key){
        this.key = key;        
    }
    public void setCode(int code){
        this.code = code;
    }
    public void setUI(GraphicsUI ui){
        this.ui = ui;
    }
    public void setAction(KeyAction action){
        this.action = action;
    }
}
