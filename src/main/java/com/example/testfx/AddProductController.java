package com.example.testfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>AddProductController Class</h1>
 * This class is used to handle the add_product.fxml page and all the functionality
 * @author Joshua McCausey
 */
public class AddProductController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField product_name;
    @FXML
    private TextField product_inv;
    @FXML
    private TextField product_price;
    @FXML
    private TextField product_max;
    @FXML
    private TextField product_min;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, Integer> partId2;

    @FXML
    private TableColumn<Part, Integer> partInv;
    @FXML
    private TableColumn<Part, Integer> partInv2;

    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, String> partName2;

    @FXML
    private TableColumn<Part, Double> partPrice;
    @FXML
    private TableColumn<Part, Double> partPrice2;

    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Part> includedPartsTable;
    @FXML
    private TextField part_search;
    @FXML
    private Label error_label;

    //temp_parts_holder was created so that I could adjust the contents without actually affecting the main Inventory
    private ObservableList<Part> temp_parts_holder = FXCollections.observableArrayList();

    //temp_associated_parts_holder was needed to be a temporary storage for storing part objects until I could push them into the new product
    private ObservableList<Part> temp_associated_parts_holder = FXCollections.observableArrayList();

    /**
     * This method is used to initialize the product tables and populate the data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populates proper tables
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partId2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInv2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partName2.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPrice2.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));


        temp_parts_holder.addAll(Inventory.getAllParts());

        //Setup the search function for the temp_parts_holder
        FilteredList<Part> filteredParts = new FilteredList<>(temp_parts_holder, b -> true);

        part_search.textProperty().addListener((observable, oldValue, newValue)-> {
            filteredParts.setPredicate(newPart -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (newPart.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(newPart.getId()).indexOf(lowerCaseFilter) != -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Part> sortedParts = new SortedList<>(filteredParts);

        sortedParts.comparatorProperty().bind(partsTable.comparatorProperty());

        partsTable.setItems(sortedParts);

        //Populate second table
        FilteredList<Part> filteredParts2 = new FilteredList<>(temp_associated_parts_holder, b -> true);

        SortedList<Part> sortedParts2 = new SortedList<>(filteredParts2);

        sortedParts2.comparatorProperty().bind(includedPartsTable.comparatorProperty());

        includedPartsTable.setItems(sortedParts2);

    }

    /**
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to validate the entry into the part_search textfield.
     * Originally I wanted to create a single if statement to figure out
     * if the input was valid or not but quickly realized I needed to break it up
     * due to having to use two methods with the same name but both having a different data type
     * for their arguments. lookupPart(int) and lookupPart(string)
     */    public void part_search_check(KeyEvent event) throws IOException{
        if(part_search.getText().isEmpty()){
            error_label.setText("");
        }else if (part_search.getText().toLowerCase().matches(".*[a-z].*")) {
            if(Inventory.lookupPart(part_search.getText()).isEmpty() && !part_search.getText().isEmpty()){
                error_label.setText("Error: No part ID or part name match your input");
            } else{
                error_label.setText("");
            }
        } else{
            if(Inventory.lookupPart(Integer.parseInt(part_search.getText())).getName() == "fail" && Inventory.lookupPart(part_search.getText()).isEmpty() && !part_search.getText().isEmpty()){
                error_label.setText("Error: No part ID or part name match your input");
            } else{
                error_label.setText("");
            }
        }
    }

    /**
     * This method is used to add a part from the first table to the second upon click.
     */
    public void push_part(ActionEvent event) throws IOException {
        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            error_label.setText("Error: Pick a part to add");
        } else {
            temp_associated_parts_holder.add(partsTable.getSelectionModel().getSelectedItem());
            temp_parts_holder.remove(partsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * This method is used to add a part from the second table to the first upon click.
     */
    public void part_remove(ActionEvent event) throws IOException {
        if (includedPartsTable.getSelectionModel().getSelectedItem() == null) {
            error_label.setText("Error: Pick a part to remove");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + includedPartsTable.getSelectionModel().getSelectedItem().getName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                temp_parts_holder.add(includedPartsTable.getSelectionModel().getSelectedItem());
                temp_associated_parts_holder.remove(includedPartsTable.getSelectionModel().getSelectedItem());
            }
        }
    }


    /**
     * This method is used to switch to the main form upon click.
     */
    public void switch_to_main(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method is used to add a product to the inventory storage.
     */
    public void add_new_product(ActionEvent event) throws IOException {
        try {
            if(Integer.parseInt(product_min.getText()) >= Integer.parseInt(product_max.getText()) || Integer.parseInt(product_inv.getText()) > Integer.parseInt(product_max.getText()) || Integer.parseInt(product_inv.getText()) < Integer.parseInt(product_min.getText())){
                error_label.setText("Error: Min must be less than Max and Inventory must be between the two");
            } else {

                Product product = new Product(Inventory.getAllProducts().size() + 1, product_name.getText(), Double.parseDouble(product_price.getText()), Integer.parseInt(product_inv.getText()), Integer.parseInt(product_min.getText()), Integer.parseInt(product_max.getText()));
                temp_associated_parts_holder.forEach(part -> {
                    product.addAssociatedPart(part);
                });
                Inventory.addProduct(product);

                root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch(Exception e){
            error_label.setText("Error: Fill all fields with valid input");
        }
    }
}
