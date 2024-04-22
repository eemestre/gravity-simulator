import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

class Particle {
    double x, y;
    double vx, vy;
    double ax, ay;
    double mass;
    double radius;
    Color color;

    public Particle(double x, double y, double vx, double vy, double ax, double ay, double mass, Color c) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
        this.mass = mass;
        this.color = c;
    }

    public void tick(double t, double g, ArrayList<Particle> particles) {
        double d, dx, dy, theta, a;

        for(int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            dx = p.x - this.x;
            dy = p.y - this.y;
            d = Math.sqrt(dx*dx + dy*dy);
            
            try {
                theta = Math.atan(Math.abs(dy/dx));
            } catch(ArithmeticException  e) {
                theta = Math.PI / 2;
            }

            a = (g * p.mass) / (d*d);

            if(dx > 0) {
                this.ax = a * Math.cos(theta);
            } else {
                this.ax = -(a * Math.cos(theta));
            }

            if(dy > 0) {
                this.ay = a * Math.sin(theta);;
            } else {
                this.ay = -(a * Math.sin(theta));
            }

            this.vx += this.ax * t;
            this.vy += this.ay * t;

            this.x += this.vx*t + this.ax*t*t/2;
            this.y += this.vy*t + this.ay*t*t/2;
        }
    }

    public void render(Graphics g) {
        g.setColor(this.color);
        g.fillOval((int)(x-(radius/2)), (int)(y-(radius/2)), (int)(radius), (int)(radius));
    }

}