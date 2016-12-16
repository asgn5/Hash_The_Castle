import java.util.ArrayList;

/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * Knight is the controllable character in the game. It extends sprite so it
 * can access Sprites methods and variables
 */
public class Knight extends Sprite {

    /**
     * A list of arrow objects
     */
    private ArrayList< Arrow > arrows;


    /**
     * @param x       - coordinate associated with Sprites x
     * @param y       - coordinate associated with Sprites y
     * @param imgPath - Image associated with the knight sprite
     */
    public Knight( int x, int y, String imgPath ) {
        super( x, y, imgPath );
        arrows = new ArrayList<>();
    }


    /**
     * We build an array list of Arrows. This will allow us to keep track
     * of the arrows on screen vs. off screen.
     *
     * @return the list of all arrows
     */
    public ArrayList< Arrow > getArrow() {
        return arrows;
    }


    /**
     * @param path fire sets the bounds of the arrows and where they will be visible
     *             on the screen and when an arrow is fired it adds a new arrow object to the arrayList
     */
    public void fire( String path ) {
        arrows.add( new Arrow( x + width, y + height / 2, path ) );
    }
}