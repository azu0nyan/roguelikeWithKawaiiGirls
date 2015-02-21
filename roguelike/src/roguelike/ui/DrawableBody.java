package roguelike.ui;

import roguelike.creature.Body;
import roguelike.creature.BodyPart;

import java.awt.*;

/**
 *
 * @author azu
 */
public class DrawableBody extends DrawableInterfacePart{
    Body body;
    int tempDepth = 0;
    int drawedPartsCount = 0;
    
    public DrawableBody(GraphicsUI ui){
        super(ui); 
    }
    @Override
    public void draw(int x, int y, int width, int height) {
        super.draw(x, y, width, height);
        tempDepth = 0;
        drawedPartsCount = 0;
        int left = x + getUI().charInterval / 2;
        int top = y + getUI().charInterval / 2 + getUI().charInterval * drawedPartsCount;
            
        if (body != null) {
            getUI().drawString("BLOOD:" + String.valueOf(body.getBlood())
                    + "/" + String.valueOf(body.getMaxBlood()),
                    left, top, Color.WHITE);
            drawedPartsCount++;
            recursiveDraw(x, y, body.getMainBodyPart());
        } else {
            getUI().drawString("body == NULL  @_@",
                    left, top, Color.WHITE);
            
        }
    }
    public void recursiveDraw(int x, int y, BodyPart bodyPart){
        int left = x + getUI().charInterval / 2 + getUI().charInterval * tempDepth;
        int top = y + getUI().charInterval / 2 + getUI().charInterval * drawedPartsCount;
        if(bodyPart != null){
            if(bodyPart.isAlive()){
                getUI().drawString(bodyPart.toString(), left, top, Color.WHITE);
            } else {
                getUI().drawString(bodyPart.toString(), left, top, Color.RED);
            }
            tempDepth++;
            drawedPartsCount++;
            for(BodyPart tempPart : bodyPart.getInnerBodyParts()){
                recursiveDraw(x,y, tempPart);
            }
            tempDepth--;
            for(BodyPart tempPart : bodyPart.getLinkedBodyParts()){
                recursiveDraw(x,y, tempPart);
            }
        }
        //ui.drawString(name, left, top, Color.WHITE);
    }
    public void setBody(Body body){
        this.body = body;
    }
    public Body getBody(){
        return body;
    }
}
