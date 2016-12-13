/**
 *
 */
public class Room {

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
    public Room(String name, int location) {
        this.name = name;
        this.location = location;
    }

    /**
     * @return
     */
    public String getRoomName() {
        return name;
    }

    /**
     * @return
     */
    public int getLocation() {
        return location;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

}