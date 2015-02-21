/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelike.worldgenerator;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 *
 * @author nik
 */
public class WorldGeneratorOutputFrame extends JFrame{
    
    private Font font;
    public int charInterval;
    public Graphics g;

    int x = 1025;
    int y = 1025;
    private  Generator generator;

    public WorldGeneratorOutputFrame(){
        super();
        //Frame settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(1920, 1080);
        setVisible(true);
        createBufferStrategy(2);
        font = new Font("Lucida Console", Font.BOLD, 10);
        charInterval = 1;
        generator = new Generator(x, y);
        main();
    }

    public void main(){
        System.out.println("Generated");

        while(true){
            draw();
        }
    }
    public void draw(){
          BufferStrategy bs = getBufferStrategy();
        //Graphics g = null;
        try {                     
            //g.setFont();
            g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1280, 1024);
            g.setFont(font);
            for(int i = 0; i < x; i++){
                for(int j = 0; j < y; j++){
                    double height = generator.getXYHeight(i, j) / 2;
                    if(height > 0 ){
                        fillRect(i * charInterval, j * charInterval, charInterval, charInterval,
                                    new Color(Math.min(255 , (int)height ), Math.min(255 , (int)height ) , Math.min(255 , (int)height )));
                    } else if (height == 0){
                        fillRect(i * charInterval, j * charInterval,
                                charInterval, charInterval, Color.ORANGE);
                    } else {
                        fillRect(i * charInterval, j * charInterval,
                                charInterval, charInterval, Color.BLUE);
                    }
                }
            }
        } finally {
            g.dispose();
        }
        bs.show();
    }
    
    public void fillRect(int x,int y, int dx, int dy, Color color){
        g.setColor(color);
        g.fillRect(x, y, dx, dy);
    }
    public void drawChar(char ch, int x, int y, Color color){
        g.setColor(color);
        g.drawString(String.valueOf(ch), x, y + charInterval);
    }
    public void drawChar(char ch, int x, int y){
        drawChar(ch, x, y, Color.WHITE);
    }
    public void drawString(String str, int x, int y, Color color){
        g.setColor(color);
        g.drawString(str, x, y + charInterval);
    }
}
