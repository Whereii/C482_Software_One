package com.example.testfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <h1>AddPartController Class</h1>
 * This class is used to handle the add_part.fxml page and all the functionality
 * @author Joshua McCausey
 */
public class AddPartController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    private int part_type_option = 0;



    @FXML
    private TextField part_name;
    @FXML
    private TextField part_inv;
    @FXML
    private TextField part_price;
    @FXML
    private TextField part_max;
    @FXML
    private TextField part_min;

    @FXML
    private TextField part_type_input;
    @FXML
    private Label part_type;
    @FXML
    private Label error_label;

    /**
     * This method is used to switch to the outsourced part section.
     */
    public void outsourced(ActionEvent event) throws IOException{
        part_type.setText("Company Name");
        part_type_option = 1;
    }
    /**
     * This method is used to switch to the inHouse part section.
     */
    public void in_house(ActionEvent event) throws IOException{
        part_type.setText("Machine ID");
        part_type_option = 0;
    }


    /**
     * This method is used to switch to the main page upon click.
     */
    public void switch_to_main(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * * <h1>RUNTIME ERROR</h1>
     * This method is used to add a part to the inventory upon click.
     * The error checking for this method took a good bit of time but in the end did not turn out to be
     * that complicated
     */
    public void add_new_part(ActionEvent event) throws IOException{
        try {
            if(Integer.parseInt(part_min.getText()) >= Integer.parseInt(part_max.getText()) || Integer.parseInt(part_inv.getText()) > Integer.parseInt(part_max.getText()) || Integer.parseInt(part_inv.getText()) < Integer.parseInt(part_min.getText())){
                error_label.setText("Error: Min must be less than Max and Inventory must be between the two");
            } else {
                if (part_type_option == 0) {
                    InHouse part = new InHouse(Inventory.getAllParts().size() + 1, part_name.getText(), Double.parseDouble(part_price.getText()), Integer.parseInt(part_inv.getText()), Integer.parseInt(part_min.getText()), Integer.parseInt(part_max.getText()), Integer.parseInt(part_type_input.getText()));
                    Inventory.addPart(part);
                } else if (part_type_option == 1) {
                    Outsourced part = new Outsourced(Inventory.getAllParts().size() + 1, part_name.getText(), Double.parseDouble(part_price.getText()), Integer.parseInt(part_inv.getText()), Integer.parseInt(part_min.getText()), Integer.parseInt(part_max.getText()), part_type_input.getText());
                    Inventory.addPart(part);
                }

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
