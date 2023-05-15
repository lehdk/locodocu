package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.Project;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OldProjectController implements Initializable {
    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button btnConfirm1, btnCancel;
    private ControllerAssistant controllerAssistant;
    private ProjectModel projectModel;
    private Map<CheckBox, Project> boxMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);

        boxMap = new HashMap<>();

        controllerAssistant = ControllerAssistant.getInstance();
        try {
            projectModel = new ProjectModel();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    /**
     * gets the list from other controller.
     * @param oldProject List of projects
     */
    public void setOldProjects(List<Project> oldProject) {
        oldProjects(oldProject);
    }

    /**
     * Maps data from array in map
     * @param projects list data in map and checkboxes
     */
    private void oldProjects(List<Project> projects) {
        for (Project p : projects) {
            CheckBox cb = new CheckBox(p.getName());
            cb.setAlignment(Pos.CENTER);

            boxMap.put(cb, p);

            vBox.getChildren().add(cb);
        }
    }

    /**
     * iterates through map and selects value of all selected
     * @param actionEvent
     */
    public void handleConfirm(ActionEvent actionEvent) {
        List<Project> selectedProjects = new ArrayList<>();

        for (CheckBox cb : boxMap.keySet()) {
            if (cb.isSelected()) {
                selectedProjects.add(boxMap.get(cb));
            }
        }

        System.out.println(selectedProjects);

        try {
            projectModel.deleteProjects(selectedProjects);
            controllerAssistant.setCenterFX("projectsView");
            close();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    /**
     * call close method when received action
     * @param actionEvent mouse evetnt on btn
     */
    public void handleClose(ActionEvent actionEvent) {
        close();
    }

    /**
     * Closes pop up stage
     */
    private void close() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
