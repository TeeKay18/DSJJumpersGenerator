package dsjgenerator;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Tomasz Karciarz
 */
public class DSJGeneratorFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    //sciezka zapisu skryptu, sciezka DSJ4, nick, kod, kraj, ilosc skoczkow
    @FXML
    private TextField textfield0, textfield1, 
            textfield2, textfield4, textfield5;
    @FXML
    private ColorPicker colorpicker1, colorpicker2, colorpicker3, colorpicker4,
            colorpicker5, colorpicker6, colorpicker7, colorpicker8, 
            colorpicker9, colorpicker10, colorpicker11;
    @FXML
    private ChoiceBox<String> choicebox1, choicebox2;
    //pomiar czasu tworzenia skoczkow, licznik skoczkow
    @FXML
    private CheckBox checkbox1, checkbox2;
   
    private TextField[] textfields;
    private ColorPicker[] colorpickers;
    private ChoiceBox[] choiceboxes;
    private CheckBox[] checkboxes;
    private Constants constants;
    
    private Settings first_settings;
    private SettingsSetter first_settings_setter;
    private Validator validator;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        textfields = new TextField[]{textfield0, textfield1, 
        textfield2, textfield4, textfield5};
        
        colorpickers = new ColorPicker[]{colorpicker1, colorpicker2, 
            colorpicker3, colorpicker4, colorpicker5, colorpicker6, 
            colorpicker7, colorpicker8, colorpicker9, colorpicker10, 
            colorpicker11};
        
        choiceboxes = new ChoiceBox[]{choicebox1, choicebox2};
        checkboxes = new CheckBox[]{checkbox1, checkbox2};
        constants = new Constants();
       
        choicebox1.setValue("Szybki");
        choicebox1.setItems(FXCollections.observableArrayList("Szybki", 
                "Superszybki"));
        choicebox2.setValue("Brak");
        choicebox2.setItems(FXCollections.observableArrayList("Brak", "Losowy",
                "Własny"));
        
        first_settings = new Settings(textfields, colorpickers, 
                                      choiceboxes, checkboxes);
        first_settings_setter = new SettingsSetter(first_settings);
        first_settings_setter.read_settings();
    }   
    
    @FXML
    protected void save_script(ActionEvent e) throws IOException 
    {
        Settings new_settings = new Settings(textfields, colorpickers, 
                                             choiceboxes, checkboxes);
        validator = new Validator(new_settings);
        ErrorsManager err_manager = validator.validate();
        int err_no = err_manager.get_errors_no();
        String err_msg = err_manager.get_all_error_messages();
        if(err_no != 0)
        {
            AlertCreator creator = new AlertCreator();
            creator.setAlert(AlertType.ERROR, 
                            "Znaleziono " + err_no + " bledow", 
                             "Znaleziono " + err_no + " bledow", 
                             err_msg);
        }
        else
        {
            
        }
    }
    
    @FXML
    protected void run_script(ActionEvent e) throws IOException 
    {
        System.out.println("To be implemented 2");
    }

    public void save_settings() 
    {
        Settings new_settings = new Settings(textfields, colorpickers, 
                                             choiceboxes, checkboxes);
        SettingsWriter writer = new SettingsWriter();
        writer.save_settings(new_settings);
    }
    
    public void info()
    {
        System.out.println("To be implemented 4");
    }
   
}
