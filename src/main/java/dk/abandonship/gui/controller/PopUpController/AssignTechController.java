package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.Project;
import dk.abandonship.entities.User;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.gui.model.UserModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AssignTechController implements Initializable {
    @FXML private Button bntCancel, btnConfirm;
    @FXML private CheckComboBox<User> comboBoxTech;

    private UserModel userModel;
    private ProjectModel projectModel;
    private ControllerAssistant controllerAssistant;
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();
        userModel = new UserModel();

        comboBoxTech.setTitle("Technicians");

        try {
            projectModel =  new ProjectModel();
            comboBoxTech.getItems().addAll(userModel.getAllTechnicians());
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    /**
     * gives the project that should be assigned technicians to.
     * and select already selected technicians.
     * @param project the project that technicians should be assigned to
     */
    public void setProject(Project project) {
        this.project = project;

        List<User> alreadyAssign = project.getAssignedTechnicians();
        for (var v : comboBoxTech.getItems()) {
            if (alreadyAssign.contains(v)) {
                comboBoxTech.getCheckModel().check(v);
            }
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        close();
    }

    /**
     * Closes pop-up
     */
    private void close(){
        Stage stage  = (Stage) bntCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Saves the technistans in a list and sends them to DAO to be saved
     * @param actionEvent
     */
    public void handleConfirm(ActionEvent actionEvent) {
        List<User> selected = new ArrayList<>();

        for (User u : comboBoxTech.getItems()) {
            if(comboBoxTech.getItemBooleanProperty(u).get()){
                selected.add(u);
            }
        }

        if (selected.isEmpty()) {
            controllerAssistant.displayAlert("Select a technician");
            return;
        }

        try {
            projectModel.saveTechOnProject(selected, project);
            close();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

    }
}
