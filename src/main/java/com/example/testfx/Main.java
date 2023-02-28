package com.example.testfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//Javadoc is located in a file in the src folder
/**
 * <h1>FUTURE ENHANCEMENT</h1>
 * The Main.java class is simply the entry point the the application as a whole but
 * doesn't actually contain much code. This program is a data management application that is representative
 * of a database system for a company to keep track of inventory. The biggest enhancement I can think of that
 * could provide value to the company is more of an outlook on where the parts come from in or out of the company and what
 * that costs in terms of monetary value or time and where the products go post build/sell and the kind of revenue that is
 * generated.
 * @author  Joshua McCausey
 * @version 1.0
 * @since   2023-02-21
 */
public class Main extends Application {

    /**
     This method is used to initialize the entire program
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
        This method is used to initialize the entire program
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("main_form.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}