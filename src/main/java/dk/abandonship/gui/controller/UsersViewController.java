package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

    public Button openAddUserWindowButton;
    public VBox adminUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleOpenNewUserWindow() throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/dk/abandonship/gui/view/NewUser.fxml"));
        Parent root = loader.load();
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        popupStage.showAndWait();
    }

    public void createUserNode() {

    }
}
