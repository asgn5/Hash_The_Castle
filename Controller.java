import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * The main control flow of the program. Contains the necessary methods and object references
 * in order to divide the responsibilities of the view ( or views ) on their own.
 * The controller has the reference to the Hash table and has the ability to update its values
 * provided that an other object communicates to this the necessary details.
 */
public class Controller {

    /**
     * The reference to the wonderful hash-table that made this project possible.
     */
    private CastleHashTable< Player, String > table;

    /**
     * The rooms in which represent the buckets of the hash-table
     */
    private Room[] rooms;

    /**
     * The players who get initialized and added to the hash table
     */
    private ArrayList< Player > players;

    /**
     * Names of the rooms
     */
    private String[] roomNames = {
      "Dungeon", "Servant's Room", "Great Hall", "Throne Room", "The Wardrobe"
    };

    /**
     * Names to enable randomly generated first and last names for the creation of players
     */
    private final String[] names = {
      "Bosko", "Porky", "Pig", "Daffy", "Duck", "Elmer", "Fudd", "Bugs", "Bunny", "Henry", "Hawk",
      "Tweety", "Bertie", "Hubie", "The", "Three", "Bears", "Pepe", "Le", "Pew", "Sylvester", "Yosemite",
      "Sam", "Gossamer", "Foghorn", "Leghorn", "The", "Goofy", "Gophers", "Hippety", "Hopper", "Marvin",
      "Martian", "Road", "Runner", "Wile", "E", "Coyote", "Sylvester", "Jr", "Ralph", "Wolf", "Sam",
      "Sheepdog", "Marc", "Anthony", "Speedy", "Gonzales", "Tasmanian", "Devil", "Witch",
      "Hazel", "Michael", "J", "Frog", "Cool"
    };


    /**
     * The initial position for the graphical display of the monsters
     */
    private int[][] pos;

    /**
     * The reference to the player who is being controlled by the user.
     */
    private Knight knight;

    /**
     * Graphical monsters who are trying to squash the knight and
     *   make it difficult for the user to "win" the game
     */
    private ArrayList< Monster > monsters;

    /**
     * Useful conveniences for knowing when the game is being played
     * and hasWon keeps track of whether a player has won the game.
     */

    private boolean playOn, hasWon;

    /**
     * Retains the control of the game by being the object responsible of
     *  delegating the objects "abilities" such as those to "move" "fire arrows" "win"
     */
    public Controller() {
        rooms = new Room[ 5 ];
        table = new CastleHashTable<>( 5 );
        players = new ArrayList<>();
        /* initializing rooms with specified names from roomNames */
        for ( int i = 0; i < rooms.length; i++ )
            rooms[ i ] = new Room( roomNames[ i ], i );
        Random rand = new Random();
        players.add( new Player( "TEMP" ) );
        for ( int k = 1; k < 15; k++ ) {
            int first = rand.nextInt( names.length );
            int last = rand.nextInt( names.length );
            Player tmp = new Player( names[ first ] + " " + names[ last ] );
            players.add( tmp );
            table.put( tmp, tmp.toString() );
        }
        for ( Player p : players ) {
            System.out.println( p.hashCode() + "\n" );
        }
    }

    /**
     * @param i
     * @return returns the string representation of the players in the room.
     */
    public String getPlayersInRoom( int i ) {
        return table.playersInRoom( rooms[ i ] );
    }

    /**
     * Recomputes the players hashcode value to adjust their position in the hashtable
     * @param player The player to move to a new location in the table
     */
    public void move( Player player ) {
        table.remove( player );
        player.setLocation( player.hashCode()*player.hashCode() );
        table.put( player, player.toString() );
    }

    /**
     * initializes the board with several values regarding the flow of the game.
     * is called at the beginning of the game and at any subsequent restart.
     * creates a new knight, a list of monsters and initializes haswon to false
     * adnnnnnnnnnnnnnnnnd PLAYYYYYYYYYYYY ON...................
     */
    public void initBoard() {
        hasWon = false;
        playOn = true;
        knight = new Knight( 40, 60, "knight.gif" );
        monsters = new ArrayList<>();
        for ( int[] p : pos )
            monsters.add( new Monster( p[ 0 ], p[ 1 ], "monster.gif" ) );
    }

    /**
     * @return returns the "user" at the 0th position in the array
     */
    public Player getPlayer() {
        return players.get( 0 );
    }

    /**
     * @return Displays the entire contents of the table and the players that it includes.
     */
    public String tableDisplayAll() {
        return table.displayAll( rooms );
    }

    /**
     * Returns reference to all the players int the hashTable
     * @return an array of player objects
     * Used in the display dialog to find a particular player
     */
    public String[] getAllPlayers() {
        String[] thePlayers = new String[ players.size() ];
        for ( int i = 0; i < players.size(); i++ ) {
            thePlayers[ i ] = players.get( i ).getPlayerName();
        }
        return thePlayers;
    }

    /**
     * Used in the same dialog as the above method
     * Returns the room-name corresponding to the players string form
     * @param name The name of the player
     * @return the name of the room or not found
     */
    public String getPlayerRoom( String name ) {
        for ( int i = 0; i < players.size(); i++ ) {
            if ( players.get( i ).getPlayerName().equals( name ) ) {
                int temp = players.get( i ).hashCode()%table.size();
                if (temp < 0) temp += table.size();
                return getRoomName(temp);
            }
        }
        return "Not found"; // this should never be the case.
    }

    /**
     * Returns the roomname corresponding to the index where it is located.
     * @param i the index
     * @return the room name
     */
    public String getRoomName( int i ) {
        return rooms[ i ].getRoomName();
    }

    /**
     * adds the "User to the array list to keep track of them.
     * then adds them to the hashTable
     */
    public void setUser( String name ) {
        players.set( 0, new Player( name ) );
        table.put( players.get( 0 ), players.get( 0 ).getPlayerName() );
    }

    /**
     * @return The number of monsters in the list.
     * useful to know if the game is over.
     */
    public int getMonsterCount() {
        return monsters.size();
    }

    /**
     * @return Is the game still running
     */
    public boolean isPlayOn() {
        return playOn;
    }

    /******************** For strictly gui control flow *********************/
    /**
     * Forces the monsters to retain some sort of cohesion to their positioning
     * @param x the windows furthest x point
     * @param y the windows furthest y point
     */
    public void setPos( int x, int y ) {
        int x1 = x - 200;
        int x2 = x - 100;
        int x3 = x - 50;
        y = y - 100;
        Random r = new Random();
        int[][] pos2 = {
          { x1, r.nextInt( y - 100 ) }, { x1, r.nextInt( y - 100 ) }, { x1, r.nextInt( y - 100 ) }, { x1, r.nextInt(
          y - 100 ) },
          { x1, r.nextInt( y - 100 ) }, { x1, r.nextInt( y - 100 ) }, { x2, r.nextInt( y - 100 ) }, { x2, r.nextInt(
          y - 100 ) },
          { x2, r.nextInt( y - 100 ) }, { x2, r.nextInt( y - 100 ) }, { x2, r.nextInt( y - 100 ) }, { x2, r.nextInt(
          y - 100 ) },
          { x2, r.nextInt( y - 100 ) }, { x1, r.nextInt( y - 100 ) }, { x3, r.nextInt( y - 100 ) }, { x3, r.nextInt(
          y - 100 ) },
          { x3, r.nextInt( y - 100 ) }, { x3, r.nextInt( y - 100 ) }, { x2, r.nextInt( y - 100 ) }, { x1, r.nextInt(
          y - 100 ) },
          { x3, r.nextInt( y - 100 ) }, { x2, r.nextInt( y - 100 ) }, { x1, r.nextInt( y - 100 ) }, { x3, r.nextInt(
          y - 100 ) },
          { x3, r.nextInt( y - 100 ) }
        };
        pos = pos2;
    }
    /**
     * @return Whether a player has won the game or not
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * Calls the fire method of the reference to the knight object
     */
    public void knightFire() {
        knight.fire( "arrow.png" );
    }

    /**
     * @param i The new horizontal speed of the knight
     */
    public void setKnightDx( int i ) {
        knight.setDx( i );
    }

    /**
     * @param i The new vertical speed of the knight
     */
    public void setKnightDy( int i ) {
        knight.setDy( i );
    }

    /**
     * @param g2 The painting pen to draw the incredible designs we came up with
     * @param observer the panel that is observing the images.
     */
    public void drawComponents( Graphics2D g2, ImageObserver observer ) {
        if ( knight.isVisible() )
            knight.draw( g2, observer );
        for ( Arrow arrow : knight.getArrow() )
            if ( arrow.isVisible() ) arrow.draw( g2, observer );
        for ( Monster m : monsters )
            if ( m.isVisible() ) m.draw( g2, observer );
    }

    /**
     * Controls the movement of the sprites
     * including the monsters updating if they have been hit by arrows
     * or if the knight is to move or is shooting arrows.
     */
    public void controlFlow() {
        if ( monsters.isEmpty() ) {
            if ( getPlayersInRoom( 4 ).contains( table.get( players.get( 0 ) ) ) ) {
                hasWon = true;
                playOn = false;
                move( players.get( 0 ) );
                return;
            } else {
                move( players.get( 0 ) );
                initBoard(); // initializes the board.
            }
        }
        knight.move();
        ArrayList< Arrow > arrows = knight.getArrow();
        for ( int i = 0; i < arrows.size(); i++ ) {
            if ( arrows.get( i ).isVisible() ) arrows.get( i ).move();
            else arrows.remove( i );
        }
        for ( int i = 0; i < monsters.size(); i++ ) {
            Monster m = monsters.get( i );
            if ( m.isVisible() ) m.move();
            else monsters.remove( i );
        }
        checkCollisions(); //checks to see if something needs to disappear
    }

    /**
     * The mechanism for collision detection in this game
     * we discovered that we could set the sprites image sizes to bounds
     * that we could then call "intersects" on to determine if they had collided
     */
    public void checkCollisions() {
        for ( Monster m : monsters ) {
            if ( knight.getCollisison( m.getBounds() ) ) {
                knight.setVisible( false );
                m.setVisible( false );
                playOn = false;
            }
        }
        ArrayList< Arrow > a = knight.getArrow();
        for ( Arrow a1 : a ) {
            for ( Monster mon : monsters ) {
                if ( mon.getCollisison( a1.getBounds() ) ) {
                    a1.setVisible( false );
                    mon.setVisible( false );
                    if ( monsters.size() % 2 == 0 )
                        move( players.get( ( int ) ( 1 + Math.random() * 13 ) + 1 ) );
                    // the above moves players randomly any time there are an even number of monsters
                }
            }
        }
    }
}
