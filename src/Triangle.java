import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Triangle
{
    private int distance, halfWidth, halfHeight;
    private Point p1, p2, p3;
    private static final Color COLOR = new Color(244, 244, 214), LINE_COLOR = new Color(195, 195, 120);

    public Triangle(Point p1, Point p2, Point p3, int width, int height) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        distance = (int)Math.sqrt(Math.pow(p1.getX(), 2)
            + Math.pow(p1.getY(), 2) 
            + Math.pow(p1.getZ() + 10, 2));
    }

    public void draw(Graphics2D g2, BufferedImage img) {
        g2.setColor(COLOR);
        //         g2.fillPolygon(new Polygon(new int[] {(int)p1.get2Dx(), (int)p2.get2Dx(), (int)p3.get2Dx()}, 
        //                 new int[] {(int)p1.get2Dy(), (int)p2.get2Dy(), (int)p3.get2Dy()}, 3));

        double p1x = p1.get2Dx();
        double p2x = p2.get2Dx();
        double p3x = p3.get2Dx();
        if(p3x < p1x) {
            Point temp = p3;
            p3 = p1;
            p1 = temp;
        }
        if(p2x < p1x) {
            Point temp = p2;
            p2 = p1;
            p1 = temp;
        }
        if(p3x < p2x) {
            Point temp = p3;
            p3 = p2;
            p2 = temp;
        }
        p1x = p1.get2Dx();
        p2x = p2.get2Dx();
        p3x = p3.get2Dx();
        double p1y = p1.get2Dy();
        double p2y = p2.get2Dy();
        double p3y = p3.get2Dy();
        double dp1p2 = (p2y - p1y) / (p2x - p1x);
        double dp2p3 = (p3y - p2y) / (p3x - p2x);
        double dp1p3 = (p3y - p1y) / (p3x - p1x);
        if((int)Math.abs(p1x) < halfWidth && (int)Math.abs(p1y) < halfHeight)
            img.setRGB((int)p1x + halfWidth, (int)p1y + halfHeight, COLOR.getRGB());
        if(p2y < p3y) {
            for(int x = (int)p1x + 1; x < p2x; x++) {
                int dx = x - (int)p1x;
                int y2 = (int)(p1y + dp1p2 * dx);
                int y3 = (int)(p1y + dp1p3 * dx);
                for(int y = y2; y <= y3; y++) {
                    if((int)Math.abs(x) < halfWidth && (int)Math.abs(y) < halfHeight)
                        img.setRGB(x + halfWidth, y + halfHeight, COLOR.getRGB());
                }
            }
        } else {

        }

        g2.setColor(LINE_COLOR);
        g2.drawLine((int)p1.get2Dx(), (int)p1.get2Dy(), (int)p2.get2Dx(), (int)p2.get2Dy());
        g2.drawLine((int)p2.get2Dx(), (int)p2.get2Dy(), (int)p3.get2Dx(), (int)p3.get2Dy());
        g2.drawLine((int)p3.get2Dx(), (int)p3.get2Dy(), (int)p1.get2Dx(), (int)p1.get2Dy());

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
