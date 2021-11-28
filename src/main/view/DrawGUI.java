package main.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.controller.DrawController;
import main.model.ControlModel;
import main.model.DrawModel;

import java.awt.BorderLayout;

/**
 * GUI for the Draw program. Combining the ControlPanel and the CanvasPanel.
 */
public class DrawGUI implements PropertyChangeListener {
    
    private JFrame frame;
    private CanvasPanel canvasPanel;
    private ControlPanel controlPanel;


    private static int FRAME_WIDTH = 1000;
    private static int FRAME_HEIGHT = 700;

    /**
     * Construct a JFrame component and add CanvasPanel, ControlPanel for rendering.
     * @param drawModel Figure object container
     * @param controlModel drawing options
     * @param controller controller for communication between the model and the view packages
     */
    public DrawGUI(DrawModel drawModel, ControlModel controlModel, DrawController controller) {

        // create frame
        frame = new JFrame("Draw Something :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);

        // create panels
        canvasPanel = new CanvasPanel(controller);
        controlPanel = new ControlPanel(controlModel, controller);

        // add panels and paint
        frame.add(canvasPanel);
        frame.add(controlPanel, BorderLayout.PAGE_START);
        frame.paintAll(frame.getGraphics());

        // add listener
        drawModel.addListener(this);
        controlModel.addListener(this);
    }

    
    /** 
     * Call to repaint whenever a property change is detected in the DrawModel and ControlModel.
     * @param event change event detected.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    frame.repaint();
                }
            }
        );
    }
}
