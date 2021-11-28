package main.model.figure;

import java.awt.geom.Rectangle2D;
import java.awt.Color;
public class Rectangle extends Figure {
    
    /**
     * Construct a Rectangle object.
     */
    public Rectangle(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    /** 
     * @return Rectangle2D.Double the Shape object representing the object.
     */
    public Rectangle2D.Double getShape2D() {
        return new Rectangle2D.Double(getX1(), getY1(), getWidth(), getHeight());
    }
}