import java.util.ArrayList;

/**
 *
 */
public class Knight extends Sprite {

    /**
     *
     */
    private ArrayList<Arrow> arrows;

    /**
     * @param x
     * @param y
     * @param imgPath
     */
    public Knight(int x, int y, String imgPath){
        super(x,y, imgPath);
        arrows = new ArrayList<>();
    }

    /**
     * @return
     */
    public ArrayList<Arrow> getArrow(){
        return arrows;
    }

    /**
     * @param path
     */
    public void fire(String path){
        arrows.add(new Arrow(x + width, y + height / 2, path));
    }

}