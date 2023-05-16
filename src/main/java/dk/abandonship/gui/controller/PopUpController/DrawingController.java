package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.Main;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.IDrawingStrategy;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.ImageDrawingStrategy;
import dk.abandonship.gui.controller.PopUpController.DrawingStrategy.LineDrawStrategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DrawingController implements Initializable {

    @FXML private Canvas canvasDrawing;
    @FXML private Button btnSaveToDoc, btnClose, tbnScreen, btnSound, btnWifi, btnJunction, bntBrush,  btnColorPicker, bntEraser;
    private IDrawingStrategy imageMonitor, imageSpeaker, imageWifi, imageJunctionBox, lineDrawStrategy;
    private IDrawingStrategy selectedStrategy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageMonitor = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/monitor.png"))));
        imageSpeaker = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/speaker.png"))));
        imageWifi = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/wifi.png"))));
        imageJunctionBox = new ImageDrawingStrategy(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/JunctionBoxpng.png"))));
        lineDrawStrategy = new LineDrawStrategy();

        selectedStrategy = imageMonitor;
    }

    public void handleSaveToDoc(ActionEvent actionEvent) {
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

    public void handlePickColor(ActionEvent actionEvent) {
    }

    public void handleEraser(ActionEvent actionEvent) {

    }

    public void handelClose(ActionEvent actionEvent) {
        close();
    }

    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void canvasClick(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getX() + "  :  " + mouseEvent.getY());

        GraphicsContext gc = canvasDrawing.getGraphicsContext2D();
        gc.setLineWidth(5);

        selectedStrategy.draw(gc, mouseEvent.getX(), mouseEvent.getY());
    }
}
