import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
    private double x, y, z;
    private int width, height;

    public Spine(double x, double y, double z, int width, int height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.disk1Points = new Point[7847];
        this.disk2Points = new Point[7847];
        this.faces = new ArrayList<Triangle>(2 * 15426);

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("L4.shl"));

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 1)
                    if(i < 7849)
                        disk1Points[i - 2] = new Point(x + getNum(line.substring(0, 10)), y + getNum(line.substring(11, 22)), z + getNum(line.substring(22)));
                    else if(i < 23275)
                        faces.add(new Triangle(disk1Points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
                                disk1Points[Integer.parseInt(line.substring(line.indexOf(" ", 2) + 1, line.indexOf(" ", line.indexOf(" ", 2) + 1))) - 1], 
                                disk1Points[Integer.parseInt(line.substring(line.indexOf(" ", line.indexOf(" ", 2) + 1) + 1)) - 1], width, height));
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
                        disk2Points[i - 2] = new Point(x + getNum(line.substring(0, 10)), y + 37.49034 + getNum(line.substring(11, 22)), z - 15.7949 + getNum(line.substring(22)));
                    else if(i < 23275)
                        faces.add(new Triangle(disk2Points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
                                disk2Points[Integer.parseInt(line.substring(line.indexOf(" ", 2) + 1, line.indexOf(" ", line.indexOf(" ", 2) + 1))) - 1], 
                                disk2Points[Integer.parseInt(line.substring(line.indexOf(" ", line.indexOf(" ", 2) + 1) + 1)) - 1], width, height));
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

    private double getNum(String s) {
        return s.charAt(0) == ' ' ? getNum(s.substring(1)) : Double.parseDouble(s);
    }

    public void draw(Graphics2D g2) {
        if(z > 0) {
            faces.sort(new DistanceComparator());
            for(int i = 0; i < 1000; i++) {
                faces.get(i).calcDistance();
            }
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for(int i = 1000; i < 2 * 15426; i++) {
                faces.get(i).draw(g2, img);
            }
            g2.drawImage(img, -width / 2, -height / 2, null);
        }
    }

    public void transform(double[] transformationMatrix) {
        for(Point p : disk1Points) {
            p.transform(transformationMatrix);
        }

        for(Point p : disk2Points) {
            p.transform(transformationMatrix);
        }

        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
    }

    public void transformDisk1(double[] transformationMatrix) {
        for(Point p : disk1Points) {
            p.transform(transformationMatrix);
        }
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
