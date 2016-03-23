import java.lang.Double;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Basic GUI component GUITemplateComponent
 * 
 * @author Thanatcha Panpairoj
 * @version (a version number or a date)
 */
public class RendererComponent extends JComponent
{
    private int width, height, fps;
    private Spine spine;
    private ArrayList<Line> grid;

    public RendererComponent(int width, int height) {
        this.width = width;
        this.height = height;

        spine = new Spine(0, 0, 100, width, height);

        grid = new ArrayList<Line>();
        for(int w = -100000; w <= 100000; w += 400) {
            grid.add(new Line(new Point(w, 800, -100000), new Point(w, 800, 100000)));
            grid.add(new Line(new Point(-100000, 800, w), new Point(100000, 800, w)));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(width / 2, height / 2);

        g2.setColor(Color.GRAY);
        for(Line l : grid) {
            l.draw(g2);
        }

        g2.setColor(Color.BLACK);
        g2.drawString("WASD to move", -width / 2 + 5, - height / 2 + 17);
        g2.drawString("Mouse to turn", -width / 2 + 5, - height / 2 + 34);
        g2.drawString("ESC to exit", -width / 2 + 5, - height / 2 + 51);
        g2.drawString("FPS: " + fps, width / 2 - 50, - height / 2 + 17);

        spine.draw(g2);
//         double xShift = spine.getX();
//         double yShift = spine.getY();
//         double zShift = spine.getZ();
//
//         spine.transformDisk1(new double[] {1, 0, 0, -xShift, 
//                 0, 1, 0, -yShift, 
//                 0, 0, 1, -zShift, 
//                 0, 0, 0,         1});
// 
//         double xSpinAngle = 0.016;
//         spine.transformDisk1(new double[] {1,                     0,                    0, 0, 
//                 0,  Math.cos(xSpinAngle), Math.sin(xSpinAngle), 0, 
//                 0, -Math.sin(xSpinAngle), Math.cos(xSpinAngle), 0, 
//                 0,                     0,                    0, 1});
// 
//         double ySpinAngle = 0.008;
//         spine.transformDisk1(new double[] {Math.cos(ySpinAngle), 0, Math.sin(ySpinAngle), 0,
//                 0, 1,                    0, 0, 
//                 -Math.sin(ySpinAngle), 0, Math.cos(ySpinAngle), 0, 
//                 0, 0,                    0, 1});
// 
//         double zSpinAngle = 0.02;
//         spine.transformDisk1(new double[] {Math.cos(zSpinAngle), Math.sin(zSpinAngle), 0, 0, 
//                 -Math.sin(zSpinAngle), Math.cos(zSpinAngle), 0, 0, 
//                 0,                    0, 1, 0,
//                 0,                    0, 0, 1});
// 
//         spine.transformDisk1(new double[] {1, 0, 0, xShift,
//                 0, 1, 0, yShift, 
//                 0, 0, 1, zShift, 
//                 0, 0, 0,        1});         
    }

    public void transform(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }

        spine.transform(transformationMatrix);
    }
    
    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
