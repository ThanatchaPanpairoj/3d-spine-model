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
    private Point center;
    private Color color;
    private Line l1, l2, l3;

    public Triangle(Color c, Point p1, Point p2, Point p3) {
        this.center = new Point ((p1.getX() + p2.getX() + p3.getX()) / 3, (p1.getY() + p2.getY() + p3.getY()) / 3, (p1.getZ() + p2.getZ() + p3.getZ()) / 3, 1);
        this.color = c;
        this.l1 = new Line(p1, p2);
        this.l2 = new Line(p2, p3);
        this.l3 = new Line(p3, p1);
    }

    public void draw(Graphics2D g2) {
        poly = new Polygon(new int[] {(int)l1.getPointOne().get2Dx(), 
                (int)l1.getPointTwo().get2Dx(), 
                (int)l2.getPointOne().get2Dx(), 
                (int)l2.getPointTwo().get2Dx(), 
                (int)l3.getPointOne().get2Dx(), 
                (int)l3.getPointTwo().get2Dx(),
                6},
            new int[] {(int)l1.getPointOne().get2Dy(), 
                (int)l1.getPointTwo().get2Dy(), 
                (int)l2.getPointOne().get2Dy(), 
                (int)l2.getPointTwo().get2Dy(), 
                (int)l3.getPointOne().get2Dy(), 
                (int)l3.getPointTwo().get2Dy(),},
            6);

        g2.setColor(color);
        g2.fillPolygon(poly);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(0.01f));
        l1.draw(g2);
        l2.draw(g2);
        l3.draw(g2);
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
