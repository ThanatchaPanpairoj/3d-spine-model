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
    private int distance, lightingScale;
    private float lightingScaleConstant;
    private boolean l4;
    private Point p1, p2, p3, normal;

    public Triangle(boolean l4, Point p1, Point p2, Point p3) {
        this.l4 = l4;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        distance = (int)(p1.getZ() + p2.getZ() + p3.getZ()); 
    }

    public void calculateNormal() {
        float a1 = p2.getX() - p1.getX();
        float a2 = p2.getY() - p1.getY();
        float a3 = p2.getZ() - p1.getZ();
        float b1 = p3.getX() - p2.getX();
        float b2 = p3.getY() - p2.getY();
        float b3 = p3.getZ() - p2.getZ();
        normal = new Point(a2*b3-a3*b2,a3*b1-a1*b3,a1*b2-a2*b1);
        lightingScaleConstant = 94 / (float)(Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }

    public void draw(Graphics2D g2) {
        if((p2.getX() * normal.getX() + p2.getY() * normal.getY() + p2.getZ() * (normal.getZ() - 110)) < 0) {
            g2.setColor(new Color(35 + lightingScale, 30 + lightingScale, lightingScale));
            g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
                    new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));
            //g2.setColor(LINE_COLOR);
            //} else {
            //g2.setColor(Color.RED);
            //g2.drawLine(p2.get2Dx(), p2.get2Dy(), p2.get2Dx() + normal.get2Dx(), p2.get2Dy() + normal.get2Dy());
            //g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
            //g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
            //g2.drawLine(p3.get2Dx(), p3.get2Dy(), p1.get2Dx(), p1.get2Dy());
            //System.out.println((normal.getZ()));
            //System.out.println((lightingScaleConstant));
            //System.out.println(Math.acos(-1 * (normal.getZ() - 100) * lightingScaleConstant));
            distance = (int)(p1.getZ() + p2.getZ() + p3.getZ());
        } else {
            distance = 999; 
        }
    }

    public void transform(float[] transformationMatrix) {
        normal.transform(transformationMatrix);
    }

    public void calculateNewlightingScale(float lightX, float lightY, float lightZ) {
        lightingScale = 110 + (int)((lightX * normal.getX() + lightY * normal.getY() + lightZ * normal.getZ()) * lightingScaleConstant);
    }

    public boolean getL4() {
        return l4;
    }

    public int getDistance() {
        return distance;
    }
}
