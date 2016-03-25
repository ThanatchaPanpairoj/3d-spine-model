import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;

import java.io.*;

import javax.swing.JComponent;

/**
 * Basic GUI component GUITemplateComponent
 * 
 * @author Thanatcha Panpairoj
 * @version (a version number or a date)
 */
public class RendererComponent extends JComponent
{
    private int hWidth, hHeight, fps, transformation;
    private Point light;
    private Spine spine;
    private ArrayList<float[]> translations, xRotations, yRotations, zRotations;

    public RendererComponent(int width, int height) {
        this.hWidth = width >> 1;
        this.hHeight = height >> 1;
        transformation = 0;

        spine = new Spine();
        light = new Point(0, -1, 0);
        translations = new ArrayList<float[]>(631);
        xRotations = new ArrayList<float[]>(631);
        yRotations = new ArrayList<float[]>(631);
        zRotations = new ArrayList<float[]>(631);

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("L4_Position.tab")));

            int i = 0;
            float x = 0;
            float y = 0;
            float z = 0;
            double xa = 0;
            double ya = 0;
            double za = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(i > 3 && i < 635) {
                    int comma1 = line.indexOf(',', 14);
                    int comma2 = line.indexOf(',', comma1 + 1);
                    int comma3 = line.indexOf(',', comma2 + 1);
                    int comma4 = line.indexOf(',', 95);
                    int comma5 = line.indexOf(',', comma4 + 1);
                    int comma6 = line.indexOf(',', comma5 + 1);
                    float nx = getNum(line.substring(14, comma1));
                    float ny = getNum(line.substring(comma1 + 1, comma2));
                    float nz = getNum(line.substring(comma2 + 1, comma3));
                    double nxa = Math.toRadians(getNum(line.substring(comma4 + 1, comma5)));
                    double nya = getNum(line.substring(comma5 + 1, comma6));
                    double nza = getNum(line.substring(comma6 + 1));
                    translations.add(new float[]{1, 0, 0, nx - x,
                            0, 1, 0, -(ny - y),
                            0, 0, 1, nz - z,
                            0, 0, 0, 1});
                    xRotations.add(new float[] {(float)Math.cos(nxa - xa), 0, (float)Math.sin(nxa - xa), 0,
                            0, 1,                    0, 0, 
                            -(float)Math.sin(nxa - xa), 0, (float)Math.cos(nxa - xa), 0, 
                            0, 0,                    0, 1});
                    yRotations.add(new float[] {1,                     0,                    0, 0, 
                            0,  (float)Math.cos(nya - ya), (float)Math.sin(nya - ya), 0, 
                            0, -(float)Math.sin(nya - ya), (float)Math.cos(nya - ya), 0, 
                            0,                     0,                    0, 1});
                    zRotations.add(new float[] {(float)Math.cos(nza - za), (float)Math.sin(nza - za), 0, 0,
                            -(float)Math.sin(nza - za), (float)Math.cos(nza - za), 0, 0, 
                            0, 0,                    1, 0,        
                            0, 0,                    0, 1});
                    x = nx;
                    y = ny;
                    z = nz;
                    xa = nxa;
                    ya = nya;
                    za = nza;
                }
                i++;
            }

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'L4_Position.tab'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file 'L4_Position.tab'");                  
            ex.printStackTrace();
        }

        //         transform(new float[] {(float)Math.cos(Math.PI * 0.5), 0, (float)Math.sin(Math.PI * 0.5), 0,
        //                             0, 1,                    0, 0, 
        //                             -(float)Math.sin(Math.PI * 0.5), 0, (float)Math.cos(Math.PI * 0.5), 0, 
        //                             0, 0,                    0, 1});
    }

    public float getNum(String s) {
        int e = s.indexOf('e');
        return Float.parseFloat(s.substring(0, e)) * (float)(Math.pow(10, Integer.parseInt(s.substring(e + 1)))) * 0.1f;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(hWidth, hHeight);

        spine.calculateNewlightingScale(light.getX(), light.getY(), light.getZ());

        transform(new float[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, 120, 
                0, 0, 0,     1});

        g2.setColor(Color.WHITE);
        g2.drawString("ESC to exit", -hWidth + 5, - hHeight + 17);
        g2.drawString("FPS: " + fps, hWidth - 50, - hHeight + 17);

        spine.draw(g2);

        transform(new float[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, -120, 
                0, 0, 0,     1});
    }

    public void animate() {
        if(transformation < 631) {
            //spine.rotateDisk1(xRotations.get(transformation));
            spine.rotateDisk1(yRotations.get(transformation));
            //spine.rotateDisk1(zRotations.get(transformation));
            //spine.transformDisk1(translations.get(transformation));
            transformation++;
        }
    }

    public void transform(float[] transformationMatrix) {
        //light.transform(transformationMatrix);
        spine.transform(transformationMatrix);
    }
    
    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
