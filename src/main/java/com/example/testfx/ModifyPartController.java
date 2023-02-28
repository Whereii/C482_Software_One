package com.example.testfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>ModifyPartController Class</h1>
 * This class is used to handle the modify_part.fxml page and all the functionality
 * @author Joshua McCausey
 */
public class ModifyPartController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int part_type_option = 0;


    @FXML
    private TextField part_id;
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
    private RadioButton IH;
    @FXML
    private RadioButton OS;
    @FXML
    private Label error_label;

    private Part modify_part = Inventory.lookupPart(Controller.modify_part_id);


    //if 0 then the part is in-house if 1 then the part is outsourced
    private int ih_or_os = 0;



    /**
     * This method is used to initialize the modify part fields and populate the data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Checks to see if modify_part is outsourced or in-house
        if(modify_part.getClass() == Outsourced.class){
            ih_or_os = 1;
            part_type.setText("Company Name");
            OS.setSelected(true);
        }

        part_id.setText(String.valueOf(modify_part.getId()));
        part_name.setText(modify_part.getName());
        part_inv.setText(String.valueOf(modify_part.getStock()));
        part_price.setText(String.valueOf(modify_part.getPrice()));
        part_max.setText(String.valueOf(modify_part.getMax()));
        part_min.setText(String.valueOf(modify_part.getMin()));

        if(ih_or_os == 0){
            //downcast the part since storing it in a list of type Part required a custom casting to access the getMachineId method
            InHouse downcast_part = (InHouse) modify_part;
            part_type_input.setText(String.valueOf(downcast_part.getMachineId()));
        } else {
            //downcast the part since storing it in a list of type Part required a custom casting to access the getMachineId method
            Outsourced downcast_part = (Outsourced) modify_part;
            part_type_input.setText(downcast_part.getCompanyName());
        }


    }
    /**
     * This method is used to switch to the outsourced part section.
     */
    public void outsourced(ActionEvent event) throws IOException {
        part_type.setText("Company Name");
        ih_or_os = 1;
    }
    /**
     * This method is used to the inHouse part section.
     */
    public void in_house(ActionEvent event) throws IOException{
        part_type.setText("Machine ID");
        ih_or_os = 0;
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
     * This method is used to push the newly modified part to the inventory to replace the old part.
     * I had issues using the built-in list method called indexOf because when i passed it the proper object that I wanted
     * to find the index of, it would return -1 indicating it was not present in the list. I then had the idea
     * to just create my own custom index tracking variable and that seemed to work just fine.
     */
    public void modify_part(ActionEvent event) throws IOException {
        try {
            if(Integer.parseInt(part_min.getText()) >= Integer.parseInt(part_max.getText()) || Integer.parseInt(part_inv.getText()) > Integer.parseInt(part_max.getText()) || Integer.parseInt(part_inv.getText()) < Integer.parseInt(part_min.getText())){
                error_label.setText("Error: Min must be less than Max and Inventory must be between the two");
            } else {
                //indexOf for the Inventory container was not working properly so I created a custom way of checking the index
                int index = 0;

                for (Part part : Inventory.getAllParts()) {
                    if (part.getId() == Controller.modify_part_id) {
                        break;
                    } else {
                        index++;
                    }
                }

                if (ih_or_os == 0) {
                    InHouse part = new InHouse(modify_part.getId(), part_name.getText(), Double.parseDouble(part_price.getText()), Integer.parseInt(part_inv.getText()), Integer.parseInt(part_min.getText()), Integer.parseInt(part_max.getText()), Integer.parseInt(part_type_input.getText()));
                    Inventory.updatePart(index, part);
                } else if (ih_or_os == 1) {
                    Outsourced part = new Outsourced(modify_part.getId(), part_name.getText(), Double.parseDouble(part_price.getText()), Integer.parseInt(part_inv.getText()), Integer.parseInt(part_min.getText()), Integer.parseInt(part_max.getText()), part_type_input.getText());
                    Inventory.updatePart(index, part);
                }

                root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e){
            error_label.setText("Error: Fill all fields with valid input");
        }
    }
}
