package dsjgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Tomasz Karciarz
 */
public class SettingsWriter implements Writer 
{
    private String settings_content;
    
    public SettingsWriter()
    {
        this.settings_content = "";
    }

    @Override
    public void write_newline(String setting) 
    {
        settings_content += setting;
        settings_content += "\n";
    }

    @Override
    public String get_string() 
    {
        return settings_content;
    }
    
    public void save_settings(Settings settings)
    {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        
        try
        {
            File file = new File(s + "\\settings.txt");
            if(file.createNewFile() || file.exists()) file.setWritable(true);
            
            try (FileWriter writer = new FileWriter(file)) 
            {
                System.out.println(Arrays.toString(settings.get_textfields()));
                for (TextField field : settings.get_textfields())
                    write_newline(field.getText());
                for (ColorPicker picker : settings.get_colorpickers())
                    write_newline(picker.getValue().toString().substring(2,8));
                for (ChoiceBox box : settings.get_choiceboxes())
                    write_newline((String) box.getValue());
                writer.write(settings_content);
                writer.close();
            }
            catch (Exception e)
            {
                AlertCreator creator = new AlertCreator();
                creator.setAlert(Alert.AlertType.ERROR, 
                                "Blad przy wpisywaniu do pliku", 
                                "Tresc bledu:", e.getMessage());
            }
        }
        catch (IOException e)
        {
            AlertCreator creator = new AlertCreator();
            creator.setAlert(Alert.AlertType.ERROR, 
                    "Blad w tworzeniu pliku", 
                    "Tresc bledu:", e.getMessage());
        }
        
    }
    
}
