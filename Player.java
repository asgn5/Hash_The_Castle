/**
 *
 */
public class Player {

    /**
     *
     */
    private String name;

    /**
     *
     */
    private int location;

    /**
     * @param name
     * @param location
     */
    public Player(String name, int location) {
        this.name = name;
        this.location = location;
    }

    /**
     * @return
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * @param in
     */
    public void setLocation(int in) {
        location = in;
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return location;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return getPlayerName();
    }

    // Useful random number generator for a possible hash function
    //    int x = name.hashCode() + room.getRoomName().hashCode();
    //    x ^= (x << 21);
    //    x ^= (x >>> 35);
    //    x ^= (x << 4);
    //    return x;

}
