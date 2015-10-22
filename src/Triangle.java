import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Triangle
{
    private int distance;
    private Point p1, p2, p3;
    private static final Color COLOR = new Color(244, 244, 214), LINE_COLOR = new Color(195, 195, 120);

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        distance = (int)Math.sqrt(Math.pow(p1.getX(), 2)
            + Math.pow(p1.getY(), 2) 
            + Math.pow(p1.getZ() + 10, 2));
    }

    public void draw(Graphics2D g2) {
        g2.setColor(COLOR);
        g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
                new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));

        g2.setColor(LINE_COLOR);
        g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
        g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
        g2.drawLine(p3.get2Dx(), p3.get2Dy(), p1.get2Dx(), p1.get2Dy());

        distance = (int)Math.sqrt(Math.pow(p1.getX(), 2)
            + Math.pow(p1.getY(), 2) 
            + Math.pow(p1.getZ() + 10, 2));
    }
    
    public void calcDistance() {
        distance = (int)Math.sqrt(Math.pow(p1.getX(), 2)
            + Math.pow(p1.getY(), 2) 
            + Math.pow(p1.getZ() + 10, 2));
    }

    public int getDistance() {
        return distance;
    }
}
