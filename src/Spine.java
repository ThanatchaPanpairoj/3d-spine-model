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
    private Point[] disk1Points, disk2Points;
    private ArrayList<Triangle> faces;

    public Spine() {
        this.disk1Points = new Point[7847];
        this.disk2Points = new Point[7847];
        this.faces = new ArrayList<Triangle>(30852);

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("L4.shl"));

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 1)
                    if(i < 7849)
                        disk1Points[i - 2] = new Point(getNum(line.substring(0, 10)), getNum(line.substring(11, 22)) - 23, getNum(line.substring(22)));
                    else if(i < 23275)
                        faces.add(new Triangle(disk1Points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader("L5.shl"));

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 1)
                    if(i < 7849)
                        disk2Points[i - 2] = new Point(getNum(line.substring(0, 10)), 37.49034f - 23f + getNum(line.substring(11, 22)), 15.7949f + getNum(line.substring(22)));
                    else if(i < 23275)
                        faces.add(new Triangle(disk2Points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
                                disk2Points[Integer.parseInt(line.substring(line.indexOf(" ", 2) + 1, line.indexOf(" ", line.indexOf(" ", 2) + 1))) - 1], 
                                disk2Points[Integer.parseInt(line.substring(line.indexOf(" ", line.indexOf(" ", 2) + 1) + 1)) - 1]));
                i++;
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
    }

    private float getNum(String s) {
        return s.charAt(0) == ' ' ? getNum(s.substring(1)) : Float.parseFloat(s);
    }

    public void draw(Graphics2D g2) {
        faces.sort(new DistanceComparator());
        for(int i = 0; i < 30852; i++) {
            faces.get(i).draw(g2);
        }
    }

    public void transform(float[] transformationMatrix) {
        for(Point p : disk1Points) {
            p.transform(transformationMatrix);
        }

        for(Point p : disk2Points) {
            p.transform(transformationMatrix);
        }

        for(Triangle t : faces) {
            t.transform(transformationMatrix);
        }
    }

    public void transformDisk1(float[] transformationMatrix) {
        for(Point p : disk1Points) {
            p.transform(transformationMatrix);
        }
    }

    public void calculateNewlightingScale(float lightX, float lightY, float lightZ) {
        for(Triangle f : faces) {
            f.calculateNewlightingScale(lightX, lightY, lightZ);
        }
    }
}
