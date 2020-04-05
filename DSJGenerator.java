package dsjgenerator;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tomasz Karciarz
 */
public class DSJGenerator extends Application {
    
Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(dsjgenerator.DSJGeneratorFXMLController.class.getResource("DSJGeneratorFXML.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        Constants constants = new Constants();
        stage.setTitle(constants.get_program_name() + constants.get_version());
        stage.show();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
