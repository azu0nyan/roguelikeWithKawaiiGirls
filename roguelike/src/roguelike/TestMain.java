package roguelike;

import it.biobytes.ammentos.Ammentos;
import it.biobytes.ammentos.PersistenceException;
import org.sqlite.SQLiteConnectionPoolDataSource;
import org.sqlite.SQLiteDataSource;
import roguelike.DAO.BodyPartsLoader;
import roguelike.DAO.StupidDataSource;
import roguelike.DAO.TileTypesLoader;
import roguelike.DAO.prototypes.BodyPartPrototype;
import roguelike.Tools;
import roguelike.datetime.Season;
import roguelike.map.MapGeneratorThread;

import javax.sql.PooledConnection;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 22.08.13
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        BodyPartsLoader l = new BodyPartsLoader();
        l.loadBodyPartsTypesFromDB("");
        System.out.println(l.getNextID() + " " + l.getNextConnectedId() + " " + l.getNextTagId());
    }


}
