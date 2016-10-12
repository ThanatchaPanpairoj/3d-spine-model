import java.awt.Toolkit;

/**
 * Write a description of class Point here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Point
{
    private float x, y, z, depthScale;
    private static final float WIDTH = (float)Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5f;

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        depthScale = WIDTH / z;
    }

    public void transform(float[] transformationMatrix) {
        float newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        float newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        float newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
        depthScale = WIDTH / z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
