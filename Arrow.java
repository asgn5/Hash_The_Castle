/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * Arrow class extends sprite and it builds the arrow that the knight class can shoot
 * Sets the board width to the width of the panel, set the Arrow Speed to move 5 pixel
 * per repaint
 */
public class Arrow extends Sprite {

    /**
     * The boards width, notifies the arrow when to dissapear
     * based on the native resolution
     */
    private final int BOARD_WIDTH = CastleBoard.SCREEN_WIDTH;

    /**
     * The speed the arrows move horizontally.
     */
    private final int ARROW_SPEED = 1;


    /**
     * @param x       this will super to the sprite class to get the knights x and y coordinates
     * @param y       - set the arrows y corrdinate
     * @param imgPath The image that will be associated with the arrow.
     */
    public Arrow( int x, int y, String imgPath ) {
        super( x, y, imgPath );
        setDx( ARROW_SPEED );
    }


    /**
     * this sets the bounds of the arrow if the arrow image goes off of the screen we set it to
     * false.
     */
    @Override
    public void move() {
        if ( getX() >= BOARD_WIDTH - getWidth() )
            visable = false;
        super.move();
    }
}