package dk.abandonship.utils;

import dk.abandonship.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

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
        ControllerAssistant.borderPane = borderPane;
    }

    /**
     * Sets center FXML to the given fxml name
     * returns Controller
     * @param file The name of the FXML file you want to show.
     * @throws IOException If the FXML file could not be loaded.
     */
    public Object setCenterFX(String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/" + file + ".fxml"));
        Node newScene = loader.load();
        borderPane.setCenter(newScene);

        return loader.getController();
    }

    /**
     * sets top FXML to the given fxml name
     * @param file The name of the FXML file you want to show. Null if clear the area.
     * @throws IOException If FXML file could not be loaded
     */
    public void setTopFX(String file) throws IOException {
        if(file == null) {
            borderPane.setTop(null);
            return;
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/" + file + ".fxml"));
        Node newScene = loader.load();
        borderPane.setTop(newScene);
    }

    public void displayAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void displayError(Throwable t) {
        t.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
        t.printStackTrace();
    }
}