package eecs2030.project.Models;

/**
 * Poision, a simple poision to kill a snake.
 */
public class Poision extends Buffer {

    /**
     * Constructor
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Poision(int x, int y) {
        super(x, y);
    }

    /**
     * Copy constructor
     *
     * @param t a Tile
     */
    public Poision(Tile t) {
        this(t.x, t.y);
    }

    /**
     * Add buffer to snake
     * Snake will be killed and game over.
     *
     * @param snake
     */
    @Override
    public void addTo(Snake snake) {
        snake.setAlive(false);
    }

    @Override
    public String toString() {
        return super.toString() + " contains Poision.";
    }
}
