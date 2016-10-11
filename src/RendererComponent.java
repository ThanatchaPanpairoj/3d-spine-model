import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.util.ArrayList;
import java.util.Arrays;

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
    private BufferedImage canvas;
    private int[] pixels;
    private double[] zBuffer;

    public RendererComponent(int width, int height) {
        this.hWidth = width >> 1;
        this.hHeight = height >> 1;
        transformation = 0;

        spine = new Spine();
        light = new Point(0, -1, 0);
        translations = new ArrayList<float[]>(632);
        //        xRotations = new ArrayList<float[]>(632);
        yRotations = new ArrayList<float[]>(632);
        //        zRotations = new ArrayList<float[]>(632);
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt)canvas.getRaster().getDataBuffer()).getData();
        zBuffer = new double[pixels.length];
        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("L4_Position.tab")));

            int i = 0;
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
                    ny = 73.61936f-getNum(line.substring(comma1 + 1, comma2));
                    nz = getNum(line.substring(comma2 + 1, comma3));
                    //                    nxa = Math.toRadians(getNum(line.substring(comma4 + 1, comma5)));
                    nya = Math.toRadians(getNum(line.substring(comma5 + 1, comma6)));
                    //                    nza = Math.toRadians(getNum(line.substring(comma6 + 1)));
                    translations.add(new float[]{1, 0, 0, nx,
                            0, 1, 0, ny,
                            0, 0, 1, nz,
                            0, 0, 0, 1});
                    //                     xRotations.add(new float[] {(float)Math.cos(nxa - xa), 0, (float)Math.sin(nxa - xa), 0,
                    //                             0, 1,                    0, 0, 
                    //                             -(float)Math.sin(nxa - xa), 0, (float)Math.cos(nxa - xa), 0, 
                    //                             0, 0,                    0, 1});
                    yRotations.add(new float[] {1,                     0,                    0, 0, 
                            0,  (float)Math.cos(nya - ya), (float)Math.sin(nya - ya), 0, 
                            0, -(float)Math.sin(nya - ya), (float)Math.cos(nya - ya), 0, 
                            0,                     0,                    0, 1});
                    //                     zRotations.add(new float[] {(float)Math.cos(nza - za), (float)Math.sin(nza - za), 0, 0,
                    //                             -(float)Math.sin(nza - za), (float)Math.cos(nza - za), 0, 0, 
                    //                             0, 0,                    1, 0,        
                    //                             0, 0,                    0, 1});
                    //                    xa = nxa;
                    ya = nya;
                    //                    za = nza;
                }
                i++;
            }
            translations.add(new float[]{1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1});
            //             xRotations.add(new float[] {(float)Math.cos(-xa), 0, (float)Math.sin(-xa), 0,
            //                     0, 1,                    0, 0, 
            //                     -(float)Math.sin(-xa), 0, (float)Math.cos(-xa), 0, 
            //                     0, 0,                    0, 1});
            yRotations.add(new float[] {1,                     0,                    0, 0, 
                    0,  (float)Math.cos(-ya), (float)Math.sin(-ya), 0, 
                    0, -(float)Math.sin(-ya), (float)Math.cos(-ya), 0, 
                    0,                     0,                    0, 1});
            //             zRotations.add(new float[] {(float)Math.cos(-za), (float)Math.sin(-za), 0, 0,
            //                     -(float)Math.sin(-za), (float)Math.cos(-za), 0, 0, 
            //                     0, 0,                    1, 0,        
            //                     0, 0,                    0, 1});

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
        Arrays.fill(pixels, (new Color(0, 0, 0, 0)).getRGB());
        Arrays.fill(zBuffer, 100000000);
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

        spine.draw(pixels, zBuffer);
        g.drawImage(canvas, -hWidth, -hHeight, this);
        transform(new float[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, -110, 
                0, 0, 0,     1});
    }

    public void animateStep1() {
        spine.resetPosition();
    }

    public void animateStep2() {
        //spine.rotateDisc1(xRotations.get(transformation));
        spine.rotateDisc1(yRotations.get(transformation++));
        //spine.rotateDisc1(zRotations.get(transformation));
        if(transformation == 632)
            transformation = 0;
    }

    public void animateStep3() {
        spine.transformDisc1(translations.get(transformation == 0 ? 631 : transformation - 1));
    }

    public void transform(float[] transformationMatrix) {
        //light.transform(transformationMatrix);
        spine.transform(transformationMatrix);
    }

    public void rotateDisc1(float[] transformationMatrix) {
        spine.rotateDisc1(transformationMatrix);
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
