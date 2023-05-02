package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.Role;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.ControllerAssistant;
import dk.abandonship.utils.DefaultRoles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vbox;

    private ProjectModel projectModel;
    private LoggedInUserState state;

    private ControllerAssistant controllerAssistant;

    public ProjectViewController() {
        state = LoggedInUserState.getInstance();
        controllerAssistant = ControllerAssistant.getInstance();
        try {
            projectModel = new ProjectModel();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        if (state.hasRole(DefaultRoles.ADMIN)) {
            setAdminBtn();
        }

        setProjects();


    }

    private void setAdminBtn(){

        Button btn = new Button("+");
        btn.setPrefWidth(1450);
        btn.setPrefHeight(125);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addProject());
        btn.setStyle("-fx-font-size: 60px");
        vbox.getChildren().add(btn);
    }

    private void setProjects(){
        vbox.setSpacing(5);

        for (var p : projectModel.getProjectObservableList()) {
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
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
            Button btn = new Button("View details");
            vBox.getChildren().add(btn);
            vBox.setStyle("-fx-background-color: #030202");
            vBox.getChildren().add(new Label(" \n"));

            vbox.getChildren().add(vBox);
        }
    }

    private void addProject(){
        //FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/CreateProjectView.fxml"));


        //Method 1
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/CreateProjectView.fxml"));
            Parent root = loader.load();
            Scene popupScene = new Scene(root);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.showAndWait();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}