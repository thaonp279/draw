package main.model.figure;
import java.awt.geom.Path2D;
import java.awt.Color;

public class Heart extends Figure {
    
    /**
     * Construct a Heart object.
     */
    public Heart(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    /** 
     * @return Path2D.Double the Shape object representing the object.
     */
    public Path2D.Double getShape2D() {
        double x1 = getX1();
        double y1 = getY1();
        double x2 = x1 + getWidth()/2;
        double x3 = x1 + getWidth();
        double y2 = y1 + getHeight()/2.5;
        double y3 = y1 + getHeight();
        Path2D.Double heart = new Path2D.Double();
        heart.moveTo(x1, y2);
        heart.curveTo(x1, y1, x2, y1, x2, y2);
        heart.curveTo(x2, y1, x3, y1, x3, y2);
        heart.lineTo(x2, y3);
        heart.lineTo(x1, y2);
        return heart;
    }
}
