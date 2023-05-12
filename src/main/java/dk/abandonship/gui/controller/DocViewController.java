package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.Documentation;
import dk.abandonship.entities.Project;
import dk.abandonship.entities.documetationNodes.DocumentationLogInNode;
import dk.abandonship.entities.documetationNodes.DocumentationNode;
import dk.abandonship.entities.documetationNodes.DocumentationPictureNode;
import dk.abandonship.entities.documetationNodes.DocumentationTextFieldNode;
import dk.abandonship.gui.controller.PopUpController.CreateDocController;
import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.ControllerAssistant;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class DocViewController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox vboxModifyButtons, vboxIOButtons;
    @FXML private ComboBox<Documentation> docs;
    private Project project;
    private ProjectModel projectModel;
    private ControllerAssistant controllerAssistant;
    private LinkedHashMap<Node, DocumentationNode> nodeMap;

    private boolean userIsAssignedTechnician = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            controllerAssistant = ControllerAssistant.getInstance();
            projectModel = new ProjectModel();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vboxModifyButtons.setSpacing(10);

        HBox hBox = new HBox();
        Button btn = new Button("Create new doc");
        docs = new ComboBox<>();

        hBox.getChildren().add(docs);
        hBox.getChildren().add(btn);

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        docs.valueProperty().addListener(event -> openDoc());
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> createNewDoc());

        vboxModifyButtons.getChildren().add(hBox);
        vboxModifyButtons.getChildren().add(new Label("\n\n"));
    }

    public void setProject(Project project){
        this.project = project;
        docs.setItems(FXCollections.observableArrayList(project.getDocumentations()));
    }

    /**
     * Init all buttons and fields under existing document if there are any fields
     */
    private void openDoc() {
        Documentation documentation = docs.getValue();

        userIsAssignedTechnician = project.getAssignedTechnicians().contains(LoggedInUserState.getInstance().getLoggedInUser());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        HBox savAndCancelBox = new HBox();
        savAndCancelBox.setAlignment(Pos.CENTER);
        savAndCancelBox.setSpacing(15);

        vboxIOButtons = new VBox();

        nodeMap = new LinkedHashMap<>();

        if(userIsAssignedTechnician) {
            Button btnAddTextField = new Button("Add Text Field");
            Button btnAddLogin = new Button("Add Login");
            Button btnAddImage = new Button("Add Image");
            btnAddTextField.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> addTextFieldForEdit(null));
            btnAddLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> handleAddLogin(null));
            btnAddImage.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> handleAddPicture(null));
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

        savAndCancelBox.getChildren().add(btnPrint);

        vboxModifyButtons.getChildren().add(vboxIOButtons);
        vboxModifyButtons.getChildren().add(hBox);
        vboxModifyButtons.getChildren().add(savAndCancelBox);

        try {
            projectModel.loadDocumentationData(documentation);
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }

        for (DocumentationNode dn: documentation.getDocumentationNodes()) {
            if (dn instanceof DocumentationTextFieldNode){
                addTextFieldForEdit((DocumentationTextFieldNode) dn);
            } else if (dn instanceof DocumentationLogInNode){
                handleAddLogin((DocumentationLogInNode) dn);
            } else if (dn instanceof DocumentationPictureNode){
                handleAddPicture((DocumentationPictureNode) dn);
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

    /**
     * creates a text area
     * @param docNode a docNode that can contain exiting data, should be null if it's a new field
     */
    private void addTextFieldForEdit(DocumentationTextFieldNode docNode) {

        if(docNode == null) {
            docNode = new DocumentationTextFieldNode(DocumentationNode.UNUSED_NODE_ID, "");
        }

        TextArea field = new TextArea();
        field.setDisable(!userIsAssignedTechnician);
        nodeMap.put(field, docNode);
        vboxIOButtons.getChildren().add(field);
        vboxIOButtons.getChildren().add(new Label("\n\n")); //Mini spacing

        if(docNode.getId() != DocumentationNode.UNUSED_NODE_ID){
            field.setText(docNode.getText());
        }
    }

    /**
     * Creates a login in a vbox
     * @param docNode a docNode that can contain exiting data, should be null if it's a new field
     */
    private void handleAddLogin(DocumentationLogInNode docNode){

        if(docNode == null) {
            docNode = new DocumentationLogInNode(DocumentationNode.UNUSED_NODE_ID, "", "", "");
        }

        VBox vboxLog = new VBox();
        TextField device = new TextField();
        TextField username = new TextField();
        TextField password = new TextField();

        device.setDisable(!userIsAssignedTechnician);
        username.setDisable(!userIsAssignedTechnician);
        password.setDisable(!userIsAssignedTechnician);

        vboxLog.setAlignment(Pos.CENTER_LEFT);
        vboxLog.getChildren().add(new Label("Device"));
        vboxLog.getChildren().add(device);
        vboxLog.getChildren().add(new Label("Username"));
        vboxLog.getChildren().add(username);
        vboxLog.getChildren().add(new Label("Password"));
        vboxLog.getChildren().add(password);

        nodeMap.put(vboxLog, docNode);

        vboxIOButtons.getChildren().add(vboxLog);
        vboxIOButtons.getChildren().add(new Label("\n\n"));

        if (docNode.getId() != DocumentationNode.UNUSED_NODE_ID) {
            device.setText(docNode.getDevice());
            username.setText(docNode.getUsername());
            password.setText(docNode.getPassword());
        }
    }

    /**
     * Adds a picture node to the documentation.
     * @param pictureNode The picture node to add. Set to null if this is a new field.
     */
    private void handleAddPicture(DocumentationPictureNode pictureNode) {
        if(pictureNode == null) {
            pictureNode = new DocumentationPictureNode(DocumentationNode.UNUSED_NODE_ID, "", null);
        }

        VBox container = new VBox();
        container.setSpacing(5);

        nodeMap.put(container, pictureNode);

        container.getChildren().add(new Label("Title"));
        var titleTextField = new TextField();
        container.getChildren().add(titleTextField);

        ImageView imageView = new ImageView();
        container.getChildren().add(imageView);
        updateShownImage(imageView, pictureNode);

        var setImageButton = new Button("Set Image");
        DocumentationPictureNode finalDocNode = pictureNode;
        setImageButton.setOnAction(event -> handleSetImage(finalDocNode, imageView));
        container.getChildren().add(setImageButton);

        if (pictureNode.getId() != DocumentationNode.UNUSED_NODE_ID) {
            titleTextField.setText(pictureNode.getPictureTitle());
        }

        vboxIOButtons.getChildren().add(container);
    }

    /**
     * Updates the shown image
     * @param imageView The image view to update
     * @param pictureNode The image to show.
     */
    private void updateShownImage(ImageView imageView, DocumentationPictureNode pictureNode) {
        if(pictureNode.getImageData() == null) return;

        imageView.setFitHeight(400);
        imageView.setFitWidth(400);

        var image = new Image(new ByteArrayInputStream(pictureNode.getImageData()));
        imageView.setImage(image);
    }

    /**
     * Opens a file chooser and saves the image data into the DocumentationPictureNode.
     * @param pictureNode The picture node to set image data to.
     * @param imageView The associated image view.
     */
    private void handleSetImage(DocumentationPictureNode pictureNode, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) scrollPane.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile == null || !selectedFile.canRead()) {
            controllerAssistant.displayAlert("Could not read the chose image");
        }

        try(var fileInputStream = new FileInputStream(selectedFile)) {
            var imageData = fileInputStream.readAllBytes();

            pictureNode.setImageData(imageData);
        } catch (IOException ess) {
            throw new RuntimeException(ess);
        }

        updateShownImage(imageView, pictureNode);
    }

    private void createNewDoc(){
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/CreateDoc.fxml"));
            Parent root = loader.load();
            Scene popupScene = new Scene(root);

            CreateDocController docController = loader.getController();
            docController.setProject(project);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.showAndWait();

            docs.setItems(FXCollections.observableArrayList(project.getDocumentations()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
