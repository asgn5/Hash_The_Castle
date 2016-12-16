import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * Sprite class is the base class that all other sprites extend we have our
 * getters and setters here move methods and draw method
 */
public class Sprite {

    /**
     * Instance variables
     */
    public int x, y, width, height, dx, dy;

    /**
     * Keeps track of whether image is visible or not
     */
    public boolean visable;

    /**
     * The image of
     */
    public Image image;


    /**
     * @param x         - X coordinate associated with the image
     * @param y         - Y coordinate associated with the image
     * @param imagePath - Image associated with the sprite
     */
    public Sprite( int x, int y, String imagePath ) {
        this.x = x;
        this.y = y;
        visable = true;
        ImageIcon icon = new ImageIcon( imagePath );
        image = icon.getImage();
        dx = dy = 0;
        width = icon.getIconWidth();
        height = icon.getIconHeight();
    }


    /**
     * @return Gets the X position of sprite
     */
    public int getX() {
        return x;
    }


    /**
     * @param iX sets the X position
     */
    public void setX( int iX ) {
        x = iX;
    }


    /**
     * @returnGets the Y position of sprite
     */
    public int getY() {
        return y;
    }


    /**
     * @param iY - sets the Y position
     */
    public void setY( int iY ) {
        y = iY;
    }


    /**
     * @return whether or not image is visable or not.
     */
    public boolean isVisible() {
        return visable;
    }


    /**
     * @param vis will be set to either true or false. This methods allows you tovisable
     *            set the visablity
     */
    public void setVisible( boolean vis ) {
        visable = vis;
    }


    /**
     * @return Gets the width of the image returns an int
     */
    public int getWidth() {
        return width;
    }


    /**
     * @return gets the height of the image returned in an int
     */
    public int getHeight() {
        return height;
    }


    /**
     * @param num Sets the direction of the sprite on the y axis
     */
    public void setDy( int num ) {
        dy = num;
    }


    /**
     * @return gets the direction of X and the number associated with it
     */
    public int getDx() {
        return dx;
    }


    /**
     * @param num Sets the direction of sprite on the x axis
     */
    public void setDx( int num ) {
        dx = num;
    }


    /**
     * @param rectangle this returns the rectangle that is invisible around the sprite
     *                  if 2 rectangles intersect then we have a collision and something needs to happen
     *                  whether it is end the game or set visibility to arrow and monster to false.
     * @return
     */
    public boolean getCollisison( Rectangle rectangle ) {
        return rectangle.intersects( getBounds() );
    }


    /**
     * @return This method will draw a square around the sprite. The square is
     * invisible but it allows us to detect collisions by doing it this way.
     */
    public Rectangle getBounds() {
        return new Rectangle( x, y, width, height );
    }


    /**
     * This moves the sprite and wont let the sprite leave the screen on any side
     */
    public void move() {
        if ( x + dx > -width / 4 && y + dy > -height / 2 ) {
            if ( ( x + dx < CastleBoard.SCREEN_WIDTH + 1 )
                   && ( y + dy < CastleBoard.SCREEN_HEIGHT - height ) ) {
                x += dx;
                y += dy;
            }
        }
    }


    /**
     * @param g2
     * @param observer Draws the image associated with the Sprite.
     */
    public void draw( Graphics g2, ImageObserver observer ) {
        g2.drawImage( image, x, y, observer );
    }
}