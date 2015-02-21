package roguelike.spell;

/**
 *
 * @author azu
 */
public class EffectCastOverTime extends Effect{
    int time = 10;
    int maxTime = -1;
   
    public EffectCastOverTime(int time,int maxTime){
        super();
        setMaxTime(maxTime);
        setTime(time);
    }
    public EffectCastOverTime(int time){
        this(time, -1);//надеюсь так можно писать
    }
    public void setTime(int time){
        if(maxTime < 0){
            this.time = time;
        } else {
            this.time = Math.min(time, maxTime);
        }
    }
    public int getTime(){
        return time;
    }
    public void addTime(int x){
        if(maxTime < 0){
            this.time += x;
        } else {
            this.time = Math.min(time + x, maxTime);
        }
    }
    public void setMaxTime(int time){
        maxTime = time;
    }
    public int getMaxTime(){
        return time;
    }
    @Override
    public void update(){
        if (time > 0) {
            super.update();
            time--;
        } else {
            disspell();
        }
        
    }
}
