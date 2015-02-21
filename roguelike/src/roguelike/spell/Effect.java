package roguelike.spell;


import roguelike.creature.Creature;
import roguelike.event.Event;


/**
 *
 * @author azu
 */
public class Effect {    
    Creature owner = null;
    boolean isActive = false;
    SpellCastableOnHimself onUpdateSpell = null;
    SpellCastableOnHimself onAppliedSpell = null;
    SpellCastableOnHimself onDisspeledSpell = null;
    String description;
    
    public Effect(){
        
    }
    public void setOwner(Creature c){
        /**
         * вызывает метод onApplied 
         * т.е. может применить заклинание задонное как заклинание вызываемое 
         * при наложении
         */
        //if(owner != null)
        owner = c;        
        onApplied();
    }
    public Creature getOwner(){
        return owner;
    }
    public void setOnAppliedSpell(SpellCastableOnHimself spell){
        onAppliedSpell = spell;
    }
    public void setOnUpdateSpell(SpellCastableOnHimself spell){
        onUpdateSpell = spell;
    }
    public void setDisspeledSpell(SpellCastableOnHimself spell){
        onDisspeledSpell = spell;
    }
    private void onApplied(){
        /**
         * вызывается при вызове setOwner
         */
        isActive = true;
        if(onAppliedSpell != null){
            onAppliedSpell.cast(owner);
        }
    }
    private void onDisspeled(){
        if((onDisspeledSpell != null) && isActive){
            onDisspeledSpell.cast(owner);
        }
        isActive = false;
        owner = null;
    }
    public void disspell(){
        onDisspeled();
    }
    public void update(){        
        if(isActive && (onUpdateSpell != null)){
            onUpdateSpell.cast(owner);
        }
    }
    public void event(Event event){
        
    }
    public boolean isActive(){
        return isActive;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
