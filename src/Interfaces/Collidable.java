package Interfaces;

public interface Collidable {

    public Boolean isCollidingWith(Collidable obj);

    public int getLeftBound();

    public int getRightBound();

    public int getUpperBound();

    public int getLowerBound();

}
