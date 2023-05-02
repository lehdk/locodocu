package dk.abandonship.utils;

import dk.abandonship.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    /**
     * sets center FXML to the given fxml name
     * returns Controller
     * @param file
     * @throws Exception
     */
    public Object setCenterFX(String file) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/" + file + ".fxml"));
        Node newScene = loader.load();
        borderPane.setCenter(newScene);

        return loader.getController();
    }

    /**
     * sets top FXML to the given fxml name
     * @param file
     * @throws Exception
     */
    public void setTopFX(String file) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/" + file + ".fxml"));
        Node newScene = loader.load();
        borderPane.setTop(newScene);
    }

    public void displayAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("!!Invaid!!");
        alert.setHeaderText("Something went wrong, \n" + message);
        alert.showAndWait();
    }

    public void displayError(Throwable t)
    {
        t.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
        t.printStackTrace();
    }


}