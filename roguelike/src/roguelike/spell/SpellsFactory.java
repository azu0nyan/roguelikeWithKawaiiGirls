package roguelike.spell;


/**
 *
 * @author azu
 */
public class SpellsFactory {
    public static Spell getSpell(int id){ 
        return null;
    }
    public static SpellCastableOnHimself getHimselfHealingSpell(int healValue){
        return new SpellSimpleHeal(healValue);
    }
    
    
   
}
