package roguelike.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author nik
 */
public class DrawableObjectsList {

    private GraphicsUI ui;
    private List<Object> list;
    private int iterator = 0;
    String name = "Some item list";
    private boolean drawPointer = true;
    public DrawableObjectsList(GraphicsUI ui){
            this.ui = ui;
    }

    public void setIterator(int i){
        if(list != null){
            if(i >= 0){
                if( i < list.size() && list.size() != 0){
                    iterator = i;
                } else if(list.size() > 0){
                    iterator = list.size() - 1;
                } else {
                    iterator = 0;
                }
            } else {
                iterator = 0;
            }
        } else {
            iterator = 0;
        }
    }

    public int getIterator(){
        return iterator;
    }

    public void setName(String name){
        this.name = name;
    }

    public void increaseIterator(){
        setIterator(iterator + 1);
    }

    public void decreaseIterator(){
        setIterator(iterator - 1);
    }

    public void setList(List list){
        if(list != null){
            this.list = new ArrayList<Object>();
            this.list.addAll(list);
            //При смене списка итратор указывает на одну и туже позицию
            if((iterator >= list.size()) && (list.size() != 0)){
                setIterator(list.size() - 1);
            } else if(iterator < 0) {
                setIterator(0);
            } else if(list.size() == 0){
                iterator = 0;
            }
        }
    }

    public Object getPointedObject(){
        if(list != null && (list.size() > 0) && (iterator < list.size())){
            return list.get(iterator);
        } 
        return null;
    }

    public int getSize(){
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public void draw(int x, int y, int width, int height, int itemsCount){
        ui.fillRect(x, y, height, width, Color.BLACK);
        int left = x + ui.charInterval / 2;
        int top = y + ui.charInterval / 2;
        ui.drawString(name, left, top, Color.WHITE);
        top += ui.charInterval;
        itemsCount = Math.min(itemsCount, list.size());
        for(int i = 0; i < itemsCount; i++){
            if(i != iterator || !drawPointer){
                ui.drawString(list.get(i).toString(), left, top, Color.WHITE);
            } else {
                ui.drawString(">" + list.get(i).toString(), left, top, Color.WHITE);
            }
            top += ui.charInterval;
        }
    }

    public void setDrawPointer(boolean draw){
        drawPointer = draw;
    }
    
}
