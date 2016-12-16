import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 * The view of the project.
 * Displays the functionality of the hash table
 * The aim of the game is to be able to make all of the monsters disappear
 * and reach the final level before any other player can.
 * The "players" movement is simulated everytime the user's arrow makes a monster disappear
 * The movement of any player resembles re-computing the hash function
 * moving to an other room is equivalent to a player (key) being placed into a different bucket.
 */
public class Castle extends JPanel implements ActionListener {

    /**
     * The north panel holds all of the buttons in order to interact with the hash table
     * via the controller object of the class.
     * The south panel is present for direction.
     */
    private JPanel northPanel, southPanel;

    /**
     * The visual text display of the south panel
     */
    private JTextField textField;

    /**
     * A button corresponding to every room
     */
    private JButton[] buttons = new JButton[ 5 ];

    /**
     * All-rooms displays a dialog of every room and the players within.
     * Find player controls the ability to be able to find a player
     *    based on their hashcode that corresponds to a room ( hash-table bucket ).
     */
    private JButton allRooms, findPlayer;

    /**
     * This is the center panel where all the magic of the game happens
     * the player has the ability to fire arrows at the monsters in order do defend himself
     */
    private CastleBoard castleBoard;

    /**
     * This object is a reference to the control flow of the program.
     * Is told by this class as well as the castleboard when certain situations occur
     * and therby updating it's respective values/objects.
     */
    private Controller controller;


    /**
     * The initialization of the castle class sets up its panel's and components to get ready to play!
     * @param windowX The native resolution of the windows maximum width (or x) coordinate.
     * @param windowY The windows maximum height in relation to the computer's native resolution.
     */
    public Castle( int windowX, int windowY ) {
        this.setPreferredSize( new Dimension( windowX, windowY ) );
        this.setBackground( Color.white );
        this.setLayout( new BorderLayout() );
        controller = new Controller();
        castleBoard = new CastleBoard( windowX, windowY, controller );
        setNorthPanel();
        setSouthPanel();
        setCenterPanel();
    }


    /**
     * Creates the interactive buttons that correspond to testing the functionality of the hash table.
     * Adds buttons for every room ( bucket ).
     */
    private void setNorthPanel() {
        northPanel = new JPanel( new GridLayout( 1, 8 ) );
        allRooms = new JButton( "Display All Rooms" );
        allRooms.addActionListener( this );
        allRooms.setFocusable( false );
        for ( int l = 0; l < buttons.length; l++ ) {
            buttons[ l ] = new JButton( controller.getRoomName( l ) );
            buttons[ l ].addActionListener( this );
            buttons[ l ].setFocusable( false );
            northPanel.add( buttons[ l ] );
        }
        findPlayer = new JButton( "Find a Player" );
        findPlayer.addActionListener( this );
        findPlayer.setFocusable( false );
        northPanel.add( allRooms );
        northPanel.add( findPlayer );
        northPanel.setFocusable( false );
        this.add( northPanel, "North" );
    }


    /**
     * Creates a simple south panel that reminds the user of the above functionality
     */
    private void setSouthPanel() {
        southPanel = new JPanel( new GridLayout() );
        textField = new JTextField();
        textField.setBackground( Color.black );
        textField.setForeground( Color.white );
        textField.setEditable( false );
        textField.setHorizontalAlignment( JTextField.CENTER );
        textField.setText( "Find out where a player is by clicking a button" );
        southPanel.add( textField );
        this.add( southPanel, "South" );
    }


    /**
     * This simply adds the castleboard panel to the center of the layout.
     */
    private void setCenterPanel() {
        this.add( castleBoard, "Center" );
        castleBoard.requestFocus();
    }


    /**
     * The main initializer of the game.
     * Starts with a dialog asking for your name and proceeds to create the user's representation into the
     *   hash-table.
     *   Utilizes javas Default Toolkit to get the size of whatever screen the game is being played on.
     * @param args
     */
    public static void main( String[] args ) {
                /* For aesthetics takes the native screen size*/
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = ( int ) screenSize.getWidth(); // divides in half
        int height = ( int ) screenSize.getHeight() - 1;
        Castle view = new Castle( width, height );
        Window window = new Window();
        window.addPanel( view );
        window.showFrame();
    }


    /**
     * Controls the action of the buttons as well as letting the controller know of changes
     * as the game progresses.
     * @param actionEvent The event performed ( button press )
     */
    @Override
    public void actionPerformed( ActionEvent actionEvent ) {
        castleBoard.actionPerformed( actionEvent );
        for ( int i = 0; i < buttons.length; i++ )
            if ( actionEvent.getSource() == buttons[ i ] ) {
                String showRoom = controller.getPlayersInRoom( i );
                aOptionPane( showRoom );
            }
        if ( actionEvent.getSource() == allRooms )
            aOptionPane( controller.tableDisplayAll() );
        if ( actionEvent.getSource() == findPlayer ) {
            String result = ( String ) JOptionPane.showInputDialog( null,
              "Pick a player", "Pick", JOptionPane.QUESTION_MESSAGE, null,
              controller.getAllPlayers(), "" );
            aOptionPane( controller.getPlayerRoom( result ) );
        }
        castleBoard.requestFocus();
    }

    /*
     * While having reference to it's centerpane ( the castleboard ) ,
     * has the ability to set its timer to on or off
     * Pauses the timer whenever a button is pressed
     */
    public void aOptionPane( String message ) {
        if ( castleBoard.getTimerOn() ) {
            castleBoard.setTimer( false );
            JOptionPane.showMessageDialog( null, message, "", 1 );
            castleBoard.setTimer( true );
        } else {
            JOptionPane.showMessageDialog( null, message, "", 1 );
        }
    }


    /**
     * Contains the main window frame for the display
     **/
    public static class Window extends JFrame {

        private Container c = this.getContentPane();


        /**
         * A self proclaimed title goes along with its creation
         */
        private Window() {
            super( "Hash_The_Castle" );
        }


        /**
         * @param p The panel to be added to it. ( "this" )
         */
        private void addPanel( JPanel p ) {
            this.c.add( p );
        }


        /**
         * Sets the frame to be visible.
         */
        private void showFrame() {
            this.pack();
            this.setVisible( true );
            this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        }
    }
}