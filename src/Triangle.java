import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Triangle
{
    private Polygon poly;
    private Point center, p1, p2, p3;
    private Color color;

    public Triangle(Color c, Point p1, Point p2, Point p3) {
        this.center = new Point ((p1.getX() + p2.getX() + p3.getX()) / 3, (p1.getY() + p2.getY() + p3.getY()) / 3, (p1.getZ() + p2.getZ() + p3.getZ()) / 3, 1);
        this.color = c;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public void draw(Graphics2D g2) {
        poly = new Polygon(new int[] {(int)p1.get2Dx(), 
                (int)p2.get2Dx(), 
                (int)p3.get2Dx()}, 
            new int[] {(int)p1.get2Dy(), 
                (int)p2.get2Dy(), 
                (int)p3.get2Dy(), 
            }, 3);

        g2.setColor(color);
        g2.fillPolygon(poly);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(0.01f));
        g2.drawLine((int)p1.get2Dx(), (int)p1.get2Dy(), (int)p2.get2Dx(), (int)p2.get2Dy());
        g2.drawLine((int)p2.get2Dx(), (int)p2.get2Dy(), (int)p3.get2Dx(), (int)p3.get2Dy());
        g2.drawLine((int)p3.get2Dx(), (int)p3.get2Dy(), (int)p1.get2Dx(), (int)p1.get2Dy());
        //g2.drawString((int)getCenter().getX() + "," + (int)getCenter().getY() + "," + (int)getCenter().getZ(), (int)getCenter().get2Dx(), (int)getCenter().get2Dy());
    }

    public void setColor(Color c) {
        color = c;
    }

    public Point getCenter() {
        return center;
    }

    public Polygon getPolygon() {
        return poly;
    }
}
