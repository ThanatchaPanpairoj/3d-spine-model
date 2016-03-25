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
        translations = new ArrayList<float[]>(632);
        xRotations = new ArrayList<float[]>(632);
        yRotations = new ArrayList<float[]>(632);
        zRotations = new ArrayList<float[]>(632);

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("L4_Position.tab")));

            int i = 0;
            float x = 0;
            float y = -76.61936f;
            float z = 0;
            double xa = 0;
            double ya = 0;
            double za = 0;
            int comma1 = 0;
            int comma2 = 0;
            int comma3 = 0;
            int comma4 = 0;
            int comma5 = 0;
            int comma6 = 0;
            float nx = 0;
            float ny = 0;
            float nz = 0;
            double nxa = 0;
            double nya = 0;
            double nza = 0;
            String initialPosition = "";
            while((line = bufferedReader.readLine()) != null) {
                if(i > 3 && i < 635) {
                    if(i == 4)
                        initialPosition = line;
                    comma1 = line.indexOf(',', 14);
                    comma2 = line.indexOf(',', comma1 + 1);
                    comma3 = line.indexOf(',', comma2 + 1);
                    comma4 = line.indexOf(',', 95);
                    comma5 = line.indexOf(',', comma4 + 1);
                    comma6 = line.indexOf(',', comma5 + 1);
                    nx = getNum(line.substring(14, comma1));
                    ny = -getNum(line.substring(comma1 + 1, comma2));
                    nz = getNum(line.substring(comma2 + 1, comma3));
                    nxa = Math.toRadians(getNum(line.substring(comma4 + 1, comma5)));
                    nya = Math.toRadians(getNum(line.substring(comma5 + 1, comma6)));
                    nza = Math.toRadians(getNum(line.substring(comma6 + 1)));
                    translations.add(new float[]{1, 0, 0, nx - x,
                            0, 1, 0, ny - y,
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
            translations.add(new float[]{1, 0, 0, -x,
                    0, 1, 0, -76.61936f-y,
                    0, 0, 1, -z,
                    0, 0, 0, 1});
            xRotations.add(new float[] {(float)Math.cos(-xa), 0, (float)Math.sin(-xa), 0,
                    0, 1,                    0, 0, 
                    -(float)Math.sin(-xa), 0, (float)Math.cos(-xa), 0, 
                    0, 0,                    0, 1});
            yRotations.add(new float[] {1,                     0,                    0, 0, 
                    0,  (float)Math.cos(-ya), (float)Math.sin(-ya), 0, 
                    0, -(float)Math.sin(-ya), (float)Math.cos(-ya), 0, 
                    0,                     0,                    0, 1});
            zRotations.add(new float[] {(float)Math.cos(-za), (float)Math.sin(-za), 0, 0,
                    -(float)Math.sin(-za), (float)Math.cos(-za), 0, 0, 
                    0, 0,                    1, 0,        
                    0, 0,                    0, 1});

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
        return Float.parseFloat(s.substring(0, e)) * (float)(Math.pow(10, Integer.parseInt(s.substring(e + 1))));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(hWidth, hHeight);

        spine.calculateNewlightingScale(light.getX(), light.getY(), light.getZ());

        transform(new float[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, 110, 
                0, 0, 0,     1});

        g2.setColor(Color.WHITE);
        g2.drawString("ESC to exit", -hWidth + 5, - hHeight + 17);
        g2.drawString("FPS: " + fps, hWidth - 50, - hHeight + 17);

        spine.draw(g2);

        transform(new float[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, -110, 
                0, 0, 0,     1});
    }

    public void animate() {
        if(transformation < 632) {
            //spine.rotateDisk1(xRotations.get(transformation));
            spine.rotateDisk1(yRotations.get(transformation));
            //spine.rotateDisk1(zRotations.get(transformation));
            spine.transformDisk1(translations.get(transformation));
            transformation++;
            if(transformation == 632)
                transformation = 0;
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
