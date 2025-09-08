import javax.swing.*;
import java.awt.*;

public class DDALine extends JPanel {

    private int x1, y1, x2, y2;

    public DDALine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            g.drawLine(Math.round(x), Math.round(y), Math.round(x), Math.round(y)); // plot pixel
            x += xInc;
            y += yInc;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DDA Line Drawing Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Example line: (50, 50) to (400, 300)
        DDALine panel = new DDALine(50, 50, 400, 300);

        frame.add(panel);
        frame.setVisible(true);
    }
}
