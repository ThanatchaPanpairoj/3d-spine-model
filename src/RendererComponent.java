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
    private int hWidth, hHeight, fps;
    private Point gravity;
    private Spine spine;
    private ArrayList<Line> grid;

    public RendererComponent(int width, int height) {
        this.hWidth = width >> 1;
        this.hHeight = height >> 1;

        spine = new Spine();

        gravity = new Point(0, -1, 0);

        grid = new ArrayList<Line>();
        for(int w = -5000; w <= 5000; w += 500) {
            grid.add(new Line(new Point(w, 800, -5000), new Point(w, 800, 5000)));
            grid.add(new Line(new Point(-5000, 800, w), new Point(5000, 800, w)));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(hWidth, hHeight);
        spine.calculateNewlightingScale(gravity.getX(), gravity.getY(), gravity.getZ());
        transform(new double[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, 100, 
                0, 0, 0,     1});
        g2.setColor(Color.GRAY);
        for(Line l : grid) {
            l.draw(g2);
        }

        g2.setColor(Color.BLACK);
        g2.drawString("ESC to exit", -hWidth + 5, - hHeight + 17);
        g2.drawString("FPS: " + fps, hWidth - 50, - hHeight + 17);

        spine.draw(g2);

        transform(new double[] {1, 0, 0,     0, 
                0, 1, 0,     0, 
                0, 0, 1, -100, 
                0, 0, 0,     1});
    }

    public void transform(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        gravity.transform(transformationMatrix);
        spine.transform(transformationMatrix);
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
