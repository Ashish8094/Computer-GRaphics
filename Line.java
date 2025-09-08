import javax.swing.*;
import java.awt.*;

public class Line extends JFrame {
    public Line() {
        setTitle("Basic line example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(50, 50, 300, 300); // Draw a line
    }

    public static void main(String[] args) {
        new Line();
    }
}
