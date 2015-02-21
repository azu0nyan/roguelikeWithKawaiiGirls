package roguelike;

/**
 *
 * Класс занимается обновлением объектов/мобов
 * в будущем должен будет следить за потоками обновления
 * @author azu
 */
public class ThreadGameLoop extends Thread{
    Game game;
    long lastUpdateTime = 0;
    long deltaTime = 0;
    public boolean isPause = false;    
    public boolean printDeltaTime = false;//4debug
    
    public ThreadGameLoop(Game game){
        super("Game loop");
        this.game = game;
    }

    @Override
    public void run() {
        System.out.println("Game loop started");
        lastUpdateTime = System.currentTimeMillis();
        while(!game.isExit){
            if(!isPause){
                deltaTime = System.currentTimeMillis() - lastUpdateTime;
                if(printDeltaTime){//4debug
                    System.out.println(String.valueOf(deltaTime));
                }
                lastUpdateTime = System.currentTimeMillis();                
                game.update(deltaTime);
                long updaitingTime = System.currentTimeMillis() - lastUpdateTime;
                if(updaitingTime < 100){
                    try {
                        Thread.currentThread().sleep(100 - updaitingTime);//Примерно 10 обновлений в секунду
                    } catch (InterruptedException ex) {
                        System.out.println("game loop interrupt exception");
                    }
                }
            }
            //else sleep(100)
        }
    }

    public void pause_(){
        if(!isPause){
            deltaTime += System.currentTimeMillis() - lastUpdateTime;
            isPause = true;
            //game.getEventsManager().pause_();
        }
    }

    public void continue_(){
        if(isPause){
            lastUpdateTime = System.currentTimeMillis();
            isPause = false;
            //game.getEventsManager().continue_();
        }
    }
    
}
