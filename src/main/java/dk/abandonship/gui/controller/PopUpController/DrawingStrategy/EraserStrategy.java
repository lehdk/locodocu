package dk.abandonship.gui.controller.PopUpController.DrawingStrategy;

import javafx.scene.canvas.GraphicsContext;

public class EraserStrategy implements IDrawingStrategy {

    private static final double size = 30;

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.clearRect(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public void drag(GraphicsContext gc, double x, double y) {
        draw(gc, x, y);
    }
}
