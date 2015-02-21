package roguelike.event;

/**
 *Класс event может содержать в себе несколько типов событий,
 * класс event listener - только один.
 * Если наследник event имеет определенный тип, то он должен
 * реальзовывать соответствующий интерфейс
 * @author nik
 */
public enum EventType {
    NONE,

    CREATURE_GOING_TO_MOVE,//существо собирается переместиться
    CREATURE_MOVED, //существо переместилось

    CREATURE_DAMAGED,//существу нанесен урон
    CREATURE_WEAPON_DAMAGED, //существу нанесен урон оружием
    CREATURE_GOING_TO_CAUSE_WEAPON_DAMAGE,//существо собирается нанести урон

    CREATURE_GOING_TO_PICKUP_ITEM,
    CREATURE_PICKUP_ITEM,
    CREATURE_GOING_TO_DROP_ITEM,
    CREATURE_DROP_ITEM,

    CREATURE_GOING_TO_EQUIP_ITEM,
    CREATURE_EQUIP_ITEM,
    CREATURE_GOING_TO_UN_EQUIP_ITEM,
    CREATURE_UN_EQUIP_ITEM,

    CREATURE_GOING_TO_USE_BODY_PARTS,
    CREATURE_USED_BODY_PARTS,

    CREATURE_GOING_TO_GATHER_ITEMS,//Существо собирается собрать предметы с WorldObject
    CREATURE_GATHERED_ITEMS,
    ITEMS_GATHERED,


    DATE_CAHNGED,
    NEW_MINUTE_STARTED,
    NEW_HOUR_STARTED,
    NEW_DAY_STARTED,
    NEW_MONTH_STARTED,
    NEW_YEAR_STARTED,
    SEASONS_CHANGED

}
