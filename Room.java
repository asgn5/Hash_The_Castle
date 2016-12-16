/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * The symbolic object of the buckets in the hash table
 */
public class Room {

    /**
     * The name of the room
     */
    private String name;

    /**
     * The location in terms of position
     */
    private int location;


    /**
     * Creates a room with
     * @param name The specified name
     * @param location the specified location
     */
    public Room( String name, int location ) {
        this.name = name;
        this.location = location;
    }


    /**
     * @return The name of the room
     */
    public String getRoomName() {
        return name;
    }


    /**
     * @return The location of the room
     */
    public int getLocation() {
        return location;
    }


    /**
     * @return The string representation of the room.
     */
    @Override
    public String toString() {
        return name;
    }
}