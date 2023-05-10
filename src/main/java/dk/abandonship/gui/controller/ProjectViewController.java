package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.Project;
import dk.abandonship.gui.controller.PopUpController.AssignTechController;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.ControllerAssistant;
import dk.abandonship.utils.DefaultRoles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {
    @FXML private TableColumn projectName, customerName,  docCount;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private TableView projectTableView;
    @FXML
    private VBox vbox;

    private ProjectModel projectModel;

    private final ControllerAssistant controllerAssistant;

    public ProjectViewController() {
        controllerAssistant = ControllerAssistant.getInstance();
        try {
            projectModel = new ProjectModel();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateSelectedDisabledButtons());

        setOpenProjectBtn();

        if (LoggedInUserState.getInstance().getLoggedInUser().hasRole(DefaultRoles.PROJECTMANAGER)) {
            setNewProjectBtn();
            setAssignBtn();
        }

        buttonsHBox.setSpacing(15);
        setProjects();


    }

    private void updateSelectedDisabledButtons() {
    }

    private void setOpenProjectBtn(){
        Button btn = new Button("Open Project");
        btn.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btn.setPrefHeight(Region.USE_COMPUTED_SIZE);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openProject((Project) projectTableView.getSelectionModel().getSelectedItem()));
        buttonsHBox.getChildren().add(btn);
    }

    private void setNewProjectBtn() {

        Button btn = new Button("Add New Project");
        btn.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btn.setPrefHeight(Region.USE_COMPUTED_SIZE);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addProject());
        buttonsHBox.getChildren().add(btn);
    }
    private void setAssignBtn() {
        Button btn = new Button("Assign Technicians");
        btn.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btn.setPrefHeight(Region.USE_COMPUTED_SIZE);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> assignTechnicians((Project) projectTableView.getSelectionModel().getSelectedItem()));
        buttonsHBox.getChildren().add(btn);
    }

    private void setProjects() {
        projectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customer"));
        docCount.setCellValueFactory(new PropertyValueFactory<>("documentations"));


        projectTableView.setItems(projectModel.getProjectObservableList());

    }


    private void addProject() {
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/CreateProjectView.fxml"));
            Parent root = loader.load();
            Scene popupScene = new Scene(root);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openProject(Project project) {
        try {
            var controller = (DocViewController) controllerAssistant.setCenterFX("DocView");
            controller.setProject(project);
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

    }

    private void assignTechnicians(Project project) {
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/AssignTechView.fxml"));
            Parent root = loader.load();
            Scene popupScene = new Scene(root);

            AssignTechController techController = loader.getController();
            techController.setProject(project);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openItem(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && projectTableView.getSelectionModel().getSelectedItem() != null) //Checking double click
        {
            openProject((Project) projectTableView.getSelectionModel().getSelectedItem());
        }
    }
}
