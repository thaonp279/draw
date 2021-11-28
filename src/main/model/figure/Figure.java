package main.model.figure;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.CloneNotSupportedException;

/**
 * The Figure class provides framework for geometrical shapes.
 * The Figure object is bounded by a bounding box whose upper left corner is its x1, y1 value.
 * The bounding box's area is defined by width and height values.
 * The Figure shape's properties include its color and its fill option.
 * The Figure object also has a method that can return a Shape object which can be used for rendering and more methods.
 */
public abstract class Figure implements Cloneable{
    private double x1;
    private double y1;
    private double width;
    private double height;
    private Color color;
    private boolean fill;

    /**
     * Construct a Figure object with the 
     * @param x1 x coordinate of the upper left corner of the bounding box.
     * @param y1 y coordinate of the upper left corner of the bounding box.
     * @param color color of the object.
     * @param fill whether the object is filled or outlined.
     * @throws IllegalArgumentException if the x1, y1 coordinates are negative
     */
    public Figure(double x1, double y1, Color color, boolean fill) throws IllegalArgumentException{
        if (x1 >= 0 && y1 >= 0) {
            this.x1 = x1;
            this.y1 = y1;
            this.width = 0;
            this.height = 0;
            this.color = color;
            this.fill = fill;
        } else {
            throw new IllegalArgumentException("x1, y1 must be non-negative.");
        }
    }

    
    /** 
     * Update the bounding box.
     * @param x1 new x coordinate of the upper left corner of the bounding box.
     * @param y1 new y coordinate of the upper left corner of the bounding box.
     * @param width the width of the bounding box.
     * @param height the height of the bounding box.
     * @throws IllegalArgumentException if the x1, y1 coordinates are negative
     */
    public void updateBound(double x1, double y1, double width, double height) throws IllegalArgumentException {
        if (x1 >= 0 && y1 >= 0) {
            this.x1 = x1;
            this.y1 = y1;
            this.width = width;
            this.height = height;
        } else {
            throw new IllegalArgumentException("x1 and y1 must be non-negative");
        }
    }

    
    /** 
     * Update the area values of the bounding box.
     * @param width the new width of the bounding box.
     * @param height the new height of the bounding box.
     */
    public void updateArea(double width, double height) {
        updateBound(x1, y1, width, height); 
    }

    
    /** 
     * Update the anchor point, the upper left corner of the bounding box.
     * @param x1 new x coordinate of the upper left corner of the bounding box.
     * @param y1 new y coordinate of the upper left corner of the bounding box.
     */
    public void updateAnchorPoint(double x1, double y1) {
        updateBound(x1, y1, width, height); 
    }

    
    /** 
     * Update the color of the object.
     * @param color the color value.
     */
    public void updateColor(Color color) {
        this.color = color;
    }

    
    /** 
     * Update the fill option.
     * @param fill whether the object is filled or outlined.
     */
    public void updateFill(boolean fill) {
        this.fill = fill;
    }

    
    /** 
     * Shift the Figure object by xDiff horizontally and yDiff vertically.
     * @param xDiff a horizontal delta value
     * @param yDiff a vertical delta value
     * @throws IllegalArgumentException if xDiff and yDiff make the anchor point negative
     */
    public void shift(double xDiff, double yDiff) throws IllegalArgumentException{
        if (xDiff >= -x1 && yDiff >= -y1) {
            x1 += xDiff;
            y1 += yDiff;
        } else {
            throw new IllegalArgumentException(String.format("(x1, y1) can't be negative. xDiff must be >= %f. yDiff must be >= %f.", -x1, -y1));
        }
    }

    /**
     * Test whether object contians a point.
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return
     */
    public boolean contains(double x, double y) {
        // test if intersect with a 2x2 box at x, y coordinates
        double w = 2;
        double h = 2;
        return getShape2D().intersects(x, y, w, h);
    }

    
    /** 
     * Clone the Figure object.
     * @return Figure the clone object.
     */
    @Override
    public Figure clone() {
        try {
            return (Figure) super.clone(); 
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    
    /** 
     * @return double x coordinate of the upper left corner of the bounding box.
     */
    public double getX1() {
        return x1;
    }

    
    /** 
     * @return double y coordinate of the upper left corner of the bounding box.
     */
    public double getY1() {
        return y1;
    }

    
    /** 
     * @return double the width of the bounding box.
     */
    public double getWidth() {
        return width;
    }

    
    /** 
     * @return double the height of the bounding box.
     */
    public double getHeight() {
        return height;
    }

    
    /** 
     * @return Color the color of the object.
     */
    public Color getColor() {
        return color;
    }

    
    /** 
     * @return boolean whether the object is filled.
     */
    public boolean getFill() {
        return fill;
    }

    
    /** 
     * @return ArrayList<Object> all attributes of the Figure object.
     */
    public ArrayList<Object> getAttributes() {
        ArrayList<Object> attributes = new ArrayList<Object>(Arrays.asList(x1, y1, width, height, color, fill));
        return attributes;
    }

    /**
     * Shape2D object of the Figure object.
     * @return Shape2D object.
     */
    public abstract Shape getShape2D();

}
