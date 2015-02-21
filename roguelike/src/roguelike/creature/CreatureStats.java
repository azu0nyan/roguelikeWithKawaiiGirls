package roguelike.creature;

import roguelike.item.Item;
import roguelike.objectsAndProperties.properties.StatsObjectPropertyInterface;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;
import roguelike.stats.DamageValue;
import roguelike.stats.ItemDefenseStatsInterface;
import roguelike.stats.Stats;
import roguelike.worldobject.WorldObjectPropertyType;

import java.util.List;

/**
 *
 * @author azu
 */
/**
 * Структура для хранения характеристик существа, содержит методы для
 * рассчета урона/шанса уклонения/процента резиста и т.д. а так же методы
 * подсчитывающие характеристики существа зависящие от осноных характеристик
 */
public class CreatureStats implements ItemDefenseStatsInterface {

    private Stats baseStats;//Базовые характеристики существа, к ним добавляются все остальные
    private Stats realStats;//Реальные характеристики существа = базовые  + характеристики вещей
    private Creature owner;
    /*
     * TODO разработать систему бафов, вероятно вызовом функции
     * StastCreature.increase(stat, value, time) и хранения списка increasedSats
     * для нормальной обработки recalculateStats
     */

    public CreatureStats() {
        baseStats = new Stats();
        realStats = new Stats();
        recalculateStats();
    }

    public CreatureStats(Creature c) {
        this();
        setOwner(c);
    }

    public void setOwner(Creature c) {
        owner = c;
        recalculateStats();
    }

    public void setBaseStats(Stats stats) {
        baseStats = new Stats(stats);
        recalculateStats();
    }

    public void recalculateStats() {
        realStats = new Stats();
        realStats.add(baseStats);
        if (owner != null) {
            List<Item> items = owner.getEquipedItemList();
            if (items != null) {
                for (Item item : items) {
                    WorldObjectPropertyInterface s = item.getPropertyByType(WorldObjectPropertyType.STATS);
                    if(s != null){
                        realStats.add(((StatsObjectPropertyInterface)s).getStats());
                    }
                }
            }
            //increasedStats
        }
    }

    public int getMaxHP() {
        return realStats.hitPoints + realStats.stamina * 10;
    }

    public void setBaseHP(int hp) {
        baseStats.hitPoints = hp;
        recalculateStats();
    }

    public int getMaxMP() {
        return realStats.manaPoints + realStats.intellect * 10;
    }

    public void setBaseMP(int mp) {
        baseStats.manaPoints = mp;
        recalculateStats();
    }

    public int getIntellect() {
        return realStats.intellect;
    }

    public void setBaseIntellect(int intellect) {
        baseStats.intellect = intellect;
    }

    public int getStamina() {
        return realStats.stamina;
    }

    public void setBaseStamina(int stamina) {
        baseStats.stamina = stamina;
    }

    public int getAgility() {
        return realStats.agility;
    }

    public void setBaseAgility(int agility) {
        baseStats.agility = agility;
    }

    public int getStrength() {
        return realStats.strength;
    }

    public void setBaseStrength(int strength) {
        baseStats.strength = strength;
    }
    //public double get critical strike chance

    public void setBaseMovementSpeed(int speed) {
        baseStats.speed = speed;
    }

    public int getMovementSpeed() {
        return realStats.speed;
    }
    /*
     * public int getDamage() { Item attackingWeapon =
     * owner.getAttackingWeapon(); int weaponDamage = 0; if(attackingWeapon !=
     * null){ weaponDamage = attackingWeapon.getDamage(); } else { weaponDamage
     * = 1; } return realStats.strength * 2 + weaponDamage; }
     */
    public DamageValue calcDamage(WeaponPropertyInterface weapon){
        return new DamageValue(weapon.getDamage());
    }

    public int calcDamageToCompare(WeaponPropertyInterface weapon) {
        if (weapon != null && weapon.getDamage() != null) {
            return weapon.getDamage().getSumDamage();
        }
        return 0;
    }

    public double calcNeededEnduranceToHit(WeaponPropertyInterface weapon) {
        if (weapon != null) {
            return weapon.getWeight() * 5;//TODO поменять формулу
        }
        return 0;
    }

    public boolean canAtackUsingWeapon(WeaponPropertyInterface weapon) {
        //проверяем хватит ли у нас выносливости для удара    
        if (weapon != null) {
            List<BodyPart> owningBodyParts = weapon.getOwningBodyParts();
            if (owningBodyParts != null && owningBodyParts.size() > 0) {
                double neededEndurancePerPart = calcNeededEnduranceToHit(weapon) / owningBodyParts.size();
                for (BodyPart bodyPart : owningBodyParts) {
                    if (bodyPart.getEndurance() < neededEndurancePerPart) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public WeaponPropertyInterface getBestWeaponToAttack() {
        List<WorldObjectPropertyInterface> weapons = owner.getPropertiesByType(WorldObjectPropertyType.WEAPON);
        WeaponPropertyInterface bestWeapon = null;
        int maxDamage = 0;
        for (WorldObjectPropertyInterface weapon : weapons) {
            if (canAtackUsingWeapon((WeaponPropertyInterface)weapon)) {
                int damage = calcDamageToCompare((WeaponPropertyInterface)weapon);//лишний раз не считаем урон
                if (damage >= maxDamage) {// >= для того чтобы можно было бить оружием с 0 уроном, хотя зачем...
                    bestWeapon = (WeaponPropertyInterface)weapon;
                    maxDamage = damage;
                }
            }
        }
        return bestWeapon;
    }

    @Override
    public int getNatureResist() {
        return realStats.natureResist;
    }

    @Override
    public int getFrostResist() {
        return realStats.frostResist;
    }

    @Override
    public int getFireResist() {
        return realStats.fireResist;
    }

    @Override
    public int getHolyResist() {
        return realStats.holyResist;
    }

    @Override
    public int getUnholyResist() {
        return realStats.unholyResist;
    }

    @Override
    public int getPhusicalResist() {
        return realStats.phusicalResist;
    }
}
