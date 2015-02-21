package roguelike;

/**
 * Created with IntelliJ IDEA.
 * @author azu
 * Date: 07.10.13
 * Time: 3:13
 * To change this template use File | Settings | File Templates.
 */
public class Triple <F, M, S> {
    private F first;
    private M second;
    private S third;

    public Triple(F first, M second, S third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(M second){
        this.second = second;
    }
    public void setThird(S third) {
        this.third = third;
    }

    public F first() {
        return first;
    }

    public M second(){
        return second;
    }

    public S third() {
        return third;
    }

    public String toString(){
        return "(" + (first() == null?"null":first().toString()) + "," + (second() == null?"null": second().toString()) + "," + (third() == null?"null":third().toString()) + ")";
    }
}