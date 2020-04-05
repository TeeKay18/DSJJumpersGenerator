package dsjgenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author Tomasz Karciarz
 */
public class SettingsSetter
{
    private final Settings settings;
    private final Constants constants;
    
    public SettingsSetter(Settings settings)
    {
        this.settings = settings;
        this.constants = new Constants();
    }
    
    public void read_settings()
    {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String filePath = s + "\\settings.txt";
        BufferedReader fileReader = null;
        try 
        {
            //how many newlines should be in text
            int hml = settings.length()-settings.get_checkboxes().length; 
            fileReader = new BufferedReader(new FileReader(filePath));
            String line;
            int h_m = 0; //how many newlines read so far
            String[] set_strings = new String[hml];
            while ((line = fileReader.readLine()) != null && h_m < hml)
            {
                set_strings[h_m] = line;
                h_m++;
            }
            if (h_m == hml) set_values(set_strings);
            else set_default_values();
        } 
        catch (IOException ex) 
        {
            set_default_values();
        }    
        finally 
        {
            if (fileReader != null) 
            {
                try 
                {
                    fileReader.close();
                } 
                catch (IOException ex) 
                {
                    set_default_values();
                }
            }
        }
    }

    private void set_default_values() 
    {
        settings.set_textfields(null);
        settings.set_default_colorpickers(constants.get_default_colors());
        String[] new_choiceboxes = new String[]
                    {constants.get_choicebox1_value(),
                    constants.get_choicebox2_value()};
        System.out.println(Arrays.toString(new_choiceboxes));
        settings.set_default_choiceboxes(new_choiceboxes);
    }

    private void set_values(String[] set_strings) 
    {
        settings.set_textfields(set_strings);
        settings.set_colorpickers(set_strings);
        settings.set_choiceboxes(set_strings);
    }
    
}
