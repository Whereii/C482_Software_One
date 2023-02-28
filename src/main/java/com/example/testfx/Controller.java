package com.example.testfx;

import javafx.application.Platform;
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
 * <h1>Controller Class</h1>
 * This class is used to handle the main_form.fxml page and all the functionality
 * @author Joshua McCausey
 */
public class Controller implements Initializable{

    @FXML
    private Label error_label;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, Integer> partInv;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, Integer> productInv;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productPrice;

    @FXML
    private TableView<Product> productTable;
    /**
     * This method is used to initialize the starting tables and to populate them.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populate the tables
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));


        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));


        //Incorporate search mechanisms into the parts table
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), b -> true);

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


        //Incorporate search mechanisms into the products table
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.getAllProducts(), b -> true);

        product_search.textProperty().addListener((observable2, oldValue2, newValue2)-> {
            filteredProducts.setPredicate(newProduct -> {
                if(newValue2 == null || newValue2.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue2.toLowerCase();

                if (newProduct.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(newProduct.getId()).indexOf(lowerCaseFilter) != -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);

        sortedProducts.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedProducts);

    }


    private static Stage stage;
 private Scene scene;
 private Parent root;

public static int modify_part_id; //Useful for the modify parts section

public static int modify_product_id; //Useful for the modify products section


    @FXML
 private TextField part_search;
 @FXML
 private TextField product_search;

    /**
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to validate the text input into the part_search textfield.
     * Originally I wanted to create a single if statement to figure out
     * if the input was valid or not but quickly realized I needed to break it up
     * due to having to use two methods with the same name but both having a different data type
     * for their arguments. lookupPart(int) and lookupPart(string)
     */
 public void part_search_check(KeyEvent event) throws IOException{
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
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to validate the text input into the product_search textfield.
     * Originally I wanted to create a single if statement to figure out
     * if the input was valid or not but quickly realized I needed to break it up
     * due to having to use two methods with the same name but both having a different data type
     * for their arguments. lookupProduct(int) and lookupProduct(string)
     */
    public void product_search_check(KeyEvent event) throws IOException{
        if(product_search.getText().isEmpty()){
            error_label.setText("");
        }else if (product_search.getText().toLowerCase().matches(".*[a-z].*")) {
            if(Inventory.lookupProduct(product_search.getText()).isEmpty() && !product_search.getText().isEmpty()){
                error_label.setText("Error: No part ID or part name match your input");
            } else{
                error_label.setText("");
            }
        } else{
            if(Inventory.lookupProduct(Integer.parseInt(product_search.getText())).getName() == "fail" && Inventory.lookupProduct(product_search.getText()).isEmpty() && !product_search.getText().isEmpty()){
                error_label.setText("Error: No part ID or part name match your input");
            } else{
                error_label.setText("");
            }
        }

    }


    /**
     * This method is used to remove a part from the parts table upon click.
     */
 public void delete_part(ActionEvent event) throws IOException{
         if (partsTable.getSelectionModel().getSelectedItem() == null) {
             error_label.setText("Error: Pick part to delete");
         } else {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + partsTable.getSelectionModel().getSelectedItem().getName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
             alert.showAndWait();
             if (alert.getResult() == ButtonType.YES) {
                 Inventory.getAllProducts().forEach(product -> {
                     if (product.getAllAssociatedParts().contains(partsTable.getSelectionModel().getSelectedItem())) {
                         product.deleteAssociatedPart(partsTable.getSelectionModel().getSelectedItem());
                     }
                 });

                 Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
                 error_label.setText("");
             }
         }
 }

    /**
     * This method is used to remove a product from the parts table upon click.
     */
public void delete_product(ActionEvent event) throws IOException {
        if (productTable.getSelectionModel().getSelectedItem() == null) {
            error_label.setText("Error: Pick Product to delete");
        } else if (!productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
            error_label.setText("Error: Cannot delete a product that contains associated parts");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + productTable.getSelectionModel().getSelectedItem().getName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                error_label.setText("");
            }
        }
}
    /**
     * This method is used to exit the application upon click.
     */
public void exit_application(ActionEvent event) throws IOException {
    Platform.exit();
}

    /**
     * This method is used to switch to the add part form upon click.
     */
 public void switch_to_adder_form(ActionEvent event) throws IOException {
     root = FXMLLoader.load(getClass().getResource("add_part.fxml"));
     stage = (Stage)((Node)event.getSource()).getScene().getWindow();
     scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
 }

    /**
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to switch to the modify part form upon click.
     * The actual code for this method is pretty simple and is used to switch the fxml file
     * but once in the modify part controller it took me a minute to figure out how to
     * retrieve the proper part. That is when I decided on having a static member that stores the value
     * of the id of the object that is getting modified so I can access it in another controller
     */
    public void switch_to_modify_form(ActionEvent event) throws IOException {
        try {
            modify_part_id = partsTable.getSelectionModel().getSelectedItem().getId();

            root = FXMLLoader.load(getClass().getResource("modify_part.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            error_label.setText("Error: Pick part to modify");
        }
    }

    /**
     * This method is used to switch to the add product form upon click.
     */
    public void switch_to_add_product(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add_product.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to switch to the modify product form upon click.
     * The actual code for this method is pretty simple and is used to switch the fxml file
     * but once in the modify product controller it took me a minute to figure out how to
     * retrieve the proper product. That is when I decided on having a static member that stores the value
     * of the id of the object that is getting modified so I can access it in another controller
     */
    public void switch_to_modify_product(ActionEvent event) throws IOException {
     try {
         modify_product_id = productTable.getSelectionModel().getSelectedItem().getId();

         root = FXMLLoader.load(getClass().getResource("modify_product.fxml"));
         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
     } catch (Exception e){
         error_label.setText("Error: Pick product to modify");
     }
    }



}


