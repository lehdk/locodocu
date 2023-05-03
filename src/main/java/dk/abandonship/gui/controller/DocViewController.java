package dk.abandonship.gui.controller;

import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DocViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vbox, vbox2;
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
        HBox hBox = new HBox();
        HBox savAndCancelBox = new HBox();
        vbox2 = new VBox();

        nodeArray = new ArrayList<>();


        Button button1 = new Button("addField");
        Button button2 = new Button("addLog-In");
        Button button3 = new Button("addPicture");

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        Button print = new Button("Print PDF");

        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addTextFieldForEdit());
        button2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addLogin());
        button3.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addPicture());

        save.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> save());
        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> cancel());
        print.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> print());


        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        savAndCancelBox.setAlignment(Pos.CENTER);
        savAndCancelBox.setSpacing(15);

        hBox.getChildren().add(button1);
        hBox.getChildren().add(button2);
        hBox.getChildren().add(button3);

        savAndCancelBox.getChildren().add(save);
        savAndCancelBox.getChildren().add(cancel);
        savAndCancelBox.getChildren().add(print);

        vbox.getChildren().add(hBox);
        vbox.getChildren().add(vbox2);
        vbox.getChildren().add(savAndCancelBox);

    }

    private void save() {
        System.out.println("SAVE"); //TODO make saving doc to DB
    }

    private void cancel() {
        try {
            controllerAssistant.setCenterFX("projectsView");
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    private void print() {
        System.out.println("Print"); //TODO make print PDF
    }

    private void addTextFieldForEdit(){
        TextArea field = new TextArea();
        nodeArray.add(field);
        vbox2.getChildren().add(field);
        vbox2.getChildren().add(new Label("\n\n")); //Mini spacing
    }

    private void addLogin(){
        VBox vboxLog = new VBox();
        TextField field1 = new TextField();
        TextField field2 = new TextField();

        vboxLog.setAlignment(Pos.CENTER_LEFT);
        vboxLog.getChildren().add(new Label("UserName"));
        vboxLog.getChildren().add(field1);
        vboxLog.getChildren().add(new Label("PassWord"));
        vboxLog.getChildren().add(field2);

        nodeArray.add(vboxLog);

        vbox2.getChildren().add(vboxLog);
        vbox2.getChildren().add(new Label("\n\n"));
    }

    private void addPicture(){
        VBox vboxPic = new VBox();
        HBox hBox = new HBox();
        TextField field = new TextField();

        vboxPic.getChildren().add(new Label("name pictures"));
        vboxPic.getChildren().add(field);
        vboxPic.getChildren().add(new Label(""));

        Button addPicture = new Button("add Picture");
        addPicture.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> selectPic(hBox, addPicture));

        hBox.setSpacing(30);

        vboxPic.getChildren().add(hBox);
        vboxPic.getChildren().add(new Label(""));
        vboxPic.getChildren().add(addPicture);
        vbox2.getChildren().add(vboxPic);

        vbox2.getChildren().add(new Label("\n\n"));
    }

    private void selectPic(HBox box, Button btn) {
        try {
            ImageView view;

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            Stage stage = (Stage) btn.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null && selectedFile.getName().endsWith(".png") || selectedFile != null && selectedFile.getName().endsWith(".jpg")
                    || selectedFile != null && selectedFile.getName().endsWith(".gif")) {

                Image image = new Image(selectedFile.toURI().toString());

                view = new ImageView(image);

                view.setFitHeight(250);
                view.setFitWidth(250);
                view.setPreserveRatio(true);

                box.getChildren().add(view);

            } else {
                controllerAssistant.displayAlert("could not initialize picture");
            }

        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }
}
