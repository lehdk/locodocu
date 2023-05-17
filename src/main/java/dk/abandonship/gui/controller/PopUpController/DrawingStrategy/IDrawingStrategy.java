package dk.abandonship.gui.controller.PopUpController.DrawingStrategy;

import javafx.scene.canvas.GraphicsContext;


public interface IDrawingStrategy {

    /**
     * Draws the strategy coordinates
     * @param gc graphic context that can be drawn on (Canvas)
     * @param x X-coordinates to draw
     * @param y Y-coordinates to draw
     */
    void draw(GraphicsContext gc, double x, double y);

    /**
     * Draws the strategy coordinates (To be used on drag methods)
     * @param gc graphic context that can be drawn on (Canvas)
     * @param x X-coordinates to draw
     * @param y Y-coordinates to draw
     */
    void drag(GraphicsContext gc, double x, double y);
}
