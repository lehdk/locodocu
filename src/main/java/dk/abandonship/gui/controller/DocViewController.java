package dk.abandonship.gui.controller;

import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.ControllerAssistant;
import dk.abandonship.utils.DefaultRoles;
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
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class DocViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vbox, vbox2;
    @FXML private ComboBox<Documentation> docs;
    private Project project;
    private ProjectModel projectModel;
    private ControllerAssistant controllerAssistant;

    private LinkedHashMap<Node, DocumentationNode> nodeMap;

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

    /**
     * Init all buttons and fields under existing document if there are any fields
     */
    private void openDoc() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        HBox savAndCancelBox = new HBox();
        savAndCancelBox.setAlignment(Pos.CENTER);
        savAndCancelBox.setSpacing(15);

        vbox2 = new VBox();

        nodeMap = new LinkedHashMap<>();

        if(LoggedInUserState.getInstance().hasRole(DefaultRoles.TECHNICIAN)) {
            Button btnAddTextField = new Button("Add Text Field");
            Button btnAddLogin = new Button("Add Login");
            Button btnAddImage = new Button("Add Image");
            btnAddTextField.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addTextFieldForEdit(null));
            btnAddLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addLogin(null));
            btnAddImage.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addPicture(null));
            Button btnSave = new Button("Save");
            Button btnCancel = new Button("Cancel");
            btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> save());
            btnCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> cancel());

            hBox.getChildren().add(btnAddTextField);
            hBox.getChildren().add(btnAddLogin);
            hBox.getChildren().add(btnAddImage);

            savAndCancelBox.getChildren().add(btnSave);
            savAndCancelBox.getChildren().add(btnCancel);
        }

        Button btnPrint = new Button("Print PDF");
        btnPrint.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> print());

        savAndCancelBox.getChildren().add(btnPrint);

        vbox.getChildren().add(vbox2);
        vbox.getChildren().add(hBox);
        vbox.getChildren().add(savAndCancelBox);

        Documentation documentation = docs.getValue();
        try {
            projectModel.loadDocumentationData(documentation);
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

        for (DocumentationNode dn: documentation.getDocumentationNodes()) {
            if (dn instanceof DocumentationTextFieldNode){
                addTextFieldForEdit((DocumentationTextFieldNode) dn);
            } else if (dn instanceof DocumentationLogInNode){
                addLogin((DocumentationLogInNode) dn);
            } else if (dn instanceof DocumentationPictureNode){
                addPicture((DocumentationPictureNode) dn);
            }
        }
    }

    /**
     * saves node data to DB where docs value are
     */
    private void save() {
        try {
            projectModel.saveToDB(nodeMap, docs.getValue());
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    /**
     * Discard changes and edits center view to project view
     */
    private void cancel() {
        try {
            controllerAssistant.setCenterFX("projectsView");
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }


    private void print() {
        //TODO: make print PDF
    }

    /**
     * creates a text area
     * @param docNode a docNode that can contain exiting data, should be null if it's a new field
     */
    private void addTextFieldForEdit(DocumentationTextFieldNode docNode){
        TextArea field = new TextArea();
        nodeMap.put(field,docNode);
        vbox2.getChildren().add(field);
        vbox2.getChildren().add(new Label("\n\n")); //Mini spacing

        if(docNode != null){
            field.setText(docNode.getText());
        }
    }

    /**
     * Creates a login in a vbox
     * @param docNode a docNode that can contain exiting data, should be null if it's a new field
     */
    private void addLogin(DocumentationLogInNode docNode){
        VBox vboxLog = new VBox();
        TextField username = new TextField();
        TextField password = new TextField();

        vboxLog.setAlignment(Pos.CENTER_LEFT);
        vboxLog.getChildren().add(new Label("UserName"));
        vboxLog.getChildren().add(username);
        vboxLog.getChildren().add(new Label("PassWord"));
        vboxLog.getChildren().add(password);

        nodeMap.put(vboxLog, docNode);

        vbox2.getChildren().add(vboxLog);
        vbox2.getChildren().add(new Label("\n\n"));

        if (docNode != null) {
            username.setText(docNode.getUsername());
            password.setText(docNode.getPassword());
        }
    }

    /**
     *  creates a picture view with a field and imageview
     * @param docNode a docNode that can contain exiting data, should be null if it's a new field
     */
    private void addPicture(DocumentationPictureNode docNode){
        VBox vboxPic = new VBox();
        HBox hBox = new HBox();
        TextField field = new TextField();
        ImageView view = new ImageView();

        vboxPic.getChildren().add(new Label("Title"));
        vboxPic.getChildren().add(field);
        vboxPic.getChildren().add(new Label(""));

        Button btnSetPicture = new Button("Set Picture");
        btnSetPicture.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> selectPic(view, btnSetPicture));

        hBox.setSpacing(30);

        hBox.getChildren().add(view);

        vboxPic.getChildren().add(hBox);
        vboxPic.getChildren().add(new Label(""));

        if(LoggedInUserState.getInstance().hasRole(DefaultRoles.TECHNICIAN))
            vboxPic.getChildren().add(btnSetPicture);

        nodeMap.put(vboxPic, docNode);

        vbox2.getChildren().add(vboxPic);
        vbox2.getChildren().add(new Label("\n\n"));

        if (docNode != null) {
            field.setText(docNode.getPictureTittle());
            ImageView loadView = new ImageView();


            loadView.setImage(docNode.getImages());

            loadView.setFitHeight(300);
            loadView.setFitWidth(300);
            loadView.setPreserveRatio(true);

            hBox.getChildren().add(loadView);
        }
    }


    /**
     * Oppens a FileChoser to sellect an image
     * @param view IMageView the Picture should be displayed in
     * @param btn the btn thats  pressed to open filechosser
     */
    private void selectPic(ImageView view, Button btn) {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            Stage stage = (Stage) btn.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null && selectedFile.getName().endsWith(".png") || selectedFile != null && selectedFile.getName().endsWith(".jpg")
                    || selectedFile != null && selectedFile.getName().endsWith(".gif")) {

                Image image = new Image(selectedFile.toURI().toString());

                view.setImage(image);

                view.setFitHeight(300);
                view.setFitWidth(300);
                view.setPreserveRatio(true);


            } else {
                controllerAssistant.displayAlert("could not initialize picture");
            }

        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }
}
