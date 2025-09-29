import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.BufferedImage;   


public class ConcaveFill extends JPanel implements KeyListener {
    private final int[][] polygonPoints = {
            {200,100},{300,150},{250,200},
            {300,250},{200,300},{150,200}
    };
    private boolean doScanline = false;
    private boolean doFlood = false;
    private boolean doSeed = false;

    public ConcaveFill() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPolygon(g);
        if (doScanline) scanlineFill(g);
        if (doFlood) floodFill(new Point(210,180), g);
        if (doSeed) seedFill(new Point(210,180), g);
    }

    private void drawPolygon(Graphics g) {
        g.setColor(Color.WHITE);
        for (int i=0;i<polygonPoints.length;i++) {
            int[] p1 = polygonPoints[i];
            int[] p2 = polygonPoints[(i+1)%polygonPoints.length];
            g.drawLine(p1[0], p1[1], p2[0], p2[1]);
        }
    }

    private void scanlineFill(Graphics g) {
        g.setColor(Color.YELLOW);
        int ymin=Integer.MAX_VALUE, ymax=Integer.MIN_VALUE;
        for (int[] p : polygonPoints) {
            ymin=Math.min(ymin,p[1]);
            ymax=Math.max(ymax,p[1]);
        }
        for (int y=ymin;y<=ymax;y++) {
            java.util.List<Integer> xInts = new ArrayList<>();
            for (int i=0;i<polygonPoints.length;i++) {
                int[] p1=polygonPoints[i];
                int[] p2=polygonPoints[(i+1)%polygonPoints.length];
                if (p1[1]!=p2[1]) {
                    if (y>=Math.min(p1[1],p2[1]) && y<Math.max(p1[1],p2[1])) {
                        float x = p1[0] + (float)(y-p1[1])*(p2[0]-p1[0])/(p2[1]-p1[1]);
                        xInts.add((int)x);
                    }
                }
            }
            Collections.sort(xInts);
            for (int i=0;i<xInts.size()-1;i+=2) {
                g.drawLine(xInts.get(i), y, xInts.get(i+1), y);
            }
        }
    }

    private void floodFill(Point start, Graphics g) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        super.paintComponent(g2);
        drawPolygon(g2);

        Color target = new Color(img.getRGB(start.x, start.y));
        Color fill = Color.GREEN;
        if (target.equals(fill)) return;

        Stack<Point> stack = new Stack<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            if (p.x<0||p.y<0||p.x>=img.getWidth()||p.y>=img.getHeight()) continue;
            if (new Color(img.getRGB(p.x,p.y)).equals(target)) {
                img.setRGB(p.x,p.y,fill.getRGB());
                stack.push(new Point(p.x+1,p.y));
                stack.push(new Point(p.x-1,p.y));
                stack.push(new Point(p.x,p.y+1));
                stack.push(new Point(p.x,p.y-1));
            }
        }
        g.drawImage(img,0,0,null);
    }

    private void seedFill(Point start, Graphics g) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        super.paintComponent(g2);
        drawPolygon(g2);

        Color boundary = Color.WHITE;
        Color fill = Color.CYAN;

        Stack<Point> stack = new Stack<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            Point p = stack.pop();
            if (p.x<0||p.y<0||p.x>=img.getWidth()||p.y>=img.getHeight()) continue;
            Color current = new Color(img.getRGB(p.x,p.y));
            if (!current.equals(boundary) && !current.equals(fill)) {
                img.setRGB(p.x,p.y,fill.getRGB());
                stack.push(new Point(p.x+1,p.y));
                stack.push(new Point(p.x-1,p.y));
                stack.push(new Point(p.x,p.y+1));
                stack.push(new Point(p.x,p.y-1));
            }
        }
        g.drawImage(img,0,0,null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        doScanline = (c=='1');
        doFlood    = (c=='2');
        doSeed     = (c=='3');
        if (c=='4') System.exit(0);
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame f = new JFrame("Concave Polygon Fill");
        ConcaveFill panel = new ConcaveFill();
        f.add(panel);
        f.setSize(500,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        JOptionPane.showMessageDialog(f,
            "Press 1 = Scanline Fill\nPress 2 = Flood Fill\nPress 3 = Seed Fill\nPress 4 = Exit");
    }
}
