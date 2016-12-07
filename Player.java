/* Should the Player have a reference to the room?? */

public class Player {
    
    private String name;
//    private Room room;
    private int where;

    public Player(String name, int where) {
        this.name = name;
//        this.room = room;
        this.where = where;
    }

    public String getPlayerName() {
        return name;
    }

    public void setWhere(int in) {
        where = in;
    }

    @Override
    public int hashCode() {
        return where;
    }

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
