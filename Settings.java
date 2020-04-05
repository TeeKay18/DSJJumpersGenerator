package dsjgenerator;

import java.util.Arrays;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 *
 * @author Tomasz Karciarz
 */
public class Settings 
{    
    private TextField[] textfields;
    private final ColorPicker[] colorpickers;
    private ChoiceBox[] choiceboxes;
    private final CheckBox[] checkboxes;
    
    public Settings(TextField[] textfields, ColorPicker[] colorpickers,
                    ChoiceBox[] choiceboxes, CheckBox[] checkboxes)
    {
        this.textfields = textfields;
        this.colorpickers = colorpickers;
        this.choiceboxes = choiceboxes;
        this.checkboxes = checkboxes;
    }
    
    public TextField[] get_textfields()
    {
        return textfields;
    }
    
    public ColorPicker[] get_colorpickers()
    {
        return colorpickers;
    }
    
    public ChoiceBox[] get_choiceboxes()
    {
        return choiceboxes;
    }
    
    public CheckBox[] get_checkboxes()
    {
        return checkboxes;
    }
    
    public void set_textfields(String[] set_lines)
    {
        if (set_lines == null)
        {
            for (TextField textfield : textfields) 
            {
                textfield.setText("");
            }
        }
        else
        {
            for (int i = 0; i < textfields.length; i++)
            {
                textfields[i].setText(set_lines[i]);
            }
        }
    }
    
    
    public void set_default_colorpickers(String[] set_lines)
    {
        for (int i = 0; i < colorpickers.length; i++)
        {
            colorpickers[i].setValue(Color.web("#" + set_lines[i]));
        }
    }
    
    public void set_colorpickers(String[] set_lines)
    {
        for (int i = 0; i < colorpickers.length; i++)
        {
            colorpickers[i].setValue(
                    Color.web("#" + set_lines[textfields.length+i])
            );
        }
    }
    
    public void set_default_choiceboxes(String[] set_lines)
    {
        for (int i = 0; i < choiceboxes.length; i++)
        {
            choiceboxes[i].setValue(
                    set_lines[i]
            );
        }
    }
    
    public void set_choiceboxes(String[] set_lines)
    {
        for (int i = 0; i < choiceboxes.length; i++)
        {
            choiceboxes[i].setValue(
                    set_lines[textfields.length + colorpickers.length+i]
            );
        }
    }
    
    public int length()
    {
        return textfields.length + colorpickers.length 
               + choiceboxes.length + checkboxes.length;
    }
    
}
