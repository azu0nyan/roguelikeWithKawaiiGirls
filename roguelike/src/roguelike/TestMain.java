package roguelike;

import roguelike.DAO.BodyPartsLoader;

/**
 * Created with IntelliJ IDEA.
 * @author azu
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
