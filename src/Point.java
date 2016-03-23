import java.awt.Toolkit;

/**
 * Write a description of class Point here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Point
{
    private double x, y, z, depthScale;
    private double twoDX, twoDY;
    private static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        depthScale = WIDTH / (20 + z);
        twoDX = (int)(depthScale * x);
        twoDY = (int)(depthScale * y);
    }

    public void transform(double[] transformationMatrix) {
        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
        depthScale = WIDTH / (20 + z);
        twoDX = (double)(depthScale * x);
        twoDY = (double)(depthScale * y);
    }

    public double get2Dx() {
        return twoDX;
    }

    public double get2Dy() {
        return twoDY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
