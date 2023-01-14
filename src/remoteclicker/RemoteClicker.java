package remoteclicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mothy
 */
public class RemoteClicker extends Application {
    
@Override
    public void start(Stage stage) throws Exception {
        
        System.out.println("Initiating RemoteClicker.java");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RemoteClicker.class.getResource
            ("/remoteclicker_controller/LoginScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}