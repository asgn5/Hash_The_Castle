/**
 *
 */
public class Monster extends Sprite{

    /**
     *
     */
    private final int INITIAL_X = CastleBoard.SCREEN_WIDTH;

    /**
     * @param x
     * @param y
     * @param imgPath
     */
    public Monster(int x, int y, String imgPath){
        super(x,y, imgPath);
        setDx(-1);
    }

    /**
     *
     */
    @Override
    public void move(){
        if (getX() <=  0) {
            setX(INITIAL_X);
        }
        setX(getX() + getDx());
    }
}
