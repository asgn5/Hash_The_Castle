import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 */
public class Castle extends JPanel implements ActionListener {

    /**
     *
     */
    private JPanel northPanel, southPanel;

    /**
     *
     */
    private JTextField textField;

    /**
     *
     */
    private JButton[] buttons = new JButton[5];

    /**
     *
     */
    private JButton move, allRooms, findPlayer;

    /**
     *
     */
    private CastleBoard castleBoard;

    /**
     *
     */
    private Controller controller;

    /**
     * @param windowX
     * @param windowY
     */
    public Castle(int windowX, int windowY) {
        this.setPreferredSize(new Dimension(windowX, windowY));
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        controller = new Controller();
        castleBoard = new CastleBoard(windowX, windowY, controller);
        setNorthPanel();
        setSouthPanel();
        setCenterPanel();
    }

    /**
     *
     */
    private void setCenterPanel() {
        this.add(castleBoard, "Center");
        castleBoard.requestFocus();
    }

    /**
     *
     */
    private void setSouthPanel() {
        southPanel = new JPanel(new GridLayout());
        textField = new JTextField();
        textField.setBackground(Color.black);
        textField.setForeground(Color.white);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setText("Find out where a player is by clicking a button");
        southPanel.add(textField);
        this.add(southPanel, "South");
    }

    /**
     *
     */
    private void setNorthPanel() {
        northPanel = new JPanel(new GridLayout(1, 8));
        allRooms = new JButton("Display All Rooms");
        allRooms.addActionListener(this);
        allRooms.setFocusable(false);
        move = new JButton("Move");
        move.addActionListener(this);
        move.setFocusable(false);
        for (int l = 0; l < buttons.length; l++) {
            buttons[l] = new JButton(controller.getRoomName(l));
            buttons[l].addActionListener(this);
            buttons[l].setFocusable(false);
            northPanel.add(buttons[l]);
        }
        findPlayer = new JButton("Find a Player");
        findPlayer.addActionListener(this);
        findPlayer.setFocusable(false);
        northPanel.add(allRooms);
        northPanel.add(move);
        northPanel.add(findPlayer);
        northPanel.setFocusable(false);

        this.add(northPanel, "North");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        /* For aesthetics takes the native screen size*/
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth(); // divides in half
        int height = (int) screenSize.getHeight() - 1;
        Castle view = new Castle(width, height);
        Window window = new Window();
        window.addPanel(view);
        window.showFrame();
    }

    /**
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        castleBoard.actionPerformed(actionEvent);
        for (int i = 0; i < buttons.length; i++)
            if (actionEvent.getSource() == buttons[i])
                textField.setText(controller.getPlayersInRoom(i));
        if (actionEvent.getSource() == allRooms) {
            castleBoard.setTimer(false);
            String displayAll =   controller.tableDisplayAll();
            JOptionPane.showMessageDialog(null, displayAll, "", 1);
            castleBoard.setTimer(true);
        }
        if (actionEvent.getSource() == move)
            controller.move(controller.getPlayer());
        if (actionEvent.getSource() == findPlayer) {
            castleBoard.setTimer(false);
            String result
                = (String) JOptionPane.showInputDialog(null,
                "Pick a player",
                "Pick", JOptionPane.QUESTION_MESSAGE, null, controller.getAllPlayers(), "Titan");
            JOptionPane.showMessageDialog(null, controller.getPlayerRoom(result), "", 1);
            castleBoard.setTimer(true);
        }
        castleBoard.requestFocus();
    }

    /**
     *
     */
    /* Contains the main window frame. */
    public static class Window extends JFrame {
        private Container c = this.getContentPane();

        /**
         *
         */
        private Window() {
            super("Hash_The_Castle");
        }

        /**
         * @param p
         */
        private void addPanel(JPanel p) {
            this.c.add(p);
        }

        /**
         *
         */
        private void showFrame() {
            this.pack();
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

}