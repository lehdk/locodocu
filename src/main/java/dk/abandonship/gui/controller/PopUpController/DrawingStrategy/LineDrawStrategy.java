package dk.abandonship.gui.controller.PopUpController.DrawingStrategy;

import javafx.scene.canvas.GraphicsContext;

public class LineDrawStrategy implements IDrawingStrategy {
    private static final float dotSize = 8;

    private Double startX = null;
    private Double startY = null;

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        if(startX == null) {
            startX = x;
            startY = y;
            gc.fillOval(startX - dotSize / 2, startY - dotSize / 2, dotSize, dotSize);
        } else {
            gc.strokeLine(startX, startY, x, y);

            gc.fillOval( x - dotSize / 2, y - dotSize / 2, dotSize, dotSize);

            startX = null;
            startY = null;
        }
    }

    @Override
    public void drag(GraphicsContext gc, double x, double y) { }
}
