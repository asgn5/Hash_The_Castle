public class Room {

    private String name;
    private int location;

    public Room(String name, int location) {
        this.name = name;
        this.location = location;
    }

    public String getRoomName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name;
    }

}