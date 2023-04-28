package dk.abandonship.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for (int i = 0; i < 100; i++) {
            vbox.getChildren().add(new Button("Reeee"));
        }
    }
}
