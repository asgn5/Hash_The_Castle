import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Included in this atrociously long class are basic Player and Room classes
 * Minimal/basic functionality to view hashtable methods put and get
 * Randomly assigns players and displays their randomly assigned name
 * corresponding to the room they are located within.
 * Warning: proof of concept. Haha. Do what you may with it.
 */

public class Castle extends JPanel implements ActionListener {

    //    private HashTable hashTable = new HashTable(7);
    private Room[] rooms = new Room[7];
    private AButton[] buttons = new AButton[7];
    private JButton displayAllRooms;
    private ArrayList<Player> playerList;
    private JPanel northPanel, centerPanel;
    private JTextField textArea;
    private JTextArea displayAll;
    private String[] roomNames = {
        "Living Room", "Office", "Bedroom", "Dungeon", "Balcony", "Sun Room", "Study"
    };
    private CastleHashTable<Integer, Player> table;

    public Castle(int windowX, int windowY) {
        this.setPreferredSize(new Dimension(windowX, windowY));
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        northPanel = new JPanel(new GridLayout(1, 8));
        setBackground(Color.black);

        table = new CastleHashTable<>(7);

        playerList = new ArrayList<>();
        centerPanel = new JPanel(new GridLayout());
        textArea = new JTextField();
        displayAll = new JTextArea();
        displayAllRooms = new JButton("Display All Rooms");
        displayAllRooms.addActionListener(this);

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Room(roomNames[i], i);
        }
        for (int l = 0; l < buttons.length; l++) {
            buttons[l] = new AButton(rooms[l].getRoomName(), l);
            buttons[l].addActionListener(this);
            northPanel.add(buttons[l]);
        }
        northPanel.add(displayAllRooms);
        // Randomly assigns players
        for (int j = 0; j < 10; j++) {
            Random random = new Random();
            int next = random.nextInt(100);
            playerList.add(new Player("Player" + String.valueOf(j + 1), j));
        }

        textArea.setHorizontalAlignment(JTextField.CENTER);
        textArea.setEditable(false);
        textArea.setText("Currently the buttons only retrieve the players that are"
                             + " randomly assigned to corresponding rooms");
        centerPanel.add(textArea);
        centerPanel.add(displayAll);


        this.add(centerPanel, "Center");
        this.add(northPanel, "North");

        // Tests the put method
        for (Player p : playerList) {
            table.put(p.hashCode(), p);
//            System.out.println(p.getPlayerName() + " " + p.getPlayerRoom());
//            hashTable.put(p.getPlayerRoom().getRoomName(), p.getPlayerName());
        }
//        System.out.println(hashTable.printTable());
    }

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() / 2;
        int height = (int) screenSize.getHeight() / 2;
        Window window = new Window();
        Castle view = new Castle(width, height);
        window.addPanel(view);
        window.showFrame();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for (int i = 0; i < buttons.length; i++) {
            if (actionEvent.getSource() == buttons[i]) {
                if (table.get(roomNames[i].hashCode()).getPlayerName() != null) {
                    String tmp = table.get(roomNames[i].hashCode()).getPlayerName();
                    if (tmp.equals(null)) {
                        textArea.setText("KJHLKKKKKKKKKKKKKKHSDFKJLSHDFJKLSDHFKLSDKF");
                    } else
                        textArea.setText(tmp);
                }

            }

        }
        repaint();
        if (actionEvent.getSource() == displayAllRooms) {
            displayAll.setText(table.displayAll());
            repaint();
//  displayAll.setText(hashTable.printTable());
        }


    }

    private static class Window extends JFrame {
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

    public class AButton extends JButton {
        private int index;

        public AButton(String roomName, int index) {
            super(roomName);
            this.index = index;
        }

    }

}