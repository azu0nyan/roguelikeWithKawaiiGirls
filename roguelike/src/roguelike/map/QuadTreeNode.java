package roguelike.map;

import roguelike.Cordinates;
import roguelike.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by azu on 21.02.2015.
 * /\y
 * |
 * |
 * |
 * |
 * --------------->x
 */

public class QuadTreeNode<C extends Cordinates, E> {
    private QuadTreeNode topLeft;
    private QuadTreeNode topRight;
    private QuadTreeNode botLeft;
    private QuadTreeNode botRight;
    private double top;
    private double bot;
    private double right;
    private double left;
    private static int maxObjects = 50;
    List<Pair<C, E>> objects;

    public QuadTreeNode(double top, double bot, double left, double right) {
        setBorders(top, bot, left, right);
    }

    public QuadTreeNode(double top, double bot, double left, double right, List<Pair<C, E>> objects) {
        setBorders(top, bot, left, right);
        this.objects = new ArrayList<>();
        this.objects.addAll(objects);
    }
    private void setBorders(double top, double bot, double left, double right){
        setTop(top);
        setBot(bot);
        setLeft(left);
        setRight(right);
    }

    public synchronized void add(Pair<C, E> object) {
        if (object.first().getX() < left || object.first().getX() > right || object.first().getY() > top || object.first().getY() < bot) {
            left = Math.min(object.first().getX(), left);
            right = Math.max(object.first().getX(), right);
            bot = Math.min(object.first().getY(), bot);
            top = Math.max(object.first().getY(), top);
            if (!isLeaf()) {
                topLeft.setTop(top);
                topLeft.setLeft(left);
                topRight.setTop(top);
                topRight.setRight(right);
                botLeft.setBot(bot);
                botLeft.setLeft(left);
                botRight.setRight(right);
                botRight.setBot(bot);
            }
        }
        if (isLeaf()) {
            if (objects == null) {
                objects = new ArrayList<>();
                objects.add(object);
            } else if (objects.size() < maxObjects) {
                objects.add(object);
            } else if (dismemberTree()) {
                add(object);
            } else {
                objects.add(object);
            }
        } else {
            if(object.first().getX() < topLeft.getRight() && object.first().getY() > topLeft.getBot()){
                topLeft.add(object);
            } else if (object.first().getX() >= topRight.getLeft() && object.first().getY() > topRight.getBot()){
                topRight.add(object);
            } else if(object.first().getX() < botLeft.getRight() && object.first().getY() < botLeft.getTop()) {
                botLeft.add(object);
            } else if (object.first().getX() >= botRight.getLeft() && object.first().getY() < botRight.getTop()){
                 botRight.add(object);
            } else {
                throw new RuntimeException("WHAt A UGLY TREE");
            }
        }
    }

    public boolean dismemberTree() {
        objects.sort((o1, o2) -> ((o1.first().getX() == o2.first().getX())?0:(o1.first().getX() < o2.first().getX())?-1:1));
        double middleX = objects.get(objects.size()/2).first().getX();
        ArrayList<Pair<C, E>>  l = objects.stream().filter(e -> e.first().getX() < middleX).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Pair<C, E>>  r = objects.stream().filter(e -> e.first().getX() >= middleX).collect(Collectors.toCollection(ArrayList::new));;
        if(l.size() == 0 || r.size() == 0) {
            return false;
        }

        l.sort((o1, o2) -> ((o1.first().getY() == o2.first().getY()) ? 0 : (o1.first().getY() < o2.first().getY()) ? -1 : 1));
        double middleLY = l.get(l.size()/2).first().getY();
        ArrayList<Pair<C, E>>  tl = l.stream().filter(e -> e.first().getY() > middleLY).collect(Collectors.toCollection(ArrayList::new));
        topLeft = new QuadTreeNode(top, middleLY, left, middleX, tl);
        ArrayList<Pair<C, E>>  bl = l.stream().filter(e -> e.first().getY() <= middleLY).collect(Collectors.toCollection(ArrayList::new));
        botLeft = new QuadTreeNode(middleLY, bot, left, middleX, bl);

        r.sort((o1, o2) -> ((o1.first().getY() == o2.first().getY()) ? 0 : (o1.first().getY() < o2.first().getY()) ? -1 : 1));
        double middleRY = r.get(l.size()/2).first().getY();
        ArrayList<Pair<C, E>>  tr = r.stream().filter(e -> e.first().getY() > middleRY).collect(Collectors.toCollection(ArrayList::new));
        topRight = new QuadTreeNode(top, middleRY, middleX, right, tr);
        ArrayList<Pair<C, E>>  br = r.stream().filter(e -> e.first().getY() <= middleRY).collect(Collectors.toCollection(ArrayList::new));
        botRight = new QuadTreeNode(middleRY, bot, middleX, right, br);
        return false;
    }


    public boolean isLeaf() {
        return topLeft == null;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        if (!isLeaf()) {
            topLeft.setTop(top);
            topRight.setTop(top);
        }
        this.top = top;
    }

    public double getBot() {
        return bot;
    }

    public void setBot(double bot) {
        if (!isLeaf()) {
            botLeft.setBot(bot);
            botRight.setBot(bot);
        }
        this.bot = bot;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        if (!isLeaf()) {
            topLeft.setRight(right);
            botRight.setRight(right);
        }
        this.right = right;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        if (!isLeaf()) {
            topLeft.setLeft(left);
            botLeft.setLeft(left);
        }
        this.left = left;
    }
}
