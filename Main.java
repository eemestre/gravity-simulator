import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import java.awt.Font;

class Main implements KeyListener {
    static int WIDTH = 640, HEIGHT = 480; // frame dimensions
    static int SCALE = 3; // frame dimensions multiplier
    static int tps = 100; // ticks per second the game will run
    static int fps = 60;
    static boolean isRunning = true;
    static Inputhandler input = new Inputhandler();

    static BufferStrategy bs; // buffer strategy used to draw
    static Graphics g; // graphic context to draw on buffer
    static JFrame frame = new JFrame(); // creates window
    static Canvas canvas = new Canvas();

    static List<Particle> particles = new ArrayList<Particle>();

    static final double GRAV = 6.6743 * Math.pow(10, -11);

    static int temp = 0;
    static boolean bol = true;

    public static void main(String[] args) {
        canvas.addKeyListener(input); // add a component(game) that will receive keyboard events to the game
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // set component(game) dimension
        frame.add(canvas); // add component(game) to frame
        frame.setTitle("Gravity Simulation");
        frame.pack(); // set frame size to component size(game)
        frame.setLocationRelativeTo(null); // set frame location on the middle of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close operation to kill the program
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();

        long old_tps = System.currentTimeMillis();
        long old_fps = old_tps;

        while(isRunning) {
            if(System.currentTimeMillis() - old_tps >= 1000/tps) {
                tick();
                old_tps = System.currentTimeMillis();
            }

            if(System.currentTimeMillis() - old_fps >= 1000/fps) {
                render();
                old_fps = System.currentTimeMillis();
            } 
        }
    }

    public static void createParticle(String s) {
        System.out.println(s);
    }

    public static void coloca(String s) {

    }

    public static void tira(String s) {

    }

    public static void tick() {
        /*for(int i = 0; i < particles.size(); i++) {
        	particles.get(i).render(g);
        }*/
    }

    public static void render() {
        bs = canvas.getBufferStrategy(); // get current buffer strategy

        if(bs == null) { // check if it exists
            canvas.createBufferStrategy(3); // if not, creates one
            bs = canvas.getBufferStrategy(); // and get it
        }

        g = bs.getDrawGraphics(); //get graphic context to draw on buffer

        // set bg color
        if(temp == 30) {
            if(bol) {
                bol = false;
            } else {
                bol = true;
            }
            temp = 0;
        } else {
            temp+=1;
        }

        if(bol) {
            g.setColor(Color.cyan);
        } else {
            g.setColor(Color.black);
        }
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(input.text, 30, 30);;

        /*for(int i = 0; i < particles.size(); i++) {
        	particles.get(i).render(g);
        }*/

        bs.show();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

