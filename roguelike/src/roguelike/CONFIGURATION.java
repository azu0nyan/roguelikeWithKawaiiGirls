package roguelike;

/**
 *
 * @author azu
 */
public class CONFIGURATION {

    public static boolean debug = true;

    public static int wallID = 2;
    public static int waterID = 3;

    public static int maxSlotsPerItem = 4;
    public static int maxBodyPartsTagsToLoad = 10;
    public static int maxLinkedBodyPartsToLoad = 10;
    public static int maxInnerBodyPartsToLoad = 10;
    public static int maxDamagesToLoad = 10;

    public static boolean logEvents = true;
    public static boolean logListeners = true;
    public static String dbName = "RogueLikeDB.s3db";
}
