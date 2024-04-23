import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.awt.Point;

class Particle {
    final double MAX_SPEED = 1500;

    double x, y;
    double vx, vy;
    double ax, ay;
    double mass;
    double radius;
    Color color;
    boolean dragged;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.mass = 100;
        this.radius = 5;
        this.color = Color.white;
        this.dragged = false;
    }

    public void tick() {
        double d, dx, dy, theta, a;

        this.ax = 0;
        this.ay = 0;

        for(int i = 0; i < Main.particles.size(); i++) {
            Particle p = Main.particles.get(i);
            
            if(p != this) {
                dx = p.x - this.x;
                dy = p.y - this.y;
                d = Math.sqrt(dx*dx + dy*dy);
                
                try {
                    theta = Math.atan(Math.abs(dy/dx));
                } catch(ArithmeticException  e) {
                    theta = Math.PI / 2;
                }
    
                a = (Main.GRAV * p.mass) / (d*d);
    
                if(dx >= 0) {
                    this.ax += a * Math.cos(theta);
                } else {
                    this.ax -= a * Math.cos(theta);
                }
    
                if(dy >= 0) {
                    this.ay += a * Math.sin(theta);
                } else {
                    this.ay -= a * Math.sin(theta);
                }
            }
        }

        int t = (1000/Main.tps);
                
            this.vx += this.ax * t;
            this.vy += this.ay * t;

            if(this.vx > MAX_SPEED) {
                this.vx = MAX_SPEED;
            } else if(this.vx < -MAX_SPEED) {
                this.vx = -MAX_SPEED;
            }

            if(this.vy > MAX_SPEED) {
                this.vx = MAX_SPEED;
            } else if(this.vy < -MAX_SPEED) {
                this.vy = -MAX_SPEED;
            }

            this.x += this.vx*t + this.ax*t*t/2;
            this.y += this.vy*t + this.ay*t*t/2;

            if(this.x + this.radius > Main.frame.getWidth()) {
                double excess = this.x + this.radius - Main.frame.getWidth();
                this.x -= excess;
                this.vx = -Math.abs(this.vx);
            } else if(this.x - this.radius < 0) {
                double excess = this.x - this.radius;
                this.x -= excess;
                this.vx = Math.abs(this.vx);
            }

            if(this.y + this.radius > Main.frame.getHeight()) {
                double excess = this.y + this.radius - Main.frame.getHeight();
                this.y -= excess;
                this.vy = -Math.abs(this.vy);
            } else if(this.y - this.radius < 0) {
                double excess = this.y - this.radius;
                this.y -= excess;
                this.vy = Math.abs(this.vy);

            }
    }

    public void render() {
        Main.g.setColor(this.color);
        Main.g.fillOval((int)(x-(radius)), (int)(y-(radius)), (int)(2*radius), (int)(2*radius));
    }

}