import java.awt.*;
import javax.swing.*;
import java.util.*;

public class PolygonClipping extends JPanel {
    private Polygon subjectPolygon;
    private Rectangle clipWindow;
    private Polygon clippedPolygon;

    public PolygonClipping() {
        int[] xPoints = {50, 200, 250, 150, 80};
        int[] yPoints = {50, 30, 180, 250, 200};
        subjectPolygon = new Polygon(xPoints, yPoints, xPoints.length);

        clipWindow = new Rectangle(100, 100, 200, 150);

        clippedPolygon = sutherlandHodgman(subjectPolygon, clipWindow);
    }

    private Polygon sutherlandHodgman(Polygon poly, Rectangle clip) {
        int[] x = poly.xpoints;
        int[] y = poly.ypoints;
        int n = poly.npoints;

        java.util.List<Point> inputList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            inputList.add(new Point(x[i], y[i]));
        }

        java.util.List<Point> outputList = inputList;

        outputList = clipEdge(outputList, new Point(clip.x, clip.y), new Point(clip.x + clip.width, clip.y)); 
        outputList = clipEdge(outputList, new Point(clip.x + clip.width, clip.y), new Point(clip.x + clip.width, clip.y + clip.height)); 
        outputList = clipEdge(outputList, new Point(clip.x + clip.width, clip.y + clip.height), new Point(clip.x, clip.y + clip.height)); 
        outputList = clipEdge(outputList, new Point(clip.x, clip.y + clip.height), new Point(clip.x, clip.y)); 

        Polygon clipped = new Polygon();
        for (Point p : outputList) {
            clipped.addPoint(p.x, p.y);
        }
        return clipped;
    }

    private java.util.List<Point> clipEdge(java.util.List<Point> inputList, Point A, Point B) {
        java.util.List<Point> outputList = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            Point P = inputList.get(i);
            Point Q = inputList.get((i + 1) % inputList.size());

            boolean P_inside = inside(P, A, B);
            boolean Q_inside = inside(Q, A, B);

            if (P_inside && Q_inside) {
                outputList.add(Q);
            } else if (P_inside && !Q_inside) {
                outputList.add(intersection(P, Q, A, B));
            } else if (!P_inside && Q_inside) {
                outputList.add(intersection(P, Q, A, B));
                outputList.add(Q);
            }
        }
        return outputList;
    }

    private boolean inside(Point p, Point A, Point B) {
        return (B.x - A.x) * (p.y - A.y) - (B.y - A.y) * (p.x - A.x) >= 0;
    }

    private Point intersection(Point P, Point Q, Point A, Point B) {
        int A1 = Q.y - P.y;
        int B1 = P.x - Q.x;
        int C1 = A1 * P.x + B1 * P.y;

        int A2 = B.y - A.y;
        int B2 = A.x - B.x;
        int C2 = A2 * A.x + B2 * A.y;

        int det = A1 * B2 - A2 * B1;
        if (det == 0) return Q;
        int x = (B2 * C1 - B1 * C2) / det;
        int y = (A1 * C2 - A2 * C1) / det;
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        g2.drawString("1. Original Polygon with Clipping Window", 30, 20);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(subjectPolygon);
        g2.setColor(Color.RED);
        g2.drawRect(clipWindow.x, clipWindow.y, clipWindow.width, clipWindow.height);

        g2.translate(300, 0);
        g2.setColor(Color.BLACK);
        g2.drawString("2. Clipped Polygon (Inside Only)", 30, 20);
        g2.setColor(Color.BLUE);
        g2.drawPolygon(clippedPolygon);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polygon Clipping - Sutherland Hodgman");
        PolygonClipping panel = new PolygonClipping();
        frame.add(panel);
        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
