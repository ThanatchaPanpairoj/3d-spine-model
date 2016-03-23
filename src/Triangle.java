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
    private double lightingScaleConstant, lightingScale;
    private Point p1, p2, p3, normal;
    private static final Color COLOR = new Color(244, 244, 214), LINE_COLOR = new Color(195, 195, 120);

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        distance = (int)p1.getZ();
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double uz = p2.getZ() - p1.getZ();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();
        double vz = p3.getZ() - p2.getZ();
        normal = new Point(uy * vz - uz - vy, uz * vx - ux * vz, ux * vy - uy * vx);
        lightingScaleConstant = 1 / (Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }

    public void draw(Graphics2D g2) {
        //if(normal.getZ() < 100) {
            //if(-1 * (normal.getZ() -100) * lightingScaleConstant >= 0)
            g2.setColor(new Color(200 + (int)(44 * lightingScale), 200 + (int)(44 * lightingScale), 180 + (int)(34 * lightingScale)));
            g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
                    new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));
            //g2.setColor(Color.BLACK);
            //g2.drawLine(normal.get2Dx(), normal.get2Dy(), normal.get2Dx(), normal.get2Dy());
            //System.out.println((normal.getZ()));
            //System.out.println((lightingScaleConstant));
            //System.out.println(Math.acos(-1 * (normal.getZ() - 100) * lightingScaleConstant));
        //}
        distance = (int)p1.getZ();
    }

    public void transform(double[] transformationMatrix) {
        normal.transform(transformationMatrix);
    }

    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {
        lightingScale = Math.max((gravityX * normal.getX() + gravityY * normal.getY() + gravityZ * normal.getZ()) * lightingScaleConstant, -1);
    }

    public int getDistance() {
        return distance;
    }
}
