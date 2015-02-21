package roguelike.creature;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import roguelike.*;
import roguelike.ai.AI;
import roguelike.event.*;
import roguelike.event.listener.CreatureDecreaseEnduranceBodyPartsUsedListener;
import roguelike.item.Item;
import roguelike.map.Chunk;
import roguelike.objectsAndProperties.LocatedObjectWithProperties;
import roguelike.objectsAndProperties.LocatedObjectWithPropertiesInterface;
import roguelike.objectsAndProperties.ObjectType;
import roguelike.objectsAndProperties.properties.WeaponPropertyInterface;
import roguelike.objectsAndProperties.properties.WorldObjectPropertyInterface;
import roguelike.spell.Effect;
import roguelike.stats.DamageValue;
import roguelike.stats.ItemDefenseStatsInterface;
import roguelike.stats.Stats;
import roguelike.objectsAndProperties.properties.GatheringWorldObjectPropertyInterface;
import roguelike.worldobject.WorldObjectPropertyType;

/**
 *
 * @author nik
 */
public class Creature extends LocatedObjectWithProperties implements LocatedObjectWithPropertiesInterface, CreatureActionInterface{
    private AI ai;
    private Fraction fraction;
    private int type = 1;    
    private int hp = 100;    
    private CreatureStats stats;
    private double size = 0.1;
    private Body body;
    /*
     * TODO переделать поиск пути так чтобы клетки без существ были более приоритетными
    */
    private boolean isDead = false;
    //spelllist
    ArrayList<Effect> effects;
    //droplist
    
    //Важные для правильной работы переменные 
    private int timeAfterLastMovement = 0;
    private int movementDirection = 0;
    private int timeAfterLastAttack = 0;
    private Creature targetToAttack;
    //bool lastAttackHand;
    
    public Creature(TileCordinatesLinkedToChunk tCord){
        super(tCord);
        setSymbol('C');
        setName("creature name");
        setDescription("creature description");

        ai = new AI();
        stats = new CreatureStats(this);
        effects = new ArrayList<Effect>();
        setBaseHP(100);//
        setBaseMovementSpeed(1000);
        body = new Body(this);

        getChunk().addObject(this);
        createListeners();
    }

    private void createListeners(){
        new CreatureDecreaseEnduranceBodyPartsUsedListener(this);

    }

    @Deprecated
    public void event(Event event){
        //рядом открылась дверь/ сущетсво ударили и т.д.
        for(Effect e : effects){
            if(e.isActive()){
                e.event(event);
            }
        } 
    }
    @Deprecated
    public void applyDamage(int dmg){
        hp = Math.max(0, hp - dmg);
        if(hp <= 0){            
            die();
        }
    }

    @Deprecated
    public void applyHealing(int heal){        
        hp = Math.min(hp + heal, stats.getMaxHP());
    }
    
    public void update(int deltaTime){
        increaseTimeAfterLastMovement(deltaTime);
        increaseTimeAfterLastAttack(deltaTime);  
        if(!isDead()){    
            //обновление жффектов происходит ТОЛЬКО здесь
            //System.out.println(String.valueOf(effects.size()));
            for(Effect e : effects){
                if(e.isActive()){
                    e.update();
                } 
            } 
            //удалене закончвшихся эффектов
            int arraySize = effects.size();
            for(int i =0; i < arraySize; i++){
                if(!effects.get(i).isActive()){
                    effects.remove(i);
                    arraySize--;
                }
            }
            updateAI(deltaTime);
            body.update(deltaTime);
        }
    } 
    
    public void applyEffect(Effect effect){       
        if(effect != null){
            effects.add(effect);
            effect.setOwner(this);
        }
    }

    public void dispelEffect(Effect effect){
        for(Effect e : effects){
            if(e == effect){
                e.disspell();
            }
        }
    }
    
    
    public boolean isItemEquiped(Item item){
        //для совместимости
        return false;
    }

    public void recalculateStats(){
        stats.recalculateStats();
    }
    
    public List<Item> getEquipedItemList(){
        return null;
    }
    
    public void setIsDead(boolean isDead){
        this.isDead = isDead;
    }

    public boolean isDead(){
        return isDead;
    }

    ///Fractions
    public boolean isEnemy(Creature c){
        if(fraction != null){
            return fraction.isEnemy(c.getFraction());
        }
        return false;
    }

    public boolean isFriend(Creature c){
        if(fraction != null){
            return fraction.isFriend(c.getFraction());
        }
        return false;
    }

    public boolean isNeutral(Creature c){
        if(fraction != null){
            return fraction.isNeutral(c.getFraction());
        }
        return true;
    }

    public void setFraction(Fraction fraction){
        this.fraction = fraction;
    }

    public Fraction getFraction(){
        return fraction;
    }




    public void setType(int type){
        this.type = type;
    }

    public ObjectType getType(){
        return ObjectType.CREATURE;
    }

    public void setSize(double size){
        this.size = size;
    }

    public double getSize(){
        return size;
    }

    /*public Tile getTile(){
        return getLinkedCordinates().getTile();
    }        */

    public Chunk getChunk(){
        return getLinkedCordinates().getChunk();
    }

    public void processEvent(EventInterface event){
        getChunk().processEvent(event);
    }

    public void moveTo(CreatureGoingToMoveEventInterface goingToEvent){
        if(canMove() &&
                getChunk().canMoveObject(goingToEvent.getEnd(), this) &&
                !isEnemyInTile(this, goingToEvent.getEnd()) &&
                getChunk().noMovementCollisions(getCordinates(), goingToEvent.getEnd(), goingToEvent.getMovementType())){
            TileCordinates start = this.getCordinates();
            //getChunk().getChunk(start).removeObject(this);
            //getChunk().getChunk(goingToEvent.getEnd()).addObject(this);
            getLinkedCordinates().moveTo(goingToEvent.getEnd());

            setMovementDirection(0);
            increaseTimeAfterLastMovement(-stats.getMovementSpeed());
            CreatureMovedEventInterface e = new CreatureMovedEvent(goingToEvent);
            processEvent(e);
        }

    }


    public TileCordinates getCordinates(){
        return getLinkedCordinates();
    }



    public void setAI(AI ai){
        this.ai = ai;
    }
    
    ///////// some private methods/////////////////////////////
    private void die(){
        //действия перед смертью
        //dropList.drop(Tile);
        setIsDead(true);
    }    
    
    private void increaseTimeAfterLastAttack(int deltaTime){
        timeAfterLastAttack += deltaTime;
    }

    private void updateAI(int deltaTime){
        if(ai != null){
            ai.doActions(deltaTime); 
        }
    }

    /////Attack////
    public LinkedList<BodyPart> getOwningBodyParts(Item item) {
        //TODO перенестив Body
        LinkedList<BodyPart> parts = body.getBodyPartsList();
        LinkedList<BodyPart> owningParts = new LinkedList<>();
        for(BodyPart bodyPart : parts){
            if(bodyPart.isItemEquiped(item)){
                owningParts.add(bodyPart);
            }
        }
        return owningParts;
    }

    public WeaponPropertyInterface getBestWeaponToAttack(){
        return stats.getBestWeaponToAttack();
    }

    public List<WorldObjectPropertyInterface> getWeapons() {
        return getPropertiesByType(WorldObjectPropertyType.WEAPON);
    }

    public void damage(CreatureGoingToCauseWeaponDamageEvent damageEvent) {
        if(damageEvent != null){
            DamageValue damageRecived = body.damage(damageEvent.getDamage());
            CreatureWeaponDamagedEvent crDamaged= new CreatureWeaponDamagedEvent(this, damageRecived, damageEvent);
            getChunk().processEvent(crDamaged);
        }
    }
    
    public void onAttack(){
        //TODO написать
        timeAfterLastAttack = 0;
    }

    public boolean canAttack(Creature target){
        if(target != null && Tools.getDistance(getCordinates(),target.getCordinates()) < getAttackRadius()
                && getBestWeaponToAttack() != null){
            return true;
        }
        return false;
    }

    public boolean canAttack(Creature target, WeaponPropertyInterface weapon){
        if(Tools.getDistance(getCordinates(),target.getCordinates()) < getAttackRadius()
                && stats.canAtackUsingWeapon(weapon)){
            return true;
        }
        return false;
    }

    public CreatureGoingToCauseWeaponDamageEvent attack(Creature target, WeaponPropertyInterface weapon){
        if(target != null && canAttack(target, weapon)){
            CreatureGoingToCauseWeaponDamageEvent event = new CreatureGoingToCauseWeaponDamageEvent(
                    this, target, stats.calcDamage(weapon), weapon);
            getChunk().processEvent(event);
            return event;
        }
        return null;
    }

    public CreatureGoingToCauseWeaponDamageEvent attack(Creature target){        
        return attack(target, getBestWeaponToAttack());
    }

    public int getCurrentWeaponSpeed(){
         /**
          * возвращает скорость того оружие которое будет задействованно в атаке
          * TODO наисать
          */
        return 500;
    }

    public int getAttackRadius(){
        //TODO написать 
        return 2;
    }

    public int getTimeAfterLastAttack(){
        return timeAfterLastAttack;
    }

    ////Movement//////

    public boolean canMove(){
         /**
          * Проверяется ТОЛЬКО хватает ли времени на перемещение
          */
        if(getTimeAfterLastMovement() >= getMovementSpeed() && body.getBodyPartsListByTag(BodyPartTag.MOVE).size() > 0){
            return true;
        }
        return false;
    }

    private void increaseTimeAfterLastMovement(int deltaTime){
        //deltaTime может принимать отрицательные значения
        //для того чтобы не терялась скорость из-за разности времени обновления
        if (deltaTime > 0 && timeAfterLastMovement < getMovementSpeed()) {
            timeAfterLastMovement += deltaTime;
        } else if (deltaTime < 0) {
            timeAfterLastMovement = Math.max(timeAfterLastMovement + deltaTime, 0);
        }
        
    }

    public int getTimeAfterLastMovement(){
        return this.timeAfterLastMovement;
    }

    public int getMovementDirection(){
        return movementDirection;
    }

    public void setMovementDirection(int movementDirection){
        this.movementDirection = movementDirection;
    }
    
    public void setBody(Body body){
        this.body = body;
    }

    public Body getBody(){
        return body;
    }

    ///////////////Stats////////////////////////
    public void setBaseStats(Stats stats){
        this.stats.setBaseStats(stats);
    }

    public void setBaseHP(int hp){
        stats.setBaseHP(hp);
    }

    public void setBaseMovementSpeed(int speed){
        stats.setBaseMovementSpeed(speed);
    }

    public int getHP(){
        return hp;
    }

    /*public int getArmor(){
        return stats.getArmor();
    }*/
    public int getMaxHP(){
        return stats.getMaxHP();
    }

    public int getMovementSpeed(){
        return stats.getMovementSpeed();
    }

    public int getMP(){
        return 0;
    }

    public int getMaxMP(){
        return stats.getMaxMP();
    }

    public int getStamina(){
        return stats.getStamina();
    }

    public int getIntellect(){
        return stats.getIntellect();
    }

    public int getAgility(){
        return stats.getAgility();
    }

    public int getStrength(){
        return stats.getStrength();
    }


    /*public int getDamage() {
        return stats.getDamage();
    }*/
    /*public void setDefaultWeapon(Item weapon){
        defaultWeapon  = weapon;
    }       */
    /*public Item getDefaultWeapon(){
        return defaultWeapon;
    }
    public Item getAttackingWeapon() {
        return defaultWeapon;
    }     */
    public ItemDefenseStatsInterface getResists(){
        return (ItemDefenseStatsInterface)stats;
    }

    public double calcEnduranceDecreace(WeaponPropertyInterface weapon) {
        return stats.calcNeededEnduranceToHit(weapon);
    }

    public boolean isEnemyInTile(Creature c, TileCordinates end) {
        //LinkedList<Creature> creatures = getChunk().getTile(end).getCreaturesList();
        List<LocatedObjectWithPropertiesInterface> creatures = getChunk().getObjectsInTile(end, ObjectType.CREATURE);
        if (creatures == null) {
            return false;
        }
        for (LocatedObjectWithPropertiesInterface creature : creatures) {
            if (c.isEnemy((Creature)creature) && !((Creature)creature).isDead()) {
                return true;
            }
        }
        return false;
    }
    //TODO выпилить creatureHumanoid
    public boolean pickUpItem(CreatureGoingToPickUpItemsEventInterface goingToEvent){
        return false;
    }

    public boolean pickUpItem(Item item){
        return false;
    }

    public void gatherItems(CreatureGoingToGatherItemsEventInterface creatureGoingToGatherItemsEvent) {
        ItemsGatheredEventInterface gatheredEvent = creatureGoingToGatherItemsEvent.getGatheringProperty().gather(creatureGoingToGatherItemsEvent.getGatheringItems());
        List<Item> droppedItems = new ArrayList<>();
        for(Item item : gatheredEvent.getItems()){
            if(!pickUpItem(item)){
                item.setOwner(null);
                droppedItems.add(item);
            }
        }
        processEvent(new CreatureGatheredItemsEvent(creatureGoingToGatherItemsEvent, gatheredEvent));
        if(droppedItems.size() > 0){
            for(Item item : droppedItems){
                processEvent(new CreatureDropItemEvent(creatureGoingToGatherItemsEvent, this, item));
            }
        }

    }

    @Override
    public String toString(){
        return "creature:" + getName();
    }

    @Override
    public void actionMove(TileCordinates cord, MovementType type) {
        CreatureGoingToMoveEventInterface e = new CreatureGoingToMoveEvent(this, this.getCordinates(), cord, type);
        processEvent(e);

    }

    @Override
    public void actionAttack(Creature creature) {
        //TODO написать
    }

    @Override
    public void actionGather(GatheringWorldObjectPropertyInterface worldObject, List<Item> items) {
        CreatureGoingToGatherItemsEventInterface e = new CreatureGoingToGatherItemsEvent(this, worldObject, items);
    }


}
