import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

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
    private Point light;
    private Spine spine;

    public RendererComponent(int width, int height) {
        this.hWidth = width >> 1;
        this.hHeight = height >> 1;
        spine = new Spine();

        light = new Point(0, -1, 0);
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

    public void transform(float[] transformationMatrix) {
        light.transform(transformationMatrix);
        spine.transform(transformationMatrix);
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
