import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Minimal/basic functionality
 */

public class Castle extends JPanel implements ActionListener {

    /*  gui components */
    private JButton allRooms;
    private JPanel northPanel, centerPanel;
    private JTextField textField;
    private JTextArea textArea;
    private JButton[] buttons = new JButton[5];

    /* Our implementation of the hash-table, player and room classes */
    private CastleHashTable<Player, String> table;
    private Player[] player;
    private Room[] rooms;

    /* Names of the rooms */
    private String[] roomNames = {
        "Dungeon", "Servant's Room", "Great Hall", "Throne Room", "The Wardrobe"
    };

    /* Names of the players */
    private String[] names = {
        "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
        "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen"
    };

    // TODO: Divide into more for clarity
    public Castle(int windowX, int windowY) {
        this.setPreferredSize(new Dimension(windowX, windowY));
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());

        northPanel = new JPanel(new GridLayout(1, 8));
        centerPanel = new JPanel(new GridLayout());

        rooms = new Room[5];
        table = new CastleHashTable<>(5);
        player = new Player[15];
        allRooms = new JButton("Display All Rooms");
        textArea = new JTextArea();
        textField = new JTextField();

        /* just aesthetics */
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setText("Find out where a player is by clicking a button");

        /* initializing rooms with specified names from roomNames */
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Room(roomNames[i] + " " + i, i);
        }

        /* initializes the players with names from names array*/
        for (int j = 0; j < player.length; j++) {
            player[j] = new Player(names[j], j * 10);
            table.put(player[j], player[j].toString());
        }

        /* Creates a button for every room with corresponding name */
        for (int l = 0; l < buttons.length; l++) {
            buttons[l] = new JButton(rooms[l].getRoomName());
            buttons[l].addActionListener(this);
            northPanel.add(buttons[l]);
        }

        allRooms.addActionListener(this);
        centerPanel.add(textField);
        centerPanel.add(textArea);
        northPanel.add(allRooms);

        this.add(centerPanel, "Center");
        this.add(northPanel, "North");
    }

    public static void main(String[] args) {
        /* For aesthetics takes the native screen size*/
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth(); // divides in half
        int height = (int) screenSize.getHeight() / 2;
        Window window = new Window();
        Castle view = new Castle(width, height);
        window.addPanel(view);
        window.showFrame();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for (int i = 0; i < buttons.length; i++) {
            // button index corresponds to room index
            if (actionEvent.getSource() == buttons[i]) {
                // tests get player by specifying room
                textField.setText(table.playersInRoom(rooms[i]));
                repaint();
            }
        }
        if (actionEvent.getSource() == allRooms) {
            textArea.setText(table.displayAll());
            repaint();
        }
    }

    /* Contains the main window frame. */
    public static class Window extends JFrame {
        private Container c = this.getContentPane();

        private Window() {
            super("Hash_The_Castle");
        }

        private void addPanel(JPanel p) {
            this.c.add(p);
        }

        private void showFrame() {
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

}