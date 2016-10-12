import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Toolkit;

/**
 * A triangle defined by 3 vertices and drawn using half-space functions.
 * 
 * @author Thanatcha Panpairoj
 * @version (a version number or a date)
 */
public class Triangle
{
    private Vertex p1, p2, p3;
    private Point normal;
    private static final int WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    private static final int hWIDTH = WIDTH / 2;
    private static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    private static final int hHEIGHT = HEIGHT / 2;

    public Triangle(Vertex p1, Vertex p2, Vertex p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public void calculateNormal() {
        float a1 = p2.getX() - p1.getX();
        float a2 = p2.getY() - p1.getY();
        float a3 = p2.getZ() - p1.getZ();
        float b1 = p3.getX() - p2.getX();
        float b2 = p3.getY() - p2.getY();
        float b3 = p3.getZ() - p2.getZ();
        normal = new Point(a2*b3-a3*b2,a3*b1-a1*b3,a1*b2-a2*b1);
        p1.addNormal(normal);
        p2.addNormal(normal);
        p3.addNormal(normal);
    }

    public void draw(int[] img, double[] zBuffer) {
        if((p2.getX() * normal.getX() + p2.getY() * normal.getY() + p2.getZ() * (normal.getZ() - 110)) < 0) {
            drawTriangle(img, zBuffer);
        }
    }

    public void drawTriangle(int[] img, double[] zBuffer) {
        // Bounding box
        int maxX = (int)(Math.min(hWIDTH, (int)(Math.max(Math.max(p1.get2Dx(), p2.get2Dx()), p3.get2Dx()))));
        int minX = (int)(Math.max(-hWIDTH, (int)(Math.min(Math.min(p1.get2Dx(), p2.get2Dx()), p3.get2Dx()))));
        int maxY = (int)(Math.min(hHEIGHT, (int)(Math.max(Math.max(p1.get2Dy(), p2.get2Dy()), p3.get2Dy()))));
        int minY = (int)(Math.max(-hHEIGHT, (int)(Math.min(Math.min(p1.get2Dy(), p2.get2Dy()), p3.get2Dy()))));

        // Interpolation Precalculations 
        double area = (p3.get2Dx() - p1.get2Dx()) * (p2.get2Dy() - p1.get2Dy())
            - (p3.get2Dy() - p1.get2Dy()) * (p2.get2Dx() - p1.get2Dx());
        double invZ1 = 1 / (area * p1.getZ());
        double invZ2 = 1 / (area * p2.getZ());
        double invZ3 = 1 / (area * p3.getZ());

        // Delta Precalculations
        double bay = (p2.get2Dy() - p1.get2Dy()) * invZ3;
        double cby = (p3.get2Dy() - p2.get2Dy()) * invZ1;
        double acy = (p1.get2Dy() - p3.get2Dy()) * invZ2;
        double bax = (p2.get2Dx() - p1.get2Dx()) * invZ3;
        double cbx = (p3.get2Dx() - p2.get2Dx()) * invZ1;
        double acx = (p1.get2Dx() - p3.get2Dx()) * invZ2;

        // Column Precalculations
        double xaxbay = (minX - p1.get2Dx()) * bay;
        double xbxcby = (minX - p2.get2Dx()) * cby;
        double xcxacy = (minX - p3.get2Dx()) * acy;

        for (int pX = minX; pX <= maxX; pX+=1, xaxbay += bay, xbxcby += cby, xcxacy += acy) {
            boolean drawn = false;

            // Row Precalculations
            double yaybax = (maxY - p1.get2Dy()) * bax;
            double ybycbx = (maxY - p2.get2Dy()) * cbx;
            double ycyacx = (maxY - p3.get2Dy()) * acx;

            for (int pY = maxY; pY >= minY; pY-=1, yaybax -= bax, ybycbx -= cbx, ycyacx -= acx) {
                try {
                    double edge1, edge2, edge3;
                    if ((edge3 = edgeFunction(xaxbay, yaybax)) >= 0
                        && (edge1 = edgeFunction(xbxcby, ybycbx)) >= 0
                        && (edge2 = edgeFunction(xcxacy, ycyacx)) >= 0) {
                        int index = WIDTH * (pY+hHEIGHT) + (pX+hWIDTH);
                        double z = (1 / (edge1 + edge2 + edge3));
                        if (z < zBuffer[index]) {
                            zBuffer[index] = z;
                            int r = (int) (z * (edge1 * p1.getR() + edge2 * p2.getR() + edge3 * p3.getR()));
                            int g = (int) (z * (edge1 * p1.getG() + edge2 * p2.getG() + edge3 * p3.getG()));
                            int b = (int) (z * (edge1 * p1.getB() + edge2 * p2.getB() + edge3 * p3.getB()));
                            int color = (255 << 24) | (r << 16) | (g << 8) | b;
                            img[index] = color;
                        }
                        drawn = true;
                    } else if (drawn) {
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }    
    }

    public double edgeFunction(double xpxppy, double ypyppx) {
        return xpxppy - ypyppx;
    }

    public void transformNormal(float[] transformationMatrix) {
        normal.transform(transformationMatrix);
    }
}
