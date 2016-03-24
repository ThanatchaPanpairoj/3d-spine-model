import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * This is the GUITemplate class. This includes the JFrame, listeners, buttons, and the GUITemplateComponent which includes all the objects. The buttons and the components are all added to a panel, which is added to the frame.
 * The main method starts the comp and sets everything up. 
 *
 * @author (Thanatcha Panpairoj)
 * @version (7/25/15)
 */

public class Renderer extends JFrame
{
    private float mouseX, mouseY, yRotation;

    public static void main(String[] args) throws Exception {
        Renderer r = new Renderer();
    }

    public Renderer() throws Exception {
        super();

        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));

        yRotation = 0;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)dim.getWidth(), (int)dim.getHeight());
        this.setTitle("Display");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        final int width = this.getWidth();
        final int height = this.getHeight();

        Robot robot = new Robot();
        robot.mouseMove(width / 2 + 3, height / 2 + 25);
        //System.out.println("" + width + ", " + height);

        //         frame.setUndecorated(true);
        //         frame.setShape(new Ellipse2D.Double(0,0, 800, 800));//circle frame?

        JPanel panel = new JPanel();
        panel.setDoubleBuffered(true);
        panel.setBackground(Color.BLACK);
        RendererComponent comp = new RendererComponent(width, height);

        class TimeListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if(comp.hasFocus()) {
                    mouseX = (float)MouseInfo.getPointerInfo().getLocation().getX() - (float)getLocation().getX() - 3f;
                    mouseY = (float)MouseInfo.getPointerInfo().getLocation().getY() - (float)getLocation().getY() - 25f;

                    float cosYR = (float)Math.cos(yRotation);
                    float cosNYR = (float)Math.cos(-yRotation);
                    float sinYR = (float)Math.sin(yRotation);
                    float sinNYR = (float)Math.sin(-yRotation);

                    comp.transform(new float[] {1,                     0,                    0, 0, 
                            0,  cosNYR, sinNYR, 0, 
                            0, -sinNYR, cosNYR, 0, 
                            0,                     0,                    0, 1});

                    float xSpinAngle = (width * 0.5f - mouseX) * 0.0025f;
                    comp.transform(new float[] {(float)Math.cos(xSpinAngle), 0, (float)Math.sin(xSpinAngle), 0,
                            0, 1,                    0, 0, 
                            -(float)Math.sin(xSpinAngle), 0, (float)Math.cos(xSpinAngle), 0, 
                            0, 0,                    0, 1});

                    comp.transform(new float[] {1,                     0,                  0, 0, 
                            0,  cosYR, sinYR, 0, 
                            0, -sinYR, cosYR, 0, 
                            0,                     0,                  0, 1});

                    float ySpinAngle = (height * 0.5f - mouseY) * 0.0025f;
                    if(yRotation + ySpinAngle < Math.PI * 0.5 && yRotation + ySpinAngle > -Math.PI * 0.5) {
                        float cosYSA = (float)Math.cos(ySpinAngle);
                        float sinYSA = (float)Math.sin(ySpinAngle);
                        comp.transform(new float[] {1,                     0,                    0, 0, 
                                0,  cosYSA, sinYSA, 0, 
                                0, -sinYSA, cosYSA, 0, 
                                0,                     0,                    0, 1});

                        yRotation += ySpinAngle;
                    }

                    robot.mouseMove(width / 2 + 3, height / 2 + 25);

                    comp.repaint();
                }
            }
        }

        class KeyboardListener implements KeyListener {
            /**
             * Updates which keys are currently pressed.
             * 
             * @param  e  key pressed on the keyboard
             * @return    void
             */
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() ==  KeyEvent.VK_ESCAPE) {
                    System.exit(0);}
            }

            /**
             * Updates when a key is released.
             * 
             * @param  e  key released from the keyboard
             * @return    void
             */
            public void keyReleased(KeyEvent e) {}

            /**
             * Updates when a key is typed.
             * 
             * @param  e  key typed on the keyboard
             * @return    void
             */
            public void keyTyped(KeyEvent e) {}
        }

        class MousePressListener implements MouseListener
        {
            /**
             * Updates when the mouse button is pressed.
             * 
             * @param  event  mouse button press
             * @return        void
             */
            public void mousePressed(MouseEvent event){}

            /**
             * Updates when the mouse button is released.
             * 
             * @param  event  mouse button is released
             * @return        void
             */
            public void mouseReleased(MouseEvent event) {}

            public void mouseClicked(MouseEvent event) {}

            public void mouseEntered(MouseEvent event) {}

            public void mouseExited(MouseEvent event) {}
        }

        class ScrollListener implements MouseWheelListener {
            public void mouseWheelMoved(MouseWheelEvent e) {}
        }
        comp.setPreferredSize(new Dimension(width, height));
        comp.addKeyListener(new KeyboardListener());
        comp.addMouseListener(new MousePressListener());
        comp.addMouseWheelListener(new ScrollListener());
        comp.setBounds(0, 0, width, height);
        comp.setFocusable(true);
        comp.setVisible(true);
        comp.setDoubleBuffered(true);

        final int DELAY = 1000 / 60;//60 frames per second
        Timer t = new Timer(DELAY, new TimeListener());
        t.start();

        panel.setLayout(null);

        panel.add(comp);
        this.add(panel);

        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setVisible(true);

        setResizable(false);
        comp.requestFocus();
    }
}
