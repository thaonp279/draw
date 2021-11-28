package main.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

import main.controller.DrawController;

import java.awt.Color;

/**
 * ControlModel class maintains the drawing options.
 */
public class ControlModel {
    private PropertyChangeSupport notifier;

    private Color[] colors; // colors available in the program
    private int colorId; // current color index
    private boolean fill; // current fill option
    private boolean drawMode; // true if in draw mode, false if in select mode
    private String figureType; // current figure type selection
    private double x1; // x coordinate of the last anchor point
    private double y1; // y coordinate of the last anchor point
    private final Set<String> figureTypeSupported = Set.of("line", "rectangle", "ellipse", "cross", "triangle", "heart", "moon");

    /**
     * Construct a new ControlModel object with default options.
     * Create Property Change Support to handle listeners.
     */
    public ControlModel() {
        notifier = new PropertyChangeSupport(this);
        colors = new Color[]{Color.orange, Color.blue, Color.pink, Color.green, Color.red};
        colorId = 0;
        fill = false;
        drawMode = true;
        figureType = "line";
    }

    /**
     * Add listener who will get updates on changes within the ControlModel object.
     * @param listener the PropertyChangeListener to be added
     */
    public void addListener(PropertyChangeListener listener) {
        try {
            notifier.addPropertyChangeListener(listener);
        } catch (NullPointerException ignored) {
            System.out.println("no listeners to fire updates.");
        } 
    }

    /**
     * To be called whenever there is a change within the object.
     */
    private void update() {
        // hack to ensure all call to update will fire change
        boolean oldVal = true;
        boolean newVal = false;
        notifier.firePropertyChange("ControlModel update", oldVal, newVal);
    }

    
    /**  
     * move to the next color option and return the updated option.
     * @return Color the updated color option
     */
    public Color toggleColor() {
        if (colorId < colors.length - 1) {
            colorId += 1;
        } else {
            colorId = 0; // go back to the first option
        }
        update();
        return getColor();
    }

    /**
     * Toggle the fill option between filled and outlined.
     */
    public void toggleFill() {
        fill = !fill;
        update();
    }

    /**
     * Toggle the usage mode: draw and select.
     */
    public void toggleDrawMode() {
        drawMode = !drawMode;
        update();
    }

    
    /** 
     * Update the figure type.
     * @param type the figure type option
     * @throws IllegalArgumentException if type is not supported
     */
    public void setFigureType(String type) throws IllegalArgumentException {
        if (figureTypeSupported.contains(type)) {
            figureType = type;
            update();
        } else {
            throw new IllegalArgumentException(String.format("Figure type %s is unsupported.", type));
        }
    }

    
    /** 
     * Set anchor point for updating and shifting Figure objects.
     * @param x the x coordinate of the anchor point
     * @param y the y coordinate of the anchor point
     * @throws IllegalArgumentException if x, y are negative
     * @see DrawController#pressedCanvas(double, double)
     */
    public void setStartPoint(double x, double y) throws IllegalArgumentException {
        if (x >= 0 && y >= 0) {
            x1 = x;
            y1 = y;
        } else {
            throw new IllegalArgumentException("x, y must be non-negative");
        }
    }

    
    /** 
     * Get the difference between x2 and the anchor x coordinate.
     * Update x2 to be the next anchor value.
     * To be used for shifting a figure iteratively.
     * @param x2
     * @return double
     * @see DrawController#draggedCanvas(double, double, boolean)
     */
    public double getXDiff(double x2) {
        double diff = x2 - x1;
        x1 = x2;
        return diff;
    }

    
    /** 
     * Get the difference between y2 and the anchor y coordinate.
     * Update y2 to be the next anchor value.
     * To be used for shifting a figure iteratively.
     * @param y2
     * @return double
     * @see DrawController#draggedCanvas(double, double, boolean)
     */
    public double getYDiff(double y2) {
        double diff = y2 - y1;
        y1 = y2;
        return diff;
    }

    
    /** 
     * @return Color current color option.
     */
    public Color getColor() {
        return colors[colorId];
    }

    
    /** 
     * @return boolean current fill option.
     */
    public boolean getFill() {
        return fill;
    }

    
    /** 
     * @return boolean whether current usage mode is drawing or selecting.
     */
    public boolean isDrawMode() {
        return drawMode;
    }

    
    /** 
     * @return String current figure type.
     */
    public String getFigureType() {
        return figureType;
    }

    
    /** 
     * @return double x coordinate of current anchor point
     */
    public double getX1() {
        return x1;
    }

    
    /** 
     * @return double y coordinate of current anchor point
     */
    public double getY1() {
        return y1;
    }

    
    /** 
     * @return Set<String> set of supported figure types.
     */
    public Set<String> getFigureTypeSupported() {
        return figureTypeSupported;
    }

}
