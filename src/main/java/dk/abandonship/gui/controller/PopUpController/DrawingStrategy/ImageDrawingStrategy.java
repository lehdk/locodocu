package dk.abandonship.gui.controller.PopUpController.DrawingStrategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class ImageDrawingStrategy implements IDrawingStrategy{
    private Image image;

    public ImageDrawingStrategy(Image image) {
        this.image = image;
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.drawImage(image, x-150/2, y-150/2 ,150,150);
    }
}
