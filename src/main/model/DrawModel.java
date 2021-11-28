package main.model;
import java.util.ArrayList;

import main.model.figure.*;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.lang.IndexOutOfBoundsException;
import java.awt.Color;

/**
 * The DrawModel class is a container for Figure objects. 
 * It provides methods to add, update and select the Figure objects within it.
 * It also provides methods to save a history of up to 10 changes, as well as supports undo and redo functionalities.
 * @see Figure
 */
public class DrawModel {
    private PropertyChangeSupport notifier;
    private ArrayList<Figure> figures; // the main state of the DrawModel and contianer of all its Figure objects
    private ArrayList<ArrayList<Figure>> undoStack;
    private ArrayList<ArrayList<Figure>> redoStack;
    private Figure currentFigure;
    private final int firstIndex = 0;

    /**
     * Construct a new DrawModel object.
     * Initialize private fields and set up property change support.
     */
    public DrawModel() {
        notifier = new PropertyChangeSupport(this);
        figures = new ArrayList<Figure>();
        undoStack = new ArrayList<ArrayList<Figure>>();
        redoStack = new ArrayList<ArrayList<Figure>>();
    }

    /**
     * Add listener who will get updates on changes within the DrawModel object.
     * @param listener the PropertyChangeListener to be added
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * To be called whenever there is a change within the object.
     */
    private void update() {
        // hack to ensure all call to update will fire change
        boolean oldVal = true;
        boolean newVal = false;
        notifier.firePropertyChange("DrawModel update", oldVal, newVal);
    }

    
    /** 
     * Create and add a new Figure object. Set the currently selected Figure to be the new object.
     * @param type the Figure type
     * @param x1 the x coordinate of the upper-left corner of the bounding box of the Figure object
     * @param y1 the y coordinate of the upper-left corner of the bounding box of the Figure object
     * @param color the color of the Figure object
     * @param fill whether the Figure object is filled
     */
    public void createNewFigure(String type, double x1, double y1, Color color, boolean fill) {
        try {
            switch (type) {
                case "line":
                    currentFigure = new Line(x1, y1, color, fill);
                    break;
                case "rectangle":
                    currentFigure = new Rectangle(x1, y1, color, fill);
                    break;
                case "ellipse":
                    currentFigure = new Ellipse(x1, y1, color, fill);
                    break;
                case "cross":
                currentFigure = new DiagonalCross(x1, y1, color, fill);
                break;
                case "heart":
                currentFigure = new Heart(x1, y1, color, fill);
                break;
                case "triangle":
                currentFigure = new Triangle(x1, y1, color, fill);
                break;
                case "moon":
                currentFigure = new Moon(x1, y1, color, fill);
                break;
                default:
                    throw new IllegalArgumentException(type + " is not supported.");
            }
            figures.add(firstIndex, currentFigure);
            update();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /** 
     * Update the bounding box of the selected Figure object.
     * @param x1 the x coordinate of the upper-left corner of the bounding box of the Figure object
     * @param y1 the y coordinate of the upper-left corner of the bounding box of the Figure object
     * @param width the width of the bounding box of the Figure object
     * @param height the height of the bounding box of the Figure object
     * @throws NullPointerException if no Figure object is selected for update
     */
    public void updateCurrentFigure(double x1, double y1, double width, double height) throws NullPointerException {
        try {
            if (currentFigure != null) {
                currentFigure.updateBound(x1, y1, width, height);
                update();
            } else {
                throw new NullPointerException("No current figure selected for update.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /** 
     * Update the color of the selected Figure object.
     * @param color the Color object for the Figure object
     * @throws NullPointerException if no Figure object is selected for update
     */
    public void updateCurrentFigure(Color color) throws NullPointerException {
        try {
            if (currentFigure != null) {
                currentFigure.updateColor(color);
                update();
            } else {
                throw new NullPointerException("No current figure selected for update.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /** 
     * Update whther the selected Figure object is filled or outlined.
     * @param fill a boolean value of filled or not
     * @throws NullPointerException if no Figure object is selected for update
     */
    public void updateCurrentFigure(boolean fill) throws NullPointerException {
        try {
            if (currentFigure != null) {
                currentFigure.updateFill(fill);
                update();
            } else {
                throw new NullPointerException("No current figure selected for update.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /** 
     * Shift the selected Figure object by xDiff horizontally and yDiff vertically.
     * @param xDiff a horizontal delta value
     * @param yDiff a vertical delta value
     * @throws NullPointerException if no Figure object is selected for update
     */
    public void shiftCurrentFigure(double xDiff, double yDiff) throws NullPointerException {
        try {
            if (currentFigure != null) {
                currentFigure.shift(xDiff, yDiff);
                update();
            } else {
                throw new NullPointerException("No current figure selected for shifting.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /** 
     * Select the most recently-created Figure that contains point (x, y)
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @throws IllegalArgumentException if the point coordinates are negative
     */
    public void selectTopFigureContains(double x, double y) throws IllegalArgumentException{
        currentFigure = null;
        if (x >= 0 && y >= 0) {
            for (Figure f : figures) {
                if (f.contains(x, y)) {
                    currentFigure = f;
                    System.out.println("Draw Model: select figure " + f);
                    break;
                }
            }
            update();
        } else {
            throw new IllegalArgumentException("Point coordinates must be non-negative.");
        }
    }

    
    /** 
     * Return to a previous state before the latest change was made.
     * @throws IndexOutOfBoundsException if no previous state is found
     */
    public void undo() throws IndexOutOfBoundsException {
        if (undoStack.size() != 0) {
            redoStack.add(firstIndex, figures);
            figures = undoStack.remove(firstIndex);
            currentFigure = null;
            update();
        } else {
            throw new IndexOutOfBoundsException("undoStack is empty. Can't undo further");
        }
    }

    
    /** 
     * Go to the state that has been undone by the undo method.
     * @throws IndexOutOfBoundsException if already at latest state
     */
    public void redo() throws IndexOutOfBoundsException {
        if (redoStack.size() != 0) {
            undoStack.add(firstIndex, figures);
            figures = redoStack.remove(firstIndex);
            currentFigure = null;
            update();
        } else {
            throw new IndexOutOfBoundsException("redoStack is empty. Can't redo further.");
        }
    }

    /**
     * Clear all Figure objects within the DrawModel object.
     */
    public void clearAll() {
        figures.clear();
        update();
    }

    
    /** 
     * Deep clone the current state.
     * @return ArrayList<Figure> the cloned state with deep copies of all its Figure objects
     */
    private ArrayList<Figure> cloneState() {
        ArrayList<Figure> clone = new ArrayList<Figure>();
        for (Figure f : figures) {
            clone.add(f.clone());
        }
        return clone;
    }

    /**
     * Save a deep copy of the current state to undo stack.
     * Remove the oldest state if undo stack contains more than 10 states.
     * Clear redo stack.
     */
    public void saveHistory() {
        undoStack.add(firstIndex, cloneState());
        if (undoStack.size() > 10) {
            undoStack.remove(undoStack.size() - 1);
        }
        redoStack.clear();
    } 

    /**
     * Update the currentFigure (the selected Figure object) pointer to null.
     */
    public void emptyCurrentFigure() {
        currentFigure = null;
    }

    
    /** 
     * @return the currently selected Figure object.
     */
    public Figure getCurrentFigure() {
        return currentFigure;
    }

    
    /** 
     * @return whether a Figure object is selected for update.
     */
    public boolean hasCurrentFigure() {
        if (currentFigure == null) {
            return false;
        }
        return true;
    }

    
    /** 
     * @return ArrayList<Figure> the current state of all Figure objects.
     */
    public ArrayList<Figure> getFigures() {
        return figures;
    }

    
    /** 
     * @return ArrayList<ArrayList<Figure>> the undo stack of all previous states.
     */
    public ArrayList<ArrayList<Figure>> getUndoStack() {
        return undoStack;
    }
    
    
    /** 
     * @return ArrayList<ArrayList<Figure>> the redo stack of all states that have been undone.
     */
    public ArrayList<ArrayList<Figure>> getRedoStack() {
        return redoStack;
    }
}
