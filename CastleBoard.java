import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 *
 * The "game" aspect of the program where a player ( the user ) has the ability
 *  to fend off monsters who are attacking them.
 *  Equipped with arrows, the player attempts to fend them off whilst
 *  attempting to be the first player to be able to escape the treacherous castle.
 */
public class CastleBoard extends JPanel implements ActionListener {

    /**
     * The native screen resolution that dictates sizing.
     */
    public static int SCREEN_HEIGHT, SCREEN_WIDTH;

    /**
     * The timers delay
     */
    private final int DELAY = 2;

    /**
     * The timer instance
     */
    private Timer timer;

    /**
     * Buttons to control the flow of the gui
     * allows the user to choose to replay, quit, and restart
     * at specified times.
     */
    private JButton replay, quit, start;

    /**
     * The main control flow of the characters is done by thid object
     * who, when notified, updates particular objects.
     */
    private Controller controller;


    /**
     * @param width The native screen resolution's width passed on from the Castle
     * @param height The native screen resolution's height dimension
     * @param controller The controller being passed here by the Castle
     */
    public CastleBoard( int width, int height, Controller controller ) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.controller = controller;
        setBackground( Color.black );
        start = new JButton( "Start" );
        replay = new JButton( "Restart" );
        quit = new JButton( "Quit" );
        this.add( replay );
        this.add( start );
        this.add( quit );
        replay.addActionListener( this );
        quit.addActionListener( this );
        start.addActionListener( this );
        replay.setVisible( false );
        quit.setVisible( false );
        startPrompt();                      // The dialog to begin asks for users name
        initBoard();
    }


    /**
     * Initializes ( or for reinitializing when game is restarted ) this panel.
     * Captures the displays focus and calls the corresponding initialization of the controller object.
     * creates a new timer
     */
    private void initBoard() {
        this.addKeyListener( new TAdapter() );
        setFocusable( true );
        setDoubleBuffered( true );
        setPreferredSize( new Dimension( SCREEN_WIDTH, SCREEN_HEIGHT ) );
        controller.setPos( SCREEN_WIDTH, SCREEN_HEIGHT );
        controller.initBoard();
        timer = new Timer( DELAY, this );
    }


    /**
     *  The initial frame of the
     */
    public void startPrompt() {
        JDialog one = new JDialog( new JFrame() );
        one.setPreferredSize( new Dimension( SCREEN_WIDTH, SCREEN_HEIGHT ) );
        String str = JOptionPane.showInputDialog( one, "Enter name : ",
          "King Aurthur" );
        if ( str != null ) {
            Object[] options = { "Yes, take me to the castle",
                                 "No, Re-Enter", "EXIT!" };
            int n = JOptionPane.showOptionDialog( one,
              gameRules( str ), "Please choose one of the following",
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null, options, options[ 2 ] );
            controller.setUser( str );
        } else
            JOptionPane.showMessageDialog( one, "You pressed cancel Button.", "PLAYA", 1 );
    }


    public String gameRules( String name ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "Hello " ).append( name ).append( ",\n" )
          .append( "Make all of the monsters disappear to move to the next room\n" )
          .append( "First player to finish the last room wins\n" )
          .append( formatRule( "Up Arrow", "move up" ) )
          .append( formatRule( "Down Arrow", "move down" ) )
          .append( formatRule( "Left Arrow", "move left" ) )
          .append( formatRule( "Right Arrow", "move right" ) )
          .append( formatRule( "Space Bar", "fire arrows" ) )
          .append( "Ready??" );
        return sb.toString();
    }


    public String formatRule( String rule, String description ) {
        return String.format( "%-25s : %-1s\n", rule, description );
    }


    /**
     * @param g
     */
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        Graphics2D g2 = ( Graphics2D ) g;
        if ( controller.isPlayOn() ) {
            controller.drawComponents( g2, this );
            g.setColor( Color.white );
            Font font = new Font( "Calibri", Font.BOLD, 15 );
            g.setFont( font );
            g.drawString( "Current Room:", SCREEN_WIDTH / 4, 20 );
            g.drawString(
              controller.getPlayerRoom( controller.getPlayer().toString() ), SCREEN_WIDTH / 4, 40 );
            g.drawString( "Monsters Left:   " + controller.getMonsterCount(), SCREEN_WIDTH / 8, 20 );
        } else {
            String message;
            if ( controller.hasWon() )
                message = "YOU WIN!!!";
            else
                message = "GAME OVER!!!";
            Font medium = new Font( "Calibri", Font.BOLD, 42 );
            FontMetrics fm = getFontMetrics( medium );
            g.setColor( Color.green );
            g.setFont( medium );
            g.drawString( message, ( SCREEN_WIDTH - fm.stringWidth( message ) ) / 2, SCREEN_HEIGHT / 2 );
            replay.setVisible( true );
            quit.setVisible( true );
        }
        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * @param e
     */
    @Override
    public void actionPerformed( ActionEvent e ) {
        if ( e.getSource() == start ) {
            start.setVisible( false );
            timer.start();
        }
        if ( controller.isPlayOn() ) {
            controller.controlFlow();
        } else {
            timer.stop();
            if ( e.getSource() == replay ) {
                initBoard();
                replay.setVisible( false );
                quit.setVisible( false );
                timer.start();
            }
            if ( e.getSource() == quit ) {
                System.exit( 0 );
            }
        }
    }


    public void setTimer( boolean isOrNot ) {
        if ( !isOrNot )
            timer.stop();
        else
            timer.restart();
    }


    public boolean getTimerOn() {
        return timer.isRunning();
    }


    /**
     * This listens for the key that is pressed/release if the space button is pressed
     * we called the fire method in knight which will display shooting an arrow.
     * KeyEvent.VK _LEFT in key pressed listens for which button is pressed. it does this
     * by getting the key code associated with the button than does whatever it is told (-2)
     * stands for move 2 pixels to the left
     * Key Released sets all x an y to 0 meaning that the knight sprite is no longer moving.
     */
    private class TAdapter extends KeyAdapter {

        /**
         * @param e
         */
        @Override
        public void keyPressed( KeyEvent e ) {
            int key = e.getKeyCode();
            if ( key == KeyEvent.VK_SPACE )
                controller.knightFire();
            if ( key == KeyEvent.VK_LEFT )
                controller.setKnightDx( -1 );
            if ( key == KeyEvent.VK_RIGHT )
                controller.setKnightDx( 1 );
            if ( key == KeyEvent.VK_UP )
                controller.setKnightDy( -1 );
            if ( key == KeyEvent.VK_DOWN )
                controller.setKnightDy( 1 );
        }


        /**
         * @param e
         */
        @Override
        public void keyReleased( KeyEvent e ) {
            int key = e.getKeyCode();
            if ( key == KeyEvent.VK_LEFT )
                controller.setKnightDx( 0 );
            if ( key == KeyEvent.VK_RIGHT )
                controller.setKnightDx( 0 );
            if ( key == KeyEvent.VK_UP )
                controller.setKnightDy( 0 );
            if ( key == KeyEvent.VK_DOWN )
                controller.setKnightDy( 0 );
        }
    }
}