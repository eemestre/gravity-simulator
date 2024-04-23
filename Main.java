import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Point;


class Main implements KeyListener {
    static int WIDTH = 1280, HEIGHT = 720; // frame dimensions
    static int SCALE = 3;
    static int tps = 100; // ticks per second the game will run
    static int fps = 60;
    static boolean isRunning = true;
    static String state = "Paused";

    static Inputhandler input = new Inputhandler();
    static BufferStrategy bs; // buffer strategy used to draw
    static Graphics g; // graphic context to draw on buffer
    static Graphics2D g2;
    static JFrame frame = new JFrame(); // creates window
    static Canvas canvas = new Canvas();

    static List<Particle> particles = new ArrayList<Particle>();
    static Particle current = null;

    static final double GRAV = 6.6743 * Math.pow(6.6743, -4);

    public static void main(String[] args) {
        canvas.addKeyListener(input); // add a component (input) that will receive keyboard events
        canvas.addMouseListener(input); // add a component (input) that will receive mouse events
        canvas.addMouseWheelListener(input); // add a component (input) that will receive mouse wheel events
        canvas.addMouseMotionListener(input); // add a component (input) that will receive mouse motion events
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // set component (canvas) dimension

        frame.add(canvas); // add component (canvas) to frame
        frame.setTitle("Gravity Simulation");
        frame.pack(); // set frame size to component size (canvas)
        frame.setLocationRelativeTo(null); // set frame location on the middle of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close operation to kill the program
        //frame.setResizable(false);
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

    public static void moveCurrent(Point point) {
        current.x = point.getX();
        current.y = point.getY();
    }

    public static Particle mouseIn(Point point) {
        double xDif, yDif, distanceSquared;
        boolean in = false;

        for(int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            xDif = p.x - point.getX();
            yDif = p.y - point.getY();
            distanceSquared = xDif * xDif + yDif * yDif;
            in = distanceSquared < p.radius * p.radius;

            if(in) {
                return p;
            }
        } 

        return null;
    }

    public static void createParticle(Point point) {
        if(point.getX() > canvas.getWidth()/7){
            Particle p = new Particle(point.getX(), point.getY());

            double xDif, yDif, distanceSquared;
            boolean collision = false;

            for(int i = 0; i < particles.size(); i++) {
                Particle part = particles.get(i);
                xDif = part.x - p.x;
                yDif = part.y - p.y;
                distanceSquared = xDif * xDif + yDif * yDif;
                collision = distanceSquared < (part.radius + p.radius) * (part.radius + p.radius);

                if(collision) {
                    break;
                }
            }

            if(!collision) {
                particles.add(p);
                current = p;
                current.dragged = true;
                System.out.println("dragged criei particula");
            }
        } else {
            current = null;
        }
    }

    public static void tick() {
        if(state == "Running") {
            for(int i = 0; i < particles.size(); i++) {
                if(!particles.get(i).dragged) {
                    particles.get(i).tick();
                }
            }
        } else {
            
        }
    }

    public static void render() {
        bs = canvas.getBufferStrategy(); // get current buffer strategy

        if(bs == null) { // check if it exists
            canvas.createBufferStrategy(3); // if not, creates one
            bs = canvas.getBufferStrategy(); // and get it
        }

        g = bs.getDrawGraphics(); //get graphic context to draw on buffer

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

        g.setColor(Color.gray);
        g.fillRect(0, 0, canvas.getWidth()/7, HEIGHT*SCALE);

        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString(state, 10, 25);

        for(int i = 0; i < particles.size(); i++) {
            particles.get(i).render();
        }

        if(current != null) {
            g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4));
            g.setColor(Color.red);
            g.drawOval((int)(current.x - current.radius), (int)(current.y - current.radius), (int)(2*current.radius), (int)(2*current.radius));

            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            g.drawString(current.x+"", 10, 55);
            g.drawString(current.y+"", 10, 85);
            g.drawString(current.vx+"", 10, 115);
            g.drawString(current.vy+"", 10, 145);
            g.drawString(current.ax+"", 10, 175);
            g.drawString(current.ay+"", 10, 205);
            g.drawString(current.mass+"", 10, 235);
            g.drawString(current.radius+"", 10, 265);
        }

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

