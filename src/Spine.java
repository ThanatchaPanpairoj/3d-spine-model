import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;

import java.io.*;

/**
 * Write a description of class SpinalDisk here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spine
{
    private float x, y, z;
    private Point[] disk1Points, disk2Points;
    private ArrayList<Triangle> faces;

    public Spine() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.disk1Points = new Point[7847];
        this.disk2Points = new Point[7847];
        this.faces = new ArrayList<Triangle>(30852);

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("L4.shl")));

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 1)
                    if(i < 7849)
                        disk1Points[i - 2] = new Point(-getNum(line.substring(0, 10)), -getNum(line.substring(11, 22)), getNum(line.substring(22)));
                    else if(i < 23275)
                        faces.add(new Triangle(true, disk1Points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
                                disk1Points[Integer.parseInt(line.substring(line.indexOf(" ", 2) + 1, line.indexOf(" ", line.indexOf(" ", 2) + 1))) - 1], 
                                disk1Points[Integer.parseInt(line.substring(line.indexOf(" ", line.indexOf(" ", 2) + 1) + 1)) - 1]));
                i++;
            }

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'L4.shl'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file 'L4.shl'");                  
            ex.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("L5.shl")));

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 1)
                    if(i < 7849)
                        disk2Points[i - 2] = new Point(-getNum(line.substring(0, 10)), -getNum(line.substring(11, 22)), getNum(line.substring(22)));
                    else if(i < 23275)
                        faces.add(new Triangle(false, disk2Points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
                                disk2Points[Integer.parseInt(line.substring(line.indexOf(" ", 2) + 1, line.indexOf(" ", line.indexOf(" ", 2) + 1))) - 1], 
                                disk2Points[Integer.parseInt(line.substring(line.indexOf(" ", line.indexOf(" ", 2) + 1) + 1)) - 1]));
                i++;
            }

            for(Point p : disk2Points) {
                float cosYR = (float)Math.cos(0.29496);
                float sinYR = (float)Math.sin(0.29496);
                p.transform(new float[] {1, 0, 0, 0, 
                        0,  cosYR, sinYR, 36.12902f, 
                        0, -sinYR, cosYR, -15.7949f, 
                        0,  0,  0, 1});
                //                                  p.transform(new float[] {1, 0, 0, 0, 
                //                                          0,  cosYR, sinYR, 37.49034f, 
                //                                          0, -sinYR, cosYR, -7.89745f, 
                //                                          0,  0,  0, 1});
            }

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'L5.shl'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file 'L5.shl'");                  
            ex.printStackTrace();
        }

        for(Triangle t : faces) 
            t.calculateNormal();
    }

    private float getNum(String s) {
        return s.charAt(0) == ' ' ? getNum(s.substring(1)) : Float.parseFloat(s);
    }

    public void draw(Graphics2D g2) {
        faces.sort(new DistanceComparator());
        for(int i = 0; i < 30852; i++)
            faces.get(i).draw(g2);
    }

    public void transform(float[] transformationMatrix) {
        for(Point p : disk1Points)
            p.transform(transformationMatrix);

        for(Point p : disk2Points)
            p.transform(transformationMatrix);

        for(Triangle t : faces)
            t.transform(transformationMatrix);
    }

    public void temporaryTransform(float[] transformationMatrix) {
        for(Point p : disk1Points)
            p.transform(transformationMatrix);
    }

    public void transformDisk1(float[] transformationMatrix) {
        float newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        float newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        float newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
        //System.out.println(x + " " + y + " " + z);
        for(Point p : disk1Points)
            p.transform(transformationMatrix);
    }

    public void rotateDisk1(float[] transformationMatrix) {
        transformDisk1(new float[]{1, 0, 0, -x,
                0, 1, 0, -y,
                0, 0, 1, -z,
                0, 0, 0, 1});
        for(Point p : disk1Points) {
            p.transform(transformationMatrix);
        }

        for(Triangle t : faces)
            if(t.getL4()) {
                t.transform(transformationMatrix);
            }
    }

    public void calculateNewlightingScale(float lightX, float lightY, float lightZ) {
        for(Triangle f : faces)
            f.calculateNewlightingScale(lightX, lightY, lightZ);
    }
}
