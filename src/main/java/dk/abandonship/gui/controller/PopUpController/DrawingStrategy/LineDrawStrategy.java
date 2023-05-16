package dk.abandonship.gui.controller.PopUpController.DrawingStrategy;

import javafx.scene.canvas.GraphicsContext;

public class LineDrawStrategy implements IDrawingStrategy {

    private Double startX = null;
    private Double startY = null;

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        if(startX == null) {
            startX = x;
            startY = y;
        } else {
            gc.strokeLine(startX, startY, x, y);

            startX = null;
            startY = null;
        }
    }
}
