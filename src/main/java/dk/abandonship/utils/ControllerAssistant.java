package dk.abandonship.utils;

import dk.abandonship.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ControllerAssistant {
    @FXML private static BorderPane borderPane;
    private static ControllerAssistant controllerAssistant;

    private ControllerAssistant() {
    }

    public static ControllerAssistant getInstance(){
        if (controllerAssistant == null) controllerAssistant = new ControllerAssistant();

        return controllerAssistant;
    }

    public void setBorderPane(BorderPane borderPane){
        this.borderPane = borderPane;
    }

    public void setCenterFX(String file) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/" + file + ".fxml"));
        Node newScene = loader.load();
        borderPane.setCenter(newScene);
    }

    public void setTopFX(String file) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/" + file + ".fxml"));
        Node newScene = loader.load();
        borderPane.setTop(newScene);
    }


}