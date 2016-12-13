import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class Controller {

    /**
     *
     */
    private final int[][] pos = {
        {1200, 29}, {1200, 90}, {1200, 150},
        {1200, 155}, {1200, 175}, {1200, 95},
        {1300, 100}, {1300, 145}, {1300, 120},
        {1300, 180}, {1300, 175}, {1300, 195},
        {1300, 200}, {1200, 225}, {1400, 240},
        {1400, 265}, {1400, 275}, {1400, 285},
        {1300, 550}, {1200, 400}, {1400, 650},
        {1300, 700}, {1200, 700}, {1400, 700},
        {1400, 700}

    };
    /**
     *
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
     *
     */
    private CastleHashTable<Player, String> table;
    /**
     *
     */
    private Room[] rooms;
    /**
     *
     */
    private Player[] player;
    /**
     *
     */
    private Knight knight;
    /**
     *
     */
    private ArrayList<Monster> monsters;
    /**
     *
     */
    private boolean playOn;
    /**
     * Names of the rooms
     */
    private String[] roomNames = {
        "Dungeon", "Servant's Room", "Great Hall", "Throne Room", "The Wardrobe"
    };

    /**
     *
     */
    private ArrayList<Player> players;

    /**
     * Makes sure to allow for unique key distribution.
     */
    private ArrayList<Integer> nextKeys;

    /**
     *
     */
    public Controller() {
        rooms = new Room[5];
        table = new CastleHashTable<>(5);
        player = new Player[15];
        nextKeys = new ArrayList<>();

        players = new ArrayList<>();
        for (int theKeys = 0; theKeys < 15; theKeys++) {
            nextKeys.add(theKeys); // sets the index value to theKeys
        }

        /* initializing rooms with specified names from roomNames */
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Room(roomNames[i], i);
        }

        Random rand = new Random();
        //index 0 should be reserved for first player.
        for (int k = 0; k < 15; k++) {
            String aName = names[rand.nextInt(names.length)] + " " + names[rand.nextInt(names.length)];
            int num = rand.nextInt(k + 1);
            players.add(new Player(aName, num));
            table.put(players.get(k), players.get(k).toString());
            nextKeys.set(num, nextKeys.get(k) * nextKeys.get(k));
        }
    }

    /**
     *
     */
    public void initBoard() {
        playOn = true;
        knight = new Knight(40, 60, "knight.gif");
        monsters = new ArrayList<>();
        for (int[] p : pos)
            monsters.add(new Monster(p[0], p[1], "monster.gif"));
    }

    /**
     * @return
     */
    public Player getPlayer() {
        return players.get(0); //}[0];
    }

    /**
     * @return
     */
    public String tableDisplayAll() {
        return table.displayAll(rooms);
    }

    /**
     * @param i
     * @return
     */
    public String getPlayersInRoom(int i) {
        return table.playersInRoom(rooms[i]);
    }

    /**
     * @param player
     */
    public void move(Player player) {
        table.remove(player);
        player.setLocation(player.hashCode() + 1);
        table.put(player, player.toString());
    }

    /**
     * @return
     */
    public String[] getAllPlayers() {
        String[] thePlayers = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            thePlayers[i] = players.get(i).getPlayerName();
        }
        return thePlayers;
    }

    /**
     * @param name
     * @return
     */
    public String getPlayerRoom(String name) {
        // a Map would have been easier to return these values
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerName().equals(name))
                return getRoomName(players.get(i).hashCode() % table.size());
        }
        return "Not found"; // this should never be the case.
    }

    /**
     * @param i
     * @return
     */
    public String getRoomName(int i) {
        return rooms[i].getRoomName();
    }


    /******************** For strictly gui control flow *********************/


    /**
     * @return
     */
    public boolean isPlayOn() {
        return playOn;
    }

    /**
     * @param isOrNot
     */
    public void setPlayOn(boolean isOrNot) {
        playOn = isOrNot;
    }

    /**
     * @param g2
     * @param observer
     */
    public void drawComponents(Graphics2D g2, ImageObserver observer) {
        if (knight.isVisable())
            knight.draw(g2, observer);
        for (Arrow arrow : knight.getArrow())
            if (arrow.isVisable()) arrow.draw(g2, observer);
        for (Monster m : monsters)
            if (m.isVisable()) m.draw(g2, observer);
    }

    /**
     * @return
     */
    public int getMonsterCount() {
        return monsters.size();
    }

    /**
     *
     */
    public void controlFlow() {
        if (monsters.isEmpty()) playOn = false;
        knight.move();
        ArrayList<Arrow> arrows = knight.getArrow();
        for (int i = 0; i < arrows.size(); i++) {
            if (arrows.get(i).isVisable()) arrows.get(i).move();
            else arrows.remove(i);
        }
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            if (m.isVisable()) m.move();
            else monsters.remove(i);
        }
        checkCollisions();
    }

    /**
     *
     */
    public void checkCollisions() {
        for (Monster m : monsters) {
            if (knight.getCollisison(m.getBounds())) {
                knight.setVisable(false);
                m.setVisable(false);
                playOn = false;
            }
        }
        ArrayList<Arrow> a = knight.getArrow();
        for (Arrow a1 : a) {
            for (Monster mon : monsters) {
                if (mon.getCollisison(a1.getBounds())) {
                    a1.setVisable(false);
                    mon.setVisable(false);
                }
            }
        }
    }

    /**
     *
     */
    public void knightFire() {
        knight.fire("arrow.png");
    }

    /**
     * @param i
     */
    public void setKnightDx(int i) {
        knight.setDx(i);
    }

    /**
     * @param i
     */
    public void setKnightDy(int i) {
        knight.setDy(i);
    }

}
