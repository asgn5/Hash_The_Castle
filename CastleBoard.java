import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 */
public class CastleBoard extends JPanel implements ActionListener {

    /**
     *
     */
    public static int SCREEN_HEIGHT, SCREEN_WIDTH;

    /**
     *
     */
    private final int DELAY = 1;

    /**
     *
     */
    private Timer timer;

    /**
     *
     */
    private JButton replay, quit;

    /**
     *
     */
    private Controller controller;

    /**
     * @param width
     * @param height
     * @param controller
     */
    public CastleBoard(int width, int height, Controller controller) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.controller = controller;
        setBackground(Color.black);
        replay = new JButton("Restart");
        quit = new JButton("Quit");
        add(replay);
        add(quit);
        replay.addActionListener(this);
        quit.addActionListener(this);
        replay.setVisible(false);
        quit.setVisible(false);
        initBoard();
    }

    /**
     *
     */
    private void initBoard() {
        this.addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        controller.initBoard();
        timer = new Timer(DELAY, this);
        timer.start();
//        this.requestFocus();
    }

    /**
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (controller.isPlayOn()) {
            controller.drawComponents(g2, this);
            g.setColor(Color.white);
            g.drawString("Monsters Left: " + controller.getMonsterCount(), 5, 15);
        } else {
            String message = "GAME OVER!!!";
            Font medium = new Font("Calibri", Font.BOLD, 42);
            FontMetrics fm = getFontMetrics(medium);
            g.setColor(Color.green);
            g.setFont(medium);
            g.drawString(message, (SCREEN_WIDTH - fm.stringWidth(message))/2, SCREEN_HEIGHT / 2);
            replay.setVisible(true);
            quit.setVisible(true);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller.isPlayOn()) {
            controller.controlFlow();
        } else {
            timer.stop();
            if (e.getSource() == replay) {
                initBoard();
                replay.setVisible(false);
                quit.setVisible(false);
            }
            if (e.getSource() == quit) {
                System.exit(0);
            }
        }
    }

    public void setTimer(boolean isOrNot) {
        if (isOrNot)
            timer.restart();
        else
            timer.stop();
    }


    /**
     *
     */
    private class TAdapter extends KeyAdapter {

        /**
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE)
                controller.knightFire();
            if (key == KeyEvent.VK_LEFT)
                controller.setKnightDx(-1);
            if (key == KeyEvent.VK_RIGHT)
                controller.setKnightDx(1);
            if (key == KeyEvent.VK_UP)
                controller.setKnightDy(-1);
            if (key == KeyEvent.VK_DOWN)
                controller.setKnightDy(1);
        }

        /**
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT)
                controller.setKnightDx(0);
            if (key == KeyEvent.VK_RIGHT)
                controller.setKnightDx(0);
            if (key == KeyEvent.VK_UP)
                controller.setKnightDy(0);
            if (key == KeyEvent.VK_DOWN)
                controller.setKnightDy(0);
        }
    }
}