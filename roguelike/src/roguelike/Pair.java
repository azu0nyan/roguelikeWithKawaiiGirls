package roguelike;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 14.08.13
 * Time: 2:19
 * To change this template use File | Settings | File Templates.
 */

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    public String toString(){
        return "(" + (first() == null?"null":first().toString()) + "," + (second() == null?"null":second().toString()) + ")";
    }
}

