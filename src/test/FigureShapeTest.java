package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import main.model.figure.*;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.Shape;

/**
 * Test override methods of Figure subclasses.
 */
public class FigureShapeTest {
    private Figure figure;
    private Line line;
    private Rectangle rectangle;
    private Ellipse ellipse;
    private DiagonalCross diagonalCross;
    private Moon moon;
    private Heart heart;
    private Triangle triangle;

    private Shape figureShape;
    private Line2D.Double lineShape;
    private Rectangle2D.Double rectangleShape;
    private Ellipse2D.Double ellipseShape;
    private Path2D.Double diagonalCrossShape;
    private Path2D.Double moonShape;
    private Path2D.Double heartShape;
    private Path2D.Double triangleShape;

    @Before
    public void setup() {
        figure = new Ellipse(10, 10, Color.pink, false);
        line = new Line(10, 10, Color.pink, false);
        rectangle = new Rectangle(10, 10, Color.pink, false);
        ellipse = new Ellipse(10, 10, Color.pink, false);
        diagonalCross = new DiagonalCross(10, 10, Color.pink, false);
        moon = new Moon(10, 10, Color.pink, false);
        heart = new Heart(10, 10, Color.pink, false);
        triangle = new Triangle(10, 10, Color.pink, false);

        line.updateArea(10, 10);
        rectangle.updateArea(10, 10);
        ellipse.updateArea(10, 10);
        diagonalCross.updateArea(10, 10);
        moon.updateArea(10, 10);
        heart.updateArea(10, 10);
        triangle.updateArea(10, 10);

        figureShape = figure.getShape2D();
        lineShape = line.getShape2D();
        rectangleShape = rectangle.getShape2D();
        ellipseShape = ellipse.getShape2D();
        diagonalCrossShape = diagonalCross.getShape2D();
        moonShape = moon.getShape2D();
        heartShape = heart.getShape2D();
        triangleShape = triangle.getShape2D();
    }

    /**
     * Test getShape2D method of each subclass.
     */
    @Test
    public void testShape2D() {
        assertTrue(figureShape instanceof Ellipse2D.Double);
        assertTrue(lineShape instanceof Line2D.Double);
        assertTrue(rectangleShape instanceof Rectangle2D.Double);
        assertTrue(ellipseShape instanceof Ellipse2D.Double);
        assertTrue(diagonalCrossShape instanceof Path2D.Double);
        assertTrue(moonShape instanceof Path2D.Double);
        assertTrue(heartShape instanceof Path2D.Double);
        assertTrue(triangleShape instanceof Path2D.Double);
    }

    /**
     * Test contains method of each subclass.
     */
    @Test
    public void testContains() {
        // point on shape
        assertTrue(line.contains(15, 15));
        assertTrue(rectangle.contains(15, 15));
        assertTrue(ellipse.contains(15, 15));
        assertTrue(diagonalCross.contains(15, 15));
        assertTrue(moon.contains(15, 15));
        assertTrue(heart.contains(15, 15));
        assertTrue(triangle.contains(15, 15));

        // point not within 2 unit from shape
        double buffer = 2;
        assertFalse(line.contains(10, 11 + buffer));
        assertFalse(rectangle.contains(11 + buffer, 21 + buffer));
        assertFalse(ellipse.contains(9, 9));
        assertFalse(diagonalCross.contains(10, 11 + buffer));
        assertFalse(moon.contains(17, 15));
        assertFalse(heart.contains(10, 20));
        assertFalse(triangle.contains(10, 10));
    }
}
