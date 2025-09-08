import javax.swing.*;
import java.awt.*;

public class ShapesExample extends JFrame {
    public ShapesExample() {
        setTitle("Blank and Filled Shapes Example");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

 
    public void paint(Graphics g) {
        super.paint(g);

        
        
        g.setColor(Color.BLUE);
        g.drawRect(50, 100, 200, 100);

       
        g.setColor(Color.CYAN);
        g.fillRect(300, 100, 200, 100);

        
        g.setColor(Color.RED);
        g.drawOval(50, 250, 200, 100);

        
        g.setColor(Color.PINK);
        g.fillOval(300, 250, 200, 100);

       
        g.setColor(Color.BLACK);
        g.drawString("Blank Rectangle", 90, 95);
        g.drawString("Filled Rectangle", 340, 95);
        g.drawString("Blank Oval", 110, 245);
        g.drawString("Filled Oval", 360, 245);
    }
    public static void main(String[] args) {
        new ShapesExample();
    }
}



                                 //  ONLY BLACK COLOUR

/* import javax.swing.*;
import java.awt.*;

public class ShapesExample extends JFrame {
    public ShapesExample() {
        setTitle("Blank and Filled Shapes Example");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

 
    public void paint(Graphics g) {
        super.paint(g);

        
        
        g.setColor(Color.BLACK);
        g.drawRect(50, 100, 200, 100);

       
        g.setColor(Color.BLACK);
        g.fillRect(300, 100, 200, 100);

        
        g.setColor(Color.BLACK);
        g.drawOval(50, 250, 200, 100);

        
        g.setColor(Color.BLACK);
        g.fillOval(300, 250, 200, 100);

       
        g.setColor(Color.BLACK);
        g.drawString("Blank Rectangle", 90, 95);
        g.drawString("Filled Rectangle", 340, 95);
        g.drawString("Blank Oval", 110, 245);
        g.drawString("Filled Oval", 360, 245);
    }
    public static void main(String[] args) {
        new ShapesExample();
    }
} */
