/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * The MONSTER....
 * extends the functionality of the sprite
 */
public class Monster extends Sprite {

    /**
     * The reference to the screens maximum width.
     */
    private final int INITIAL_X = CastleBoard.SCREEN_WIDTH;


    /**
     * @param x       - X coordinate associated with the monster
     * @param y       - Y coordinate associated with the monster
     * @param imgPath - Image associated with the monsters
     *                setDx(-4) moves the monsters from the right of the screen to the left of the screen
     *                4 pixels at a time.
     */
    public Monster( int x, int y, String imgPath ) {
        super( x, y, imgPath );
        setDx( -1 );
    }


    /**
     * We set its X position to be at the ScreenWidth (meaning the very right hand side of the screen)
     */
    @Override
    public void move() {
        if ( getX() <= 0 )
            setX( INITIAL_X );
        setX( getX() + getDx() );
    }
}
