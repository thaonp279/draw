package main.model.figure;
import java.awt.geom.Path2D;
import java.awt.Color;

public class Moon extends Figure {
    
    /**
     * Construct a Moon object.
     */
    public Moon(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    /** 
     * @return Path2D.Double the Shape object representing the object.
     */
    public Path2D.Double getShape2D() {
        Path2D.Double moon = new Path2D.Double();
        double x1 = getX1();
        double y1 = getY1();
        double x2 = x1 + getWidth()/2;
        double x3 = x1 + getWidth();
        double y2 = y1 + getHeight();
        moon.moveTo(x3, y1);
        moon.curveTo(x1, y1, x1, y2, x3, y2);
        moon.curveTo(x2, y2, x2, y1, x3, y1);
        return moon;
    }
}
