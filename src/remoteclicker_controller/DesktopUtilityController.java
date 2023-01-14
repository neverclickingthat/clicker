/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteclicker_controller;



import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import javafx.scene.*;
import javafx.scene.shape.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author Mothy
 */
public class DesktopUtilityController extends Thread implements Runnable {

    //TN: Login instruction and error message display field:
    @FXML
    private Label controllerNumberLabel;
    
    @FXML
    private TextField testField;
    
    //TN: Login screen button object:
    @FXML
    private Button consoleButton;
    
    @FXML
    private Polygon leftArrow;
    
    @FXML
    private Polygon rightArrow;
    
    @FXML
    private Circle connectedLED;
    
    boolean iterater = true;
    String red = "#FF0000";
    String green = "#00FF00";
    String blue = "#0000FF";
    String black = "#000000";
    String gray = "#696969";
    String white = "#FFFFFF";
    
    int leftButton;
    int rightButton;
    private final int timeToRun = 250;
    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Timestamp updateTime;
    Timestamp currentTime;
    Timestamp controllerTimeNow;
    java.sql.Timestamp controllerTimePrevious = java.sql.Timestamp.valueOf("2021-02-19 00:27:08.0");
    
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  

    public void initialize() throws InterruptedException {
    controllerNumberLabel.setText("Connection to Controller # "+LoginScreenController.controllerNumber);
    System.out.println("Connected to Controller # "+LoginScreenController.controllerNumber);
    controllerNumberLabel.setTextFill(Color.web(black, 0.8));
    leftArrow.setFill(Color.web(gray, 0.5));
    rightArrow.setFill(Color.web(gray, 0.5));
    connectedLED.setFill(Color.web(green, 1));
    System.out.println ("Thread " + Thread.currentThread().getId() + " is running");
    
    System.out.println("Summoning getConnection()");
    con = ConnectionManager.getConnection();
    System.out.println("Post getConnection() activity:");
    
    start();
    }
    
    @FXML
            
    void handleConsoleButtonPress(ActionEvent event) throws IOException, InterruptedException { 
         }
    
    public void connectCheck () throws InterruptedException {
        
        long timeNowMath = currentTime.getTime();
        long timePreviousMath = updateTime.getTime();
        long diff = timeNowMath - timePreviousMath;
        
        System.out.println("timeNowMath: " + timeNowMath + " timePreviousMath: " + timePreviousMath + " diff: " + diff);

        if (diff > 60000) {
            connectedLED.setFill(Color.web(red, 1));
            controllerNumberLabel.setText("Connection to Controller # " + LoginScreenController.controllerNumber + " OFFLINE");
        } else {
            connectedLED.setFill(Color.web(green, 1));
            controllerNumberLabel.setText("Connection to Controller # " + LoginScreenController.controllerNumber + " ONLINE");
        }
        System.out.println ("connectCheck Thread " + Thread.currentThread().getId() + " is running");
    }    
    

    
    @Override
    public void run() {
        System.out.println("Starting run()");
    while (iterater == true) {
      
        System.out.println ("Thread " + Thread.currentThread().getId() + " is running"); 
        System.out.println("Sleeping for " + timeToRun + " milliseconds");
        try {
            sleep(timeToRun);
        } catch (InterruptedException ex) {
            Logger.getLogger(DesktopUtilityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        leftArrow.setFill(Color.web(gray, 0.5));
        System.out.println ("Thread " + Thread.currentThread().getId() + " is running"); 
        System.out.println("Sleeping for " + timeToRun + " milliseconds");
        try {
            sleep(timeToRun);
        } catch (InterruptedException ex) {
            Logger.getLogger(DesktopUtilityController.class.getName()).log(Level.SEVERE, null, ex);
        }System.out.println("Turning left arrow gray");
        rightArrow.setFill(Color.web(gray, 0.5));
        System.out.println("Turning right arrow gray");
            
   try {
        Statement stmt=con.createStatement();  
        ResultSet controllerButtons=stmt.executeQuery("SELECT * FROM `clickers` WHERE controller = " +LoginScreenController.controllerNumber);
        System.out.println ("ResultSet controllerButtons created");
        
        //Cycle through the buttons DB results and assign variables
        while (controllerButtons.next()) {
                System.out.println ("Assigning button variables");
                leftButton = controllerButtons.getInt("leftclick");
                rightButton = controllerButtons.getInt("rightclick");
            }
        
        ResultSet controllerTime=stmt.executeQuery("SELECT UPDATE_TIME, CURRENT_TIMESTAMP FROM information_schema.tables WHERE TABLE_SCHEMA = 'remote-gear' AND TABLE_NAME = 'clickers'");
            System.out.println ("getting UPDATE_TIME");
        while (controllerTime.next()) {
            updateTime = controllerTime.getTimestamp("UPDATE_TIME");
            currentTime = controllerTime.getTimestamp("CURRENT_TIMESTAMP");
        }

        System.out.println ("Left button value: " + leftButton + ", Right button value: " + rightButton + ", Current DB Time: " + currentTime + " Last DB Access Time: " + updateTime);
        
        connectCheck ();
        
        if (leftButton == 1) {
            System.out.println("Triggering left button press");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_LEFT);
            System.out.println("Turning left arrow red");
            leftArrow.setFill(Color.web(red, 1));
            Thread.sleep(200);
            System.out.println("Writing a 0 to the left button DB value");
            stmt.executeUpdate("UPDATE `remote-gear`.clickers SET leftclick = 0 WHERE controller = " +LoginScreenController.controllerNumber);
        } else {
           //con.close();
        }
        
        if (rightButton == 1) {
            System.out.println("Triggering right button press");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_RIGHT);
            System.out.println("Turning right arrow red");
            rightArrow.setFill(Color.web(red, 1));
            Thread.sleep(200);
            System.out.println("Writing a 0 to the right button DB value");
            stmt.executeUpdate("UPDATE `remote-gear`.clickers SET rightclick = 0 WHERE controller = " +LoginScreenController.controllerNumber);
        } else {
           //con.close();
        }
        
        
        
    } catch(Exception e) {
      System.out.println(e);
    }
    
        
        
      

        

        
            //Loop that checks for remote status via DB then applies keypress simulators
		//Log into DB
		
		//White iterate == 1
			//Check connected value
				//If connected turn connected LED green
				//If connected print "Controller connected to DB at (date time)" once in console and log
					//Local "connectedLogged" value to 1
				//If not connected turn connected LED red
				//If not connected print "Controller disconnected from DB at (date time)" once in console and log
					//Local  "connectedLogged" value to 0
			//Check Right button pressed DB value
				//If pressed 
					//Turn right arrow green for 1/2 second
					//Print "Right button pressed at(date time)" in console and log
					//Send right button press to local machine
					//Write 0 value to right button press in DB
				//If not pressed
					//Ensure right arrow is grey
			//Check Left button pressed DB value
				//If pressed 
					//Turn Left arrow green for 1/2 second
					//Print "Left button pressed at(date time)" in console and log
					//Send left button press to local machine
					//Write 0 value to Left button press in DB
				//If not pressed
					//Ensure Left arrow is grey
					
	//Function that writes to a log and console (string)
		//Add string to log
		//console.log(string);
    

    
    
    }}}
