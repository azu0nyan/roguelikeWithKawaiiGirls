package roguelike.ui;

import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nik
 */
public class KeyListenerLoader {
    public static void loadAndSetKeyBindingsFromDB(String DBName, String tableName, GraphicsUI ui) throws Exception{
            //DBName RogueLikeDB.s3db tablename Tiles
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {            
            String keyAction = rs.getString("keyAction");
            String keyName = rs.getString("keyName");
            String interfaceState = rs.getString("interfaceState");
            Boolean isUpdate = rs.getBoolean("isUpdate");
            KeyListener tempKeyListener = new KeyListenerBinded(keyAction, keyName, interfaceState, isUpdate, ui);
            ui.addKeyListener(tempKeyListener);
        }
    }
}
