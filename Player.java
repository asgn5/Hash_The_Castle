/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 *
 * The player who has the role of being both an active participant in the visual game
 * as well as the underlying data logic of the hash table
 */
public class Player {

    /**
     * the location which is the seed to our hash function
     * increments by the size of the table everytime a player is created.
     */
    private static int LOCATION = 0;

    /**
     * The string representation of the player
     */
    private String name;

    /**
     * The location being the seed of its hashcode.
     */
    private int location;


    /**
     * Creates a player with
     * @param name The provided name
     * Increments the LOCATION ( seed to hashcode ) every time an object created.
     */
    public Player( String name ) {
        LOCATION += 5;
        this.name = name;
        location = LOCATION;
    }


    /**
     * Sets the location of the player to a new seed value
     * @param in
     */
    public void setLocation( int in ) {
        location = in;
    }


    /**
     * Seeded with the players location which
     * Utilizes a relatively new discovery of using xor as a sudo-random number generator
     * @return The HASHCODE, the apex of this entire assignment.
     * If you are reading this, we hope you find it interesting.
     */
    @Override
    public int hashCode() {
        int x = location * name.length();
        x ^= ( x << 21 );
        x ^= ( x >>> 35 );
        x ^= ( x << 4 );
        return x + 1;
    }


    /**
     * @return The players name
     */
    @Override
    public String toString() {
        return getPlayerName();
    }


    /**
     * @return The players name, helpful to tell read rather than toString
     */
    public String getPlayerName() {
        return name;
    }
}
