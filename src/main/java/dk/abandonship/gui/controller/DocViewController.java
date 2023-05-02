package dk.abandonship.gui.controller;

import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DocViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vbox;
    @FXML private ComboBox<Documentation> docs;
    private Project project;
    private ProjectModel projectModel;
    private ControllerAssistant controllerAssistant;

    private ArrayList<Node> nodeArray;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            controllerAssistant = ControllerAssistant.getInstance();
            projectModel = new ProjectModel();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vbox.setSpacing(10);
        docs = new ComboBox<>();
        docs.valueProperty().addListener(event -> openDoc());
        vbox.getChildren().add(docs);
    }

    public void setProject(Project project){
        this.project = project;
        docs.setItems(FXCollections.observableArrayList(project.getDocumentations()));
    }

    private void openDoc(){
        nodeArray = new ArrayList<>();
        Documentation documentation = docs.getValue();
        System.out.println("Init Doc:  " + documentation);
        Button button = new Button("addField");

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addTextFieldForEdit());

        vbox.getChildren().add(button);

    }

    private void addTextFieldForEdit(){
        TextArea field = new TextArea();
        nodeArray.add(field);
        vbox.getChildren().add(field);
        vbox.getChildren().add(new Label("\n\n")); //Mini spacing

        System.out.println(nodeArray.size());
    }
}
