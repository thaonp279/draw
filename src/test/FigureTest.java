package test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.model.figure.*;

import java.awt.Color;

/**
 * Testing general Figure object's functionality.
 */
public class FigureTest {

    private Figure figure;
    
    @Before
    public void setup() {
        figure = new Rectangle(10, 10, Color.blue, false);
    }

    /**
     * Test updating x1, y1, width, height, color, fill.
     */
    @Test 
    public void testUpdate() {  
        // update x1, y1, width and height
        figure.updateBound(5, 5, 5, 5);
        assertEquals(5, figure.getX1());
        assertEquals(5, figure.getY1());
        assertEquals(5, figure.getWidth());
        assertEquals(5, figure.getHeight());

        // update width and height
        figure.updateArea(-1, -1);
        assertEquals(-1, figure.getWidth());
        assertEquals(-1, figure.getHeight());

        // update upper left corner
        figure.updateAnchorPoint(1, 1);
        assertEquals(1, figure.getX1());
        assertEquals(1, figure.getY1());

        // update color
        figure.updateColor(Color.pink);
        assertEquals(Color.pink, figure.getColor());

        // update fill
        figure.updateFill(true);
        assertTrue(figure.getFill());
    }

    /**
     * Test negative anchor point.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBoundUpdate() {
        figure.updateAnchorPoint(-1, -1);
    }

    /**
     * Test shifting the figure horizontally and vertically.
     */
    @Test
    public void testShift() {
        // positive shift
        figure.shift(10, 10);
        assertEquals(20, figure.getX1());
        assertEquals(20, figure.getY1());

        // zero shift
        figure.shift(0, 0);
        assertEquals(20, figure.getX1());
        assertEquals(20, figure.getY1());

        // negative shift
        figure.shift(-5, -5);
        assertEquals(15, figure.getX1()); 
        assertEquals(15, figure.getY1()); 
    }

    /**
     * Test shifting figure out of frame (x1, y1 become negative).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalShift() {
        figure.shift(-15, -15);
    }

    /**
     * Test deep copy of clone.
     */
    @Test
    public void testClone() {
        Figure figureClone = figure.clone();
        assertEquals(figure.getX1(), figureClone.getX1());
        assertEquals(figure.getY1(), figureClone.getY1());
        assertEquals(figure.getWidth(), figureClone.getWidth());
        assertEquals(figure.getHeight(), figureClone.getHeight());
        assertEquals(figure.getColor(), figureClone.getColor());
        assertEquals(figure.getFill(), figureClone.getFill());
    }

}
