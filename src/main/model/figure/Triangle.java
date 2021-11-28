package main.model.figure;
import java.awt.geom.Path2D;
import java.awt.Color;

public class Triangle extends Figure {
    
    /**
     * Construct a Triangle object.
     */
    public Triangle(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    /** 
     * @return Path2D.Double the Shape object representing the object.
     */
    public Path2D.Double getShape2D() {
        Path2D.Double triangle = new Path2D.Double();
        triangle.moveTo(getX1() + getWidth()/2, getY1());
        triangle.lineTo(getX1(), getY1() + getHeight());
        triangle.lineTo(getX1() + getWidth(), getY1() + getHeight());
        triangle.closePath();
        return triangle;
    }
}
