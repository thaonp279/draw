package main.view;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import main.controller.DrawController;
import main.model.figure.Figure;

/**
 * The CanvasPanel component render Figure objects and listens for MouseEvent on the canvas.
 */
public class CanvasPanel extends JPanel {

    DrawController controller;
    BasicStroke stroke;
    private Color BACKGROUND_COLOR = Color.white;

    /**
     * Construct the CanvasPanel object.
     * @param controller the DrawController object that handles control functions.
     */
    public CanvasPanel(DrawController controller) {
        this.controller = controller;
        this.stroke = new BasicStroke(5);

        addMouseListener(new MouseInputAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.pressedCanvas(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                controller.releasedCanvas();
            }
        });

        addMouseMotionListener(new MouseInputAdapter() {
            public void mouseDragged(MouseEvent e) {
                controller.draggedCanvas(e.getX(), e.getY(), e.isShiftDown());
            }
        });

        setBackground(BACKGROUND_COLOR);
    }

    
    /** 
     * Render all Figure objects passed by controller.
     * @param g
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        ArrayList<Figure> figures = controller.getFigures();

        // render most recently added Figure object on top (last)
        for(int i = figures.size() - 1; i >= 0; i -= 1) {
            Figure fig = figures.get(i);
            g2d.setColor(fig.getColor());
            g2d.setStroke(stroke);
            g2d.draw(fig.getShape2D());
            if (fig.getFill()) {
                g2d.fill(fig.getShape2D());
            }
        }
    }
}