package dk.abandonship.gui.controller;

import dk.abandonship.entities.Role;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.state.LoggedInUserState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vbox;

    private final ProjectModel projectModel;
    private LoggedInUserState state;

    public ProjectViewController() throws SQLException {
        projectModel = new ProjectModel();
        state = LoggedInUserState.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        if (state.getLoggedInUser().getRoles().contains(new Role(1,"admin"))) {
            setAdminBtn();
        }

        setProjects();

    }

    private void setAdminBtn(){
        Button btn = new Button("+");
        btn.setScaleX(vbox.getScaleX());
        btn.setScaleY(vbox.getLayoutX());
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addProject());
        vbox.getChildren().add(btn);
    }

    private void setProjects(){
        vbox.setSpacing(5);

        for (var p : projectModel.getProjectObservableList()) {
            Pane pane = new Pane();
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            Label projectName = new Label();
            projectName.textProperty().setValue("Project: " + p.getName());
            projectName.setPadding(new Insets(20,20,20,20));
            hBox.getChildren().add(projectName);

            Label customerName = new Label();
            customerName.textProperty().setValue("Customer: " + p.getCustomer().getName());
            customerName.setPadding(new Insets(20,20,20,20));
            hBox.getChildren().add(customerName);

            Label documentationCount = new Label();
            documentationCount.textProperty().setValue("Documentation count: " + p.getDocumentations().size());
            documentationCount.setPadding(new Insets(20,20,20,20));
            hBox.getChildren().add(documentationCount);

            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
            Button btn =new Button("View details");
            vBox.getChildren().add(btn);

            pane.setPrefSize(vBox.getPrefWidth()+60,vBox.getPrefHeight()+60);
            pane.setStyle("-fx-background-color: #030202");
            pane.getChildren().add(vBox);
            pane.setPadding(new Insets(50,50,50,50));
            vbox.getChildren().add(pane);
        }
    }

    private void addProject(){
        System.out.println("reee");
    }
}
