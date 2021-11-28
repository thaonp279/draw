package main.model.figure;

import java.awt.geom.Line2D;
import java.awt.Color;
public class Line extends Figure {

    /**
     * Construct a Line object.
     */
    public Line(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    /** 
     * @return Line2D.Double the Shape object representing the object.
     */
    public Line2D.Double getShape2D() {
        return new Line2D.Double(getX1(), getY1(), getX1() + getWidth(), getY1() + getHeight());
    }
}
