package roguelike.ui;

import roguelike.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 15.01.14
 * Time: 0:56
 * To change this template use File | Settings | File Templates.
 */
public class CordinatesEnterFrame extends JFrame implements ActionListener {
    JTextField xCord;
    JTextField yCord;
    JButton accept;
    private Game game;
    private GraphicsUI ui;

    public CordinatesEnterFrame(Game game, GraphicsUI ui){
        this.game = game;
        this.ui = ui;
        setSize(200, 200);
        setTitle("ENTER CHUNK CORD");
        //setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setSize(200, 200);
        getContentPane().add(panel);

        xCord = new JTextField("5");
        xCord.setColumns(4);
        panel.add(xCord);
        yCord = new JTextField("5");
        yCord.setColumns(4);
        panel.add(yCord);
        accept = new JButton("ok");
        panel.add(accept);


        accept.addActionListener(this);
        accept.setActionCommand("ACCEPT");

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("ACCEPT".equals(e.getActionCommand())){
            try {
                game.spawnPlayerAndStart(Integer.valueOf(xCord.getText()), Integer.valueOf(yCord.getText()));
                setVisible(false);
                ui.gameStarted();
            } catch (Exception e1) {

            }
        }
    }
}
