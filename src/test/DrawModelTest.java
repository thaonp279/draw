package test;

import org.junit.Before;
import org.junit.Test;

import main.model.*;
import main.model.figure.Rectangle;
import main.model.figure.Figure;

import java.awt.Color;
import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

/**
 * Tests on the DrawModel object.
 */
public class DrawModelTest {
    private DrawModel model;
    private Rectangle testFigure;

    /**
     * Initialize a DrawModel object. 
     * Create a rectangle and create a testFigure of the same parameters.
     */
    @Before
    public void setup() {
        model = new DrawModel();
        model.createNewFigure("rectangle", 10, 10, Color.black, true);
        testFigure = new Rectangle(10, 10, Color.black, true);
    }

    /**
     * Test creating a new figure and moving currentFigure pointer correctly.
     */
    @Test
    public void testCreateNewFigure() {
        // create a rectangle
        assertSame(model.getCurrentFigure(), model.getFigures().get(0)); // currentFigure's pointer to newlycreated figure
        assertTrue(model.getCurrentFigure() instanceof Rectangle); // currentFigure is Rectangle object
        assertTrue(compareFigure(testFigure, model.getCurrentFigure())); // all fields are correct
    }

    /**
     * Test updating current figure.
     */
    @Test
    public void testUpdate() {
        model.updateCurrentFigure(0, 0, 10, 10); 
        model.updateCurrentFigure(Color.pink); 
        model.updateCurrentFigure(false); 
        Rectangle compareRect = new Rectangle(0, 0, Color.pink, false);
        compareRect.updateArea(10, 10);
        assertTrue(compareFigure(compareRect, model.getCurrentFigure())); // all fields are correct
        assertEquals(Color.pink, model.getCurrentFigure().getColor());
    }

    /**
     * Test emptying currentFigure pointer.
     */
    @Test
    public void testEmptyCurrentFigure() {
        model.emptyCurrentFigure();
        assertNull(model.getCurrentFigure());
    }

    /**
     * Test updating on a null currentFigure.
     */
    @Test(expected = NullPointerException.class) 
    public void testUpdateNullFigure() {
        model.emptyCurrentFigure();
        model.updateCurrentFigure(10, 10, 10, 10);
    }

    /**
     * Test shifting currentFigure.
     */
    @Test
    public void testShiftCurrentFigure() {
        // catch illegal argument, current figure is unchanged
        model.shiftCurrentFigure(-15, -15);
        assertEquals(10, (int) model.getCurrentFigure().getX1());
        assertEquals(10, (int) model.getCurrentFigure().getY1());
        
        model.shiftCurrentFigure(15, 15);
        assertEquals(25, (int) model.getCurrentFigure().getX1());
        assertEquals(25, (int) model.getCurrentFigure().getY1());
    }

    /**
     * Test selecting the most recently created figure containing a point.
     */
    @Test
    public void testSelectTopFigure() {
        // 2 figures on top of each other
        int x1 = 10;
        int y1 = 10;
        model.createNewFigure("ellipse", x1, y1, Color.pink, false);
        Figure figure1 = model.getCurrentFigure();
        figure1.updateArea(10, 10);
        model.createNewFigure("cross", x1, y1, Color.pink, false);
        Figure figure2 = model.getCurrentFigure();
        figure2.updateArea(10, 10);
        
        // select diagonal cross on top at point (15, 15)
        model.selectTopFigureContains(15, 15);
        assertSame(figure2, model.getCurrentFigure());

        // select ellipse under the diagonal cross at point (11, 15)
        model.selectTopFigureContains(11, 15);
        assertSame(figure1, model.getCurrentFigure());

        // select null point
        model.selectTopFigureContains(0,0);
        assertNull(model.getCurrentFigure());
    }

    /**
     * Test illegal selection.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalTopFigure() {
        model.selectTopFigureContains(-1, -1);
    }

    /**
     * Test saving deep copy of the state.
     */
    @Test
    public void testSaveHistory() {
        model.saveHistory();
        // ensure copy
        assertEquals(1, model.getUndoStack().size());
        assertNotSame(model.getCurrentFigure(), model.getUndoStack().get(0));
        // ensure deep copy
        Figure savedFigure = model.getUndoStack().get(0).get(0);
        assertTrue(compareFigure(testFigure, savedFigure));
        assertNotSame(savedFigure, model.getCurrentFigure());
    }

    /**
     * Test undo and redo.
     */
    @Test
    public void testUndoRedo() {
        // state i for i is the new x2 coordinate of a rectangle
        model.saveHistory();
        model.createNewFigure("rectangle", 0, 0, Color.pink, true);
        // updated rectangle coordinate 6 times
        for (int i = 1; i <= 11; i += 1) {
            model.saveHistory();
            model.updateCurrentFigure(i, i, 0, 0);
        }
        // REDO | CURRENT | UNDO
        //      | state 11 | state 10, 9, ..., 1
        // only last 10 records are saved, state 0 was removed
        assertEquals(10, model.getUndoStack().size());
        assertEquals(0, model.getRedoStack().size());
        assertEquals(11, (int) model.getCurrentFigure().getX1());

        // undo thrice
        model.undo();
        model.undo();
        ArrayList<Figure> state9 = model.getFigures();
        model.undo();
        // state 11, 10, 9 | state 8 | state 7, 6, .., 1
        assertEquals(8, (int) model.getFigures().get(0).getX1());
        assertEquals(7, model.getUndoStack().size());
        assertEquals(3, model.getRedoStack().size());

        // state 11, 10 | state 9 | state 8, 7, .., 1
        model.redo();
        assertSame(state9, model.getFigures());

        // null       | state 12 | state 9, 8, .., 1
        model.saveHistory();
        model.createNewFigure("rectangle", 12, 12, Color.pink, true);
        assertEquals(9, model.getUndoStack().size());
        assertEquals(0, model.getRedoStack().size());
        
    }

    /**
     * Test undo with no history.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIllegalUndo() {
        model.undo(); 
    }

    /** Test redo at latest state */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIllegalRedo() {
        model.redo();
    }

    /** Test clear all Figure objects from state */
    @Test
    public void clearAll() {
        model.clearAll();
        assertEquals(0, model.getFigures().size());
    }

    /** Compare all attributes of 2 Figure objects */
    private boolean compareFigure(Figure f1, Figure f2) {
        String[] attributeNames = {"x1", "y1", "width", "height", "color", "fill"};
        for (int i = 0; i < 6; i+= 1) {
            if (!f1.getAttributes().get(i).equals(f2.getAttributes().get(i))) {
                System.out.println(String.format("f1 & f2 are different in their %s value", attributeNames[i]));
                System.out.print(f1.getAttributes().get(i) + " ");
                System.out.print(f2.getAttributes().get(i));
                System.out.println();
                return false;
            }
        }
        return true;
    }

}
