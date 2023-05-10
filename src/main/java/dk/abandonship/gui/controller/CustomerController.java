package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;
import dk.abandonship.gui.controller.PopUpController.AddEditCustomerController;
import dk.abandonship.gui.model.CustomerModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.DefaultRoles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private final CustomerModel customerModel;

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private HBox buttonsHBox;

    private Button deleteCustomerButton, editCustomerButton;

    public CustomerController() {
        customerModel = new CustomerModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        customerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateSelectedDisabledButtons());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        customerTableView.setItems(customerModel.getCustomerObservableList());

        buttonsHBox.setSpacing(10);

        if(LoggedInUserState.getInstance().getLoggedInUser().hasRole(DefaultRoles.PROJECTMANAGER, DefaultRoles.SALESPERSON)) {
            Button addCustomerButton = new Button("Add Customer");
            addCustomerButton.setOnAction(event -> {
                try {
                    handleAddEditCustomer(null);
                } catch (IOException e) {
                    System.err.println("Could not open window!");
                    e.printStackTrace();
                }
            });
            buttonsHBox.getChildren().add(addCustomerButton);

            editCustomerButton = new Button("Edit Customer");
            editCustomerButton.setOnAction(event -> {
                var selectedItem = customerTableView.getSelectionModel().getSelectedItem();
                if(selectedItem == null) return;

                try {
                    handleAddEditCustomer(selectedItem);
                } catch (IOException e) {
                    System.err.println("Could not open window!");
                    throw new RuntimeException(e);
                }

            });
            buttonsHBox.getChildren().add(editCustomerButton);

            deleteCustomerButton = new Button("Delete Customer");
            deleteCustomerButton.setOnAction(event -> handleDeleteCustomer());
            buttonsHBox.getChildren().add(deleteCustomerButton);
        }

        updateSelectedDisabledButtons();
    }

    /**
     * Updates the delete customer button.
     * Disables the button if no customer is selected.
     */
    private void updateSelectedDisabledButtons() {
        var selectedItem = customerTableView.getSelectionModel().getSelectedItem();

        if(deleteCustomerButton != null) {
            deleteCustomerButton.setDisable(selectedItem == null);
        }

        if(editCustomerButton != null) {
            editCustomerButton.setDisable(selectedItem == null);
        }
    }

    /**
     * Adds or edits a customer
     * @param customer The customer you want to edit. Null of add mode.
     */
    public void handleAddEditCustomer(Customer customer) throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/AddEditCustomerView.fxml"));

        Parent root = loader.load();
        AddEditCustomerController controller = loader.getController();

        if(customer != null) {
            controller.editMode(customer);
        }

        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        popupStage.showAndWait();

        CustomerDTO result = controller.getResult();
        if(result == null) return;

        try {
            if(customer == null) {
                // Add a new customer
                customerModel.addCustomer(result);
            } else {
                // Edit a current customer
                boolean wasEdited = customerModel.editCustomer(customer, result);
                if(wasEdited) customerTableView.refresh();
                customerTableView.getSelectionModel().clearSelection();
            }
        } catch (SQLException e) {
            System.err.println("Could not add customer!");
            e.printStackTrace();
        }
    }

    public void handleDeleteCustomer() {
        //TODO update dellete stmt
        var selectedItem = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) return;

        try {
            customerModel.deleteCustomer(selectedItem);
        } catch (SQLException e) {
            System.err.println("Could not delete customer!");
            e.printStackTrace();
        }
    }
}
