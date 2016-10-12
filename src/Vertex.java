import java.awt.Toolkit;

/**
 * Write a description of class Vertex here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vertex
{
    private int r, g, b, normalsAdded, lightingScale;
    private float x, y, z, depthScale, twoDX, twoDY, lightingScaleConstant;
    private Point normal;
    private static final float WIDTH = (float) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5f;

    public Vertex(float x, float y, float z, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
        this.depthScale = WIDTH / (10 + z);
        this.twoDX = (this.depthScale * x);
        this.twoDY = (this.depthScale * y);
	    this.normal = new Point(0, 0, 0);
    }

    public void addNormal(Point normal) {
	    this.normal = new Point((this.normal.getX() * normalsAdded + normal.getX()) / (normalsAdded + 1), 
				(this.normal.getY() * normalsAdded + normal.getY()) / (normalsAdded + 1),
				(this.normal.getZ() * normalsAdded + normal.getZ()) / (normalsAdded + 1));
	    normalsAdded += 1;
	    //float mag = (float) Math.sqrt(Math.pow(this.normal.getX(), 2) + Math.pow(this.normal.getY(), 2) + Math.pow(this.normal.getZ(), 2));
        //mag = 1000f;
	    //System.out.println(this.normal.getX()+ " " + this.normal.getY() + " " + this.normal.getZ() + " " + mag);
	    //this.normal = new Point(this.normal.getX() / mag, this.normal.getY() / mag, this.normal.getZ() / mag);
	    this.lightingScaleConstant = (float) (0.7 / (0.3266667 * Math.sqrt(Math.pow(this.normal.getX(), 2) + Math.pow(this.normal.getY(), 2) + Math.pow(this.normal.getZ(), 2)))); 
    }

    public void transform(float[] transformationMatrix) {
        float newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        float newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        float newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
        depthScale = WIDTH / (10 + z);
        twoDX = (depthScale * x);
        twoDY = (depthScale * y);
    }

    public void transformNormal(float[] transformationMatrix) {
	    normal.transform(transformationMatrix);
    }

    public void calculateNewlightingScale(float gravityX, float gravityY, float gravityZ) {
        lightingScale = (int) (50 * Math.max((gravityX * normal.getX() + gravityY * normal.getY() + gravityZ * normal.getZ()) * lightingScaleConstant, -0.4));
        r = 130 + (int)(lightingScale);
        g = 135 + (int)(lightingScale);
        b = 120 + (int)(lightingScale);
    }

    public void setRGB(int r, int g, int b) {
	    this.r = r;
	    this.g = g;
	    this.b = b;
    }
    
    public int getLightingScale() {
        return this.lightingScale;
    }

    public Point getNormal() {
	    return normal;
    }

    public int getR() {
	    return r;
    }

    public int getG() {
	    return g;
    }

    public int getB() {
	    return b;
    }

    public float get2Dx() {
        return twoDX;
    }

    public float get2Dy() {
        return twoDY;
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
