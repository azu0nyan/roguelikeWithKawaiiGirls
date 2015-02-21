package roguelike;

import roguelike.DAO.TileTypesLoader;
import roguelike.ui.GraphicsUI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author azu
 */
public class Roguelike {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            Game game = new Game();
            //GameInterface gameInterface = new GameInterface(game);
            game.init();
            new GraphicsUI(game);
            //gameInterface.gameInterfaceMain();
        } catch (Exception ex) {
            Logger.getLogger(TileTypesLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            Thread.currentThread().sleep(5000);
        }
    }
}
