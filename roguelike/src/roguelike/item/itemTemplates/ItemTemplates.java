package roguelike.item.itemTemplates;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 08.10.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public enum ItemTemplates {

    SWORD, TWOHAND_SWORD, SIMPLE_ITEM;



    public ItemTemplate getItemTemplate(){
        ItemTemplate template = null;
        switch (this){
            case SWORD:{
                template = new SwordItemTemplate();
                break;
            }
            case TWOHAND_SWORD:{
                template = new TwoHandSwordItemTemplate();
                break;
            }
            case SIMPLE_ITEM:{
                template = new SimpleItem();
                break;
            }
        }
        return template;
    }

}
