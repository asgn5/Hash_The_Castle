/**
 *
 */
public class Arrow extends Sprite{

    /**
     *
     */
    private final int BOARD_WIDTH = CastleBoard.SCREEN_WIDTH;

    /**
     *
     */
    private final int ARROW_SPEED = 1;

    /**
     * @param x
     * @param y
     * @param imgPath
     */
    public Arrow(int x, int y, String imgPath){
        super(x, y, imgPath);
        setDx(ARROW_SPEED);
    }

    /**
     *
     */
    @Override
    public void move(){
        if (getX() >= BOARD_WIDTH - getWidth())
            visable = false;
        super.move();
    }

}