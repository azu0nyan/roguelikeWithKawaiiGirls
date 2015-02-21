package roguelike.spell;

/**
 *
 * @author nik
 */
public class EffectsFactory {
    public static EffectCastOverTime getHealOverTimetEffect(int turns, int healValue){
        /**
         * возвращает эффект которых хилит healValue
         * в течении turns ходов
         */
        EffectCastOverTime effect = new EffectCastOverTime(turns);
        effect.setOnUpdateSpell(SpellsFactory.getHimselfHealingSpell(healValue));
        return effect;
    }
    public static Effect getHealAllTimeEffect(int value){
        Effect effect = new Effect();
        effect.setOnUpdateSpell(SpellsFactory.getHimselfHealingSpell(value));
        return effect;
    }
    public static Effect getEffect(int id){
        //TODO ниписать эту функцию
        return null;
    }
}
