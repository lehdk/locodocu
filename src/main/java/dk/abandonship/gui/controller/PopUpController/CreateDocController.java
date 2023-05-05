package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateDocController implements Initializable {
    @FXML private TextField fieldDocName;
    @FXML private Button btnCancel, btnConfirm;
    private Project project;
    private ProjectModel projectModel;
    private ControllerAssistant controllerAssistant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();

        try {
            projectModel = new ProjectModel();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    public void setProject(Project project){
        this.project = project;
        System.out.println(project.toString());
    }

    public void handleCancel(ActionEvent actionEvent) {
        close();
    }

    public void handleConfirm(ActionEvent actionEvent) {
        if (fieldDocName.getText().isEmpty()) {
            controllerAssistant.displayAlert("Enter Name Of Doc");
            return;
        }

        try {
            projectModel.createNewDoc(fieldDocName.getText(), project);
            close();
        } catch (Exception e){
            controllerAssistant.displayError(e);
        }
    }

    private void close(){
        Stage stage  = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
