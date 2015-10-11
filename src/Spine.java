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
    private Point[] points;
    private ArrayList<Triangle> faces;
    private double x, y, z;

    public Spine(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.points = new Point[7847];
        this.faces = new ArrayList<Triangle>(15426);

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("L4.shl"));

            int i = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 1)
                    if(i < 7849)
                        points[i - 2] = new Point(x + getNum(line.substring(0, 10)), y + getNum(line.substring(11, 22)), z + getNum(line.substring(22)), 1);
                    else if(i < 23275)
                        faces.add(new Triangle(Color.WHITE, points[Integer.parseInt(line.substring(2, line.indexOf(" ", 2))) - 1], 
                                points[Integer.parseInt(line.substring(line.indexOf(" ", 2) + 1, line.indexOf(" ", line.indexOf(" ", 2) + 1))) - 1], 
                                points[Integer.parseInt(line.substring(line.indexOf(" ", line.indexOf(" ", 2) + 1) + 1)) - 1]));
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
    }

    private double getNum(String s) {
        return s.charAt(0) == ' ' ? getNum(s.substring(1)) : Double.parseDouble(s);
    }

    public void draw(Graphics2D g2) {
        boolean draw = false;
        for(Point p : points) {
            if(p.getZ() > 0) {
                draw = true;
                break;
            }
        }

        if(draw) {   
            faces.sort(new DistanceComparator());
            for(int i = 100; i < 15426; i++) {
                int color = 244 ;
                faces.get(i).setColor(new Color(color, color, color - 30));
                faces.get(i).draw(g2);
            }

            //             for(Point p : points) {
            //                 g2.drawString((int)p.getX() + "," + (int)p.getY() + "," + (int)p.getZ(), (int)p.get2Dx(), (int)p.get2Dy());
            //             }
            //             for(int i = 0; i < 8; i++) {
            //                 g2.drawString("" + i, (int)points[i].get2Dx(), (int)points[i].get2Dy());
            //             }
            //g2.drawString("" + Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)), (int)(new Point(x, y, z, 1).get2Dx()), (int)(new Point(x, y, z, 1).get2Dy()));
        }
    }

    public void transform(double[] transformationMatrix) {
        for(Point p : points) {
            if(p != null)
                p.transform(transformationMatrix);
        }

        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
    }

    public void setColor(Color c) {
        for(Triangle f : faces) {
            f.setColor(c);
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
