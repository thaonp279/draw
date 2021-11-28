package main.model.figure;

import java.awt.geom.Ellipse2D;
import java.awt.Color;

public class Ellipse extends Figure {
    
    /**
     * Construct a Ellipse object.
     */
    public Ellipse(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    /** 
     * @return Ellipse.Double the Shape object representing the object.
     */
    public Ellipse2D.Double getShape2D() {
        return new Ellipse2D.Double(getX1(), getY1(), getWidth(), getHeight());
    }
}
