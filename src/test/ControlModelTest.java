package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.model.ControlModel;

import java.awt.Color;

/**
 * Test ControlModel object.
 */
public class ControlModelTest {
    ControlModel control;
    
    @Before
    public void setup() {
        control = new ControlModel();
    }

    /**
     * Test all setter methods.
     */
    @Test
    public void testSetters() {
        assertEquals(Color.orange, control.getColor());
        assertEquals(Color.blue, control.toggleColor());
        
        control.toggleFill();
        assertTrue(control.getFill());

        control.toggleDrawMode();
        assertFalse(control.isDrawMode());

        control.setFigureType("rectangle");
        assertEquals("rectangle", control.getFigureType());

        control.setStartPoint(1, 1);
        assertEquals(1, control.getX1());
        assertEquals(1, control.getY1());

        assertEquals(1, control.getXDiff(2));
        assertEquals(2, control.getX1());
        assertEquals(1, control.getYDiff(2));
        assertEquals(2, control.getY1());

    }

    /**
     * Test setting negative starting point.
     */
    @Test(expected = IllegalArgumentException.class) 
    public void testIllegalStartPoint() {
        control.setStartPoint(-1, -1);
    }

    /**
     * Test illegal figure type.
     */
    @Test(expected = IllegalArgumentException.class) 
    public void testIllegalFigureType() {
        control.setFigureType("foo");
    }
}
