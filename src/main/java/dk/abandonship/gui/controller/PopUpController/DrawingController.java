package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.Main;
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
    private Image imageMonitor, imageSpeaker, imageWifi, imageJunctionBox;
    private Image image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageMonitor = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/monitor.png")));
        imageSpeaker = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/speaker.png")));
        imageWifi = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/wifi.png")));
        imageJunctionBox = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/Canvas/JunktionBoxpng.png")));

        image = imageMonitor;
    }

    public void handleSaveToDoc(ActionEvent actionEvent) {
    }

    public void handleSpawnScreen(ActionEvent actionEvent) {
        image = imageMonitor;
    }

    public void handleSpawnSound(ActionEvent actionEvent) {
        image = imageSpeaker;
    }

    public void handleSpawnWifi(ActionEvent actionEvent) {
        image = imageWifi;
    }

    public void handleSpawnJunction(ActionEvent actionEvent) {
        image = imageJunctionBox;
    }

    public void handleBrush(ActionEvent actionEvent) {
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

        //gc.fillOval(mouseEvent.getX(), mouseEvent.getY(), 10,10);

        gc.drawImage(image, mouseEvent.getX()-150/2, mouseEvent.getY()-150/2 ,150,150);
    }



}
