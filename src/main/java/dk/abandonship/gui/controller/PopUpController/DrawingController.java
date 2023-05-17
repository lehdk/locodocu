package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.Main;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.EraserStrategy;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.IDrawingStrategy;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.ImageDrawingStrategy;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.LineDrawStrategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DrawingController implements Initializable {

    @FXML private Pane container;
    @FXML private Canvas canvasDrawing;
    @FXML private Button btnSaveToDoc, btnClose, tbnScreen, btnSound, btnWifi, btnJunction, bntBrush,  bntEraser;
    private IDrawingStrategy imageMonitor, imageSpeaker, imageWifi, imageJunctionBox, lineDrawStrategy, eraserStrategy;
    private IDrawingStrategy selectedStrategy;
    private ImageView renderedImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageMonitor = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/monitor.png"))));
        imageSpeaker = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/speaker.png"))));
        imageWifi = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/wifi.png"))));
        imageJunctionBox = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/JunctionBoxpng.png"))));
        lineDrawStrategy = new LineDrawStrategy();
        eraserStrategy = new EraserStrategy();

        selectedStrategy = imageMonitor;

        container.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvasDrawing.setWidth(newVal.doubleValue());
        });

        container.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvasDrawing.setHeight(newVal.doubleValue());
        });

        renderedImageView = null;
    }

    public void handleSaveToDoc(ActionEvent actionEvent) {
        WritableImage writableImage = new WritableImage( (int) canvasDrawing.getWidth(), (int) canvasDrawing.getHeight());
        canvasDrawing.snapshot(null, writableImage);
        WritableImage image = canvasDrawing.snapshot(new SnapshotParameters(), writableImage);



        renderedImageView.setImage(image);

        close();
    }

    public void setImageView(ImageView imageView) {
        renderedImageView = imageView;
    }

    public void handleSelectMonitor(ActionEvent actionEvent) {
        selectedStrategy = imageMonitor;
    }

    public void handleSelectSpeaker(ActionEvent actionEvent) {
        selectedStrategy = imageSpeaker;
    }

    public void handleSelectWifi(ActionEvent actionEvent) {
        selectedStrategy = imageWifi;
    }

    public void handleSelectJunction(ActionEvent actionEvent) {
        selectedStrategy = imageJunctionBox;
    }

    public void handleSelectLine(ActionEvent actionEvent) {
        selectedStrategy = lineDrawStrategy;
    }

    public void handleEraser(ActionEvent actionEvent) {
        selectedStrategy = eraserStrategy;
    }

    public void handelClose(ActionEvent actionEvent) {
        close();
    }

    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void canvasClick(MouseEvent mouseEvent) {
        GraphicsContext gc = canvasDrawing.getGraphicsContext2D();
        gc.setLineWidth(5);

        selectedStrategy.draw(gc, mouseEvent.getX(), mouseEvent.getY());
    }

    public void handleDrag(MouseEvent mouseEvent) {
        GraphicsContext gc = canvasDrawing.getGraphicsContext2D();
        gc.setLineWidth(5);

        selectedStrategy.drag(gc, mouseEvent.getX(), mouseEvent.getY());
    }
}
