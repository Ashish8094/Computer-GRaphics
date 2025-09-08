import javax.swing.*;
import java.awt.*;

public class BresenhamLine extends JPanel {

    private int x1, y1, x2, y2;

    public BresenhamLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        int x = x1;
        int y = y1;

        while (true) {
            g.drawLine(x, y, x, y); // plot pixel

            if (x == x2 && y == y2) break;

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bresenham Line Drawing Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Example line from (50, 50) to (400, 300)
        BresenhamLine panel = new BresenhamLine(50, 50, 400, 300);

        frame.add(panel);
        frame.setVisible(true);
    }
}
