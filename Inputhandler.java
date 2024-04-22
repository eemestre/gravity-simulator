import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Inputhandler implements KeyListener {
    String text = "";

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            Main.createParticle(this.text);
            this.text = "";
        } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.text = "";
        } else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if(this.text.length() > 0) {
                this.text = this.text.substring(0, this.text.length() - 1);
            }
        } else if(e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_NUM_LOCK || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
            ;
        } else {
            this.text += e.getKeyChar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}