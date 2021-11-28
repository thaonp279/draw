package main.model.figure;
import java.awt.geom.Path2D;
import java.awt.Color;

public class DiagonalCross extends Figure {
    /**
     * Construct a DiagonalCross object.
     */
    public DiagonalCross(double x1, double y1, Color color, boolean fill) {
        super(x1, y1, color, fill);
    }

    
    /** 
     * @return Path2D.Double the Shape object representing the object.
     */
    public Path2D.Double getShape2D() {
        Path2D.Double cross = new Path2D.Double();
        cross.moveTo(getX1(), getY1());
        cross.lineTo(getX1() + getWidth(), getY1() + getHeight());
        cross.moveTo(getX1() + getWidth(), getY1());
        cross.lineTo(getX1(), getY1() + getHeight());
        return cross;
    }
}
