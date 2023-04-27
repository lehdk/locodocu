package dk.abandonship.gui.controller;

import dk.abandonship.utils.ControllerAssistant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {
    @FXML private BorderPane borderPane;
    private ControllerAssistant controllerAssistant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();
        controllerAssistant.setBorderPane(borderPane);
        try {
            controllerAssistant.setCenterFX("LogIn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}