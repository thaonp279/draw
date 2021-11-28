package main.controller;

import java.awt.Color;
import java.util.ArrayList;

import main.model.ControlModel;
import main.model.DrawModel;
import main.model.figure.Figure;


/**
 * The DrawController object handle all controls between the view package and the model package.
 */
public class DrawController {
    DrawModel draw;
    ControlModel control;

    /**
     * Construct new DrawController object.
     * @param drawModel the DrawModel object that maintains all geometric shapes that have been drawn.
     * @param controlModel the ControlModel object that maintains all the drawing options.
     */
    public DrawController(DrawModel drawModel, ControlModel controlModel) {
        draw = drawModel;
        control = controlModel;
    }

    /**
     * Handle mouse pressed on the view canvas between 2 modes: draw or select.
     * @param x x coordinate of mouse pressed event
     * @param y y coordinate of mouse pressed event
     */
    public void pressedCanvas(double x, double y) {
        control.setStartPoint(x, y);
        if (control.isDrawMode()) {
            draw.saveHistory();
            draw.createNewFigure(control.getFigureType(), x, y, control.getColor(), control.getFill());
        } else {
            draw.selectTopFigureContains(x, y);
        }
    }

    /**
     * Handle mouse dragged on the view canvas between 2 modes: draw or select.
     * @param x2 x coordinate of mouse dragged event
     * @param y2 y coordinate of mouse dragged event
     * @param aspectRatio whether aspect ratio is requested
     */
    public void draggedCanvas(double x2, double y2, boolean aspectRatio) {
        if (draw.hasCurrentFigure()) {

            if (control.isDrawMode()) {
                double x1 = control.getX1();
                double y1 = control.getY1();
                double xDiff = x2 - x1;
                double yDiff = y2 - y1;
                double width = Math.abs(xDiff);
                double height = Math.abs(yDiff);

                if (control.getFigureType().equals("line")) {
                    if (aspectRatio) { // make straight horizontal or vertical line
                        if (width > height) {
                            yDiff = 0;
                        } else {
                            xDiff = 0;
                        }
                    }
                    draw.updateCurrentFigure(x1, y1, xDiff, yDiff);
                } else {
                    if (aspectRatio) { // ensures width = height of bounding box
                        width = Math.min(width, height);
                        height = width;
                    }

                    // update the current Figure object with new location
                    // ensure its x1, y1 always on the upper left corner
                    if (x2 <= x1 && y2 <= y1) { // mouse is on the upper left of (x1, y1)
                        x1 -= width;
                        y1 -= height;
                    } else if (x2 <= x1 && y2 >= y1) { // mouse is on the bottom left of (x1, y1)
                        x1 -= width;
                    } else if (x2 >= x1 && y2 <= y1) { // mouse is on the bottm right of (x1, y1)
                        y1 -= height;
                    }

                    draw.updateCurrentFigure(x1, y1, width, height);
                }

            } else {
                // shift object if in select mode
                draw.shiftCurrentFigure(control.getXDiff(x2), control.getYDiff(y2));
            }
        }
    }

    /**
     * Complete updating current figure. Move currentFigure object to null.
     */
    public void releasedCanvas() {
        if (control.isDrawMode()) {
            draw.emptyCurrentFigure();
        }
    }

    /**
     * Handle undo.
     */
    public void undo() {
        try {
            draw.undo();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle redo.
     */
    public void redo() {
        try {
            draw.redo();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update next color option and update the color of currentFigure in DrawModel object.
     */
    public void toggleColor() {
        Color color = control.toggleColor();
        if (draw.hasCurrentFigure()) {
            draw.saveHistory();
            draw.updateCurrentFigure(color);
        }
    }

    /**
     * Toggle the fill option and update the fill option of currentFigure in DrawModel object.
     */
    public void toggleFill() {
        control.toggleFill();
        if (draw.hasCurrentFigure()) {
            draw.saveHistory();
            draw.updateCurrentFigure(control.getFill());
        }
    }

    /**
     * Toggle between usage modes draw and select.
     */
    public void toggleDrawMode() {
        control.toggleDrawMode();
    }

    /**
     * Pass figure type selection to ControlModel object.
     * @param type
     */
    public void setFigureType(String type) {
        try {
            control.setFigureType(type);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle clear all option.
     */
    public void clearAll() {
        draw.clearAll();
    }

    /** Get current figures state of the DrawModel object */
    public ArrayList<Figure> getFigures() {
        return draw.getFigures();
    }

}
