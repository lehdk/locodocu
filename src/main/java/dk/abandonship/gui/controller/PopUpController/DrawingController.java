package dk.abandonship.gui.controller.PopUpController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawingController implements Initializable {


    @FXML private Canvas canvasDrawing;
    @FXML private Button btnSaveToDoc, btnClose, tbnScreen, btnSound, btnWifi, btnJunction, bntBrush,  btnColorPicker, bntEraser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void handleSaveToDoc(ActionEvent actionEvent) {
    }

    public void handleSpawnScreen(ActionEvent actionEvent) {
    }

    public void handleSpawnSound(ActionEvent actionEvent) {
    }

    public void handleSpawnWifi(ActionEvent actionEvent) {
    }

    public void handleSpawnJunction(ActionEvent actionEvent) {
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
}
