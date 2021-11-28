package main;

import main.controller.DrawController;
import main.model.ControlModel;
import main.model.DrawModel;
import main.view.DrawGUI;

/**
 * A Vector Graphics drawing program that allows users to draw basic geometric shapes in differrent colors, fill, aspect ratio.
 * The DrawModel object manages the geometric shapes as objects of the Figure class.
 * The ControlModel object keeps all the drawing options.
 * The DrawGUI renders the shapes and the drawing options.
 * The DrawController object facillitates interactions betwen the GUI and the model.
 */
public class Main {
    public static void main(String[] args) {
        DrawModel drawModel = new DrawModel();
        ControlModel controlModel = new ControlModel();
        DrawController controller = new DrawController(drawModel, controlModel);
        new DrawGUI(drawModel, controlModel, controller);
    }
}