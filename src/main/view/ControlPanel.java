package main.view;

import javax.swing.JButton;
import javax.swing.JToolBar;

import main.controller.DrawController;
import main.model.ControlModel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Render all drawing options in the ControlModel object as JButton component.
 * Listen for actions performing on the the buttons and pass to controller.
 */
public class ControlPanel extends JToolBar implements ActionListener {
    
    private ControlModel controlModel;
    private DrawController controller;

    private JButton fillBtn;
    private JButton colorBtn;
    private JButton selectBtn;
    private HashMap<String, JButton> figureBtns; // buttons for Figure types


    private Color DEFAULT_COLOR = null;
    private Color SELECTED_COLOR = Color.lightGray;

    /**
     * Construct a ControlPanel object.
     * @param controlModel the ControlModel object that maintains all drawing options.
     * @param controller the DrawController object that handles all action events.
     */
    public ControlPanel(ControlModel controlModel, DrawController controller) {
        this.controlModel = controlModel;
        this.controller = controller;
        figureBtns = new HashMap<String, JButton>();
        addControl();
    }

    
    /** 
     * Update the color of buttons based on drawing options.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        updateButton(fillBtn, controlModel.getFill());
        updateButton(colorBtn, controlModel.getColor());
        updateButton(selectBtn, !controlModel.isDrawMode());
        updateFigureButtons();
        super.paint(g);
    }


    
    /** 
     * Update the color of button based on whether their option is selected.
     * @param button JButton component that needs updating.
     * @param selected whether the option is selected.
     */
    private void updateButton(JButton button, boolean selected) {
        button.setBorderPainted(false);
        button.setOpaque(true);
        if (selected) {
            button.setBackground(SELECTED_COLOR);
        } else {
            button.setBackground(DEFAULT_COLOR);
        }
    }

    
    /** 
     * Update the color of button with a color.
     * @param button JButton component that needs updating.
     * @param color color of choice.
     */
    private void updateButton(JButton button, Color color) {
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBackground(color);
    }

    /**
     * Update buttons for Figure types.
     * Only one button is activated at a time.
     */
    private void updateFigureButtons() {
        String selectedType = controlModel.getFigureType();
        for (String type : figureBtns.keySet()) {
            updateButton(figureBtns.get(type), type.equals(selectedType));
        }
    }

    /**
     * Make buttons for Figure types.
     */
    private void makeFigureButtons() {
        for (String type : controlModel.getFigureTypeSupported()) {
            figureBtns.put(type, makeButton(type.substring(0, 1).toUpperCase() + type.substring(1)));
        }
    }

    /**
     * Create JButton for each drawing option and figure types.
     */
    private void addControl() {
        fillBtn = makeButton("Fill");
        colorBtn = makeButton("Color");
        selectBtn = makeButton("Select");
        makeButton("Undo");
        makeButton("Redo");
        makeButton("Clear");
        makeFigureButtons();
    }


    
    /** 
     * Make buttons for drawing options.
     * @param name name of the button.
     * @return JButton component of the drawing option.
     */
    private JButton makeButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(name.toLowerCase());
        updateButton(button, DEFAULT_COLOR);
        add(button);
        return button;
    }

    
    /** 
     * Pass action event on the buttons to controller.
     * @param e event performed on the buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("fill")) {
            controller.toggleFill();
        } else if (e.getActionCommand().equals("color")) {
            controller.toggleColor();
        } else if (e.getActionCommand().equals("undo")) {
            controller.undo();
        } else if (e.getActionCommand().equals("redo")) {
            controller.redo();
        } else if (e.getActionCommand().equals("select")) {
            controller.toggleDrawMode();
        } else if (e.getActionCommand().equals("clear")) {
            controller.clearAll();
        } else {
            controller.setFigureType(e.getActionCommand());
        }
    }
}
