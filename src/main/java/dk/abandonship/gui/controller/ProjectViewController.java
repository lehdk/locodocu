package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.Project;
import dk.abandonship.gui.controller.PopUpController.AssignTechController;
import dk.abandonship.gui.controller.PopUpController.CreateProjectView;
import dk.abandonship.gui.controller.PopUpController.OldProjectController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {
    @FXML private TextField fieldSearch;
    @FXML private TableColumn<Project, String> projectName, projectAddress, projectPostalCode, customerName,  docCount, createdAt;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private TableView<Project> projectTableView;

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
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openProject(projectTableView.getSelectionModel().getSelectedItem()));
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
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> assignTechnicians(projectTableView.getSelectionModel().getSelectedItem()));
        buttonsHBox.getChildren().add(btn);
    }

    /**
     * sets the value of the tableview with data from observable list over projects in model
     */
    private void setProjects() {
        projectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        projectAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        projectPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        customerName.setCellValueFactory(new PropertyValueFactory<>("customer"));
        docCount.setCellValueFactory(new PropertyValueFactory<>("documentations"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        projectTableView.setItems(projectModel.getProjectObservableList());

    }

    public void isOld(){
        List<Project> oldProjects = new ArrayList<>();

        LocalDate now = LocalDate.now();

        for (var v : projectModel.getProjectObservableList()){

            if (v.getCreatedAt().toLocalDateTime().isBefore(now.minusMonths(48).atStartOfDay())){
                oldProjects.add(v);
            }
        }

        if (!oldProjects.isEmpty())openOldProjectPop(oldProjects);
    }

    /**
     * open createProject view in a pop-up box
     */
    private void addProject() {
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/CreateProjectView.fxml"));
            Parent root = loader.load();
            var controller = (CreateProjectView) loader.getController();
            Scene popupScene = new Scene(root);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.showAndWait();

            var result = controller.getResult();

            if(result == null) return;

            projectModel.createProject(result);

            projectTableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open project and sets it in center view
     * @param project project that should be opened
     */
    private void openProject(Project project) {
        try {
            var controller = (DocViewController) controllerAssistant.setCenterFX("DocView");
            controller.setProject(project);
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

    }

    /**
     * open assignTechVIew in a pop-up box
     * @param project project technicians should be assigned to
     */
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

    private void openOldProjectPop(List<Project> oldProjects) {
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/OldProjectPopUp.fxml"));
            Parent root = loader.load();
            Scene popupScene = new Scene(root);

            OldProjectController oldController = loader.getController();
            oldController.setOldProjects(oldProjects);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.showAndWait();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    /**
     * Set doubleClick to open a doc on the project Tableview
     * @param mouseEvent click from mouse on a item in table
     */
    public void openItem(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && projectTableView.getSelectionModel().getSelectedItem() != null) //Checking double click
        {
            openProject(projectTableView.getSelectionModel().getSelectedItem());
        }
    }


    /**
     * serchesin model for the prompt in the fieldSearch
     */
    public void search() {
        projectTableView.setItems(projectModel.getSearchResult(fieldSearch.getText().toLowerCase()));
    }
}
