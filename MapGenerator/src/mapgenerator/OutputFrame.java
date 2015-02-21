/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator;

import mapgenerator.ChunkGenerator.ChunkMapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 *
 * @author azu
 */
public class OutputFrame extends JFrame{
    
    private Font font;
    public int charInterval;
    public Graphics g;
    ChunkMapGenerator generator;
    
    public OutputFrame(){
        super();
        //Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(1280, 1024);
        setVisible(true);
        createBufferStrategy(2);
        font = new Font("Lucida Console", Font.BOLD, 10);
        charInterval = 2;   
        main();
    }
    public void main(){
        generator = new ChunkMapGenerator();
        generator.generate();
        System.out.println("Generated");
        /*try {
            Thread.currentThread().sleep(256);
        } catch (InterruptedException ex) {
            Logger.getLogger(OutputFrame.class.getName()).log(Level.SEVERE, null, ex);
        }*/
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
            for(int i = 0; i < 512; i++){
                for(int j = 0; j < 512; j++){
                    if(generator.chunks[i][j].isGenerated){
                        if(generator.chunks[i][j].isOcean){
                            fillRect(i * charInterval, j * charInterval, 
                                    charInterval, charInterval, Color.BLUE);
                        } else {
                            int height = generator.chunks[i][j].height;
                            if(height >= 0 ){
                                if(height < 256){
                                    fillRect(i * charInterval, j * charInterval, 
                                            charInterval, charInterval, new Color(Math.min(255 , height ), 
                                            0 , 0));
                                } else {
                                    fillRect(i * charInterval, j * charInterval, 
                                            charInterval, charInterval, new Color(255, 
                                            0 , Math.min(255 , height - 255 )));
                                }
                        
                            } else {
                                fillRect(i * charInterval, j * charInterval, 
                                        charInterval, charInterval, Color.BLACK);
                            }
                       }  
                    } else {
                        drawChar('x',i * charInterval, j * charInterval);
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
