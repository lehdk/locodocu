package dk.abandonship.gui.controller.PopUpController.DrawingStrategy;

import javafx.scene.canvas.GraphicsContext;


public interface IDrawingStrategy {
    void draw(GraphicsContext gc, double x, double y);
}
