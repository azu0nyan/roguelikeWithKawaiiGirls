package roguelike.ui;

import java.awt.Color;

/**
 *
 * @author nik
 */
public class DrawableInterfacePart {
    private GraphicsUI ui;
    
    public DrawableInterfacePart(GraphicsUI ui){
            this.ui = ui;
    } 
    public GraphicsUI getUI(){
        return ui;
    }
    public void draw(int x, int y, int width, int height){
        ui.fillRect(x, y, height, width, Color.BLACK);
    }
}
