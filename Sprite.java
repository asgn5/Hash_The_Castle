import java.awt.*;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 *
 */
public class Sprite {

    /**
     *
     */
    public int x, y, width, height, dx, dy;

    /**
     *
     */
    public boolean visable;

    /**
     *
     */
    public Image image;

    /**
     * @param x
     * @param y
     * @param imagePath
     */
    public Sprite(int x, int y, String imagePath){
        this.x = x;
        this.y = y;
        visable = true;

        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();
        dx = dy = 0;
        width = icon.getIconWidth();
        height = icon.getIconHeight();
    }

    /**
     * @return
     */
    public int getX(){
        return x;
    }

    /**
     * @return
     */
    public int getY(){
        return y;
    }

    /**
     * @return
     */
    public Image getImage(){
        return image;
    }

    /**
     * @return
     */
    public boolean isVisable(){
        return visable;
    }

    /**
     * @param vis
     */
    public void setVisable(boolean vis){
        visable = vis;
    }

    /**
     * @return
     */
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    /**
     * @return
     */
    public int getWidth() { return width; }

    /**
     * @return
     */
    public int getHeight() { return height; }

    /**
     * @param iX
     */
    public void setX(int iX) { x = iX; }

    /**
     * @param iY
     */
    public void setY(int iY) { y = iY; }

    /**
     * @param num
     */
    public void setDx(int num) {
        dx = num;
    }

    /**
     * @param num
     */
    public void setDy(int num) {
        dy = num;
    }

    /**
     * @return
     */
    public int getDx() { return dx; }

    /**
     * @param rectangle
     * @return
     */
    public boolean getCollisison(Rectangle rectangle) {
        return rectangle.intersects(getBounds());
    }

    /**
     *
     */
    public void move() {
        if (x + dx > -width/4 && y + dy > -height/2) {
            if (x + dx < CastleBoard.SCREEN_WIDTH + 1 && y + dy < CastleBoard.SCREEN_HEIGHT - height) {
                x += dx;
                y += dy;
            }
        }
    }

    /**
     * @param g2
     * @param observer
     */
    public void draw(Graphics g2, ImageObserver observer) {
        g2.drawImage(image, x, y, observer);
    }
}