package dk.abandonship.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
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
            Pane pane = new Pane();
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            Label label1 = new Label("lbl1");
            label1.setPadding(new Insets(20,20,20,20));
            hBox.getChildren().add(label1);

            Label label2 = new Label("lbl2");
            label2.setPadding(new Insets(20,20,20,20));
            hBox.getChildren().add(label2);

            Label label3 = new Label("lbl3");
            label3.setPadding(new Insets(20,20,20,20));
            hBox.getChildren().add(label3);

            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
            Button btn =new Button("reee");
            vBox.getChildren().add(btn);

            pane.setPrefSize(vBox.getPrefWidth()+60,vBox.getPrefHeight()+60);
            pane.setStyle("-fx-background-color: #030202");
            pane.getChildren().add(vBox);
            pane.setPadding(new Insets(50,50,50,50));
            vbox.getChildren().add(pane);
        }
    }
}
