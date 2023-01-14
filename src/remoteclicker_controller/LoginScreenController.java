/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteclicker_controller;

import java.net.URL;


import java.sql.*;
import java.io.*;


import javafx.fxml.Initializable;
import java.util.Optional;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.*;


import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Alert.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import javafx.scene.*;
import javafx.stage.*;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.EventHandler;
import static javafx.scene.input.KeyCode.R;

/**
 * FXML Login Screen Controller class
 *
 * @author Mothy
 */
public class LoginScreenController {
    
    //TN: Controller number entry field:
    @FXML
    private TextField loginTextEntryField;
    
    //TN: Controller number entry field:
    @FXML
    private Label loginErrorTextField;

    //TN: Login instruction and error message display field:
    @FXML
    private Label loginTextField;
    
    //TN: Login screen button object:
    @FXML
    private Button activateButton;
    
    boolean validController = false;
    
    public static String controllerNumber;
 	
    //@Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //TN: activates login screen button
        //activateButton.setOnAction(event -> submitLogin(event));
        loginTextField.setText("Remote Clicker Prototype");
        //System.out.println("text set?");
    }
    
    public void submitControllerNumber() {

  
    }
    
    @FXML
    void handleLoginScreenButtonPress(ActionEvent event) throws IOException, InterruptedException { 

          
    //TN: Retrieves controller number from text field:
    String controller = loginTextEntryField.getText();
    controllerNumber = controller;
    System.out.println("Text entered is " +controller);
        
    try
    {  
        Class.forName("com.mysql.jdbc.Driver");  
        Connection con=DriverManager.getConnection(  
        //"jdbc:mysql://138.91.230.143:3306/test_arduino","clicker","clicker");  
        //"jdbc:mysql://13.86.216.212:3306/remote_clicker","bseengineer@remoteclicker","SeaTeaYEngineer7!");
        //"jdbc:mysql://13.64.182.19:3306/remote-gear?sslmode=verify-full&sslrootcert=/cert/BaltimoreCyberTrustRoot.crt.pem","bseengineer","SeaTeaYEngineer7!");
          "jdbc:mysql://13.64.182.19:3306/remote-gear","bseengineer","SeaTeaYEngineer7!");      
        Statement stmt=con.createStatement();  
        ResultSet rs=stmt.executeQuery("SELECT controller FROM `clickers` WHERE controller = " +controller);  
        
        if (rs.next() == true) {
             validController = true;
             System.out.println("Valid controller number " +controller);
        } else {
             System.out.println("Invalid controller number " +controller);
        }
        con.close();
      }

    catch(Exception e)
    {
        System.out.println(e);
    }
    
        if (validController) {
           try {
                        loginErrorTextField.setText("Loading Controller "+controller);

                        System.out.println("Main screen load sequence");  
                        Parent loader = FXMLLoader.load(getClass().getResource
                            ("DesktopUtility.fxml"));
                        Scene partsScreen = new Scene(loader);
                        Stage window = (Stage) 
                                ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(partsScreen);
                        
                        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent t) {
                            Platform.exit();
                            System.exit(0);
                            }
                        });
                        
                        window.show(); 
                        
            }
            catch (IOException e) {
                e.printStackTrace();
            }
			
        } else {

            loginErrorTextField.setText("Invalid Controller Number");

            return;
			
    }  
    }
}
  

 


