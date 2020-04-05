package dsjgenerator;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Tomasz Karciarz
 */
public class ScriptWriter implements Writer 
{
    private String script_content; 
    private Settings settings;
    private Constants constants;
    private TextField[] textfields;
    private final ColorPicker[] colorpickers;
    private ChoiceBox[] choiceboxes;
    private final CheckBox[] checkboxes;
    
    public ScriptWriter(Settings settings, Constants constants)
    {
        this.settings = settings;
        this.constants = constants;
        this.textfields = settings.get_textfields();
        this.colorpickers = settings.get_colorpickers();
        this.checkboxes = settings.get_checkboxes();
        this.choiceboxes = settings.get_choiceboxes();
    }
    
    public void write(String script_line)
    {
        script_content += script_line;
    }

    @Override
    public void write_newline(String script_line) 
    {
        write(script_line);
        script_content += "\n";
    }
    
    private String set_cursor_quickness(String mode) 
    {
        if(mode.equals("Superszybki"))
        {
            return ", 0";
        }
        return "";
    }
    
    private void decision_jumpers_counter(boolean selected) 
    {
       if (selected)
       {
           write_newline("counter := 1");
       }
    }
    
    private void decision_run_script(String mode, File file) 
    {
        if (mode.equals("run"))
        {
            try 
            {
                Desktop.getDesktop().open(file);
            } 
            catch (IOException e) 
            {
                AlertCreator creator = new AlertCreator();
                creator.setAlert(Alert.AlertType.ERROR, 
                        "Nie udalo sie uruchomic skryptu",
                        "Tresc bledu:", e.getMessage());
            }
        }
    }
    
    private void decision_suit_mode(String mode) 
    {
        //mode of creating suits
        String t = (String) settings.get_choiceboxes()[1].getValue();
        if(t.equals("Własny"))
        {
            String[] swm = constants.get_suit_width_multipliers();
            String[] shm = constants.get_suit_height_multipliers();
            int c = 0; //licznik counter
            while (c < 11)
            {
                set_custom_part(swm[c/4], shm[c%4], mode, colorpickers[c]);
                c++;
            }
        }
        else if(t.equals("Losowy"))
        {
            write_newline("MouseMove, 0.335*A_ScreenWidth, 0.483*A_ScreenHeight" 
                          + mode);
            write_newline("MouseClick, left");
        }
    }
    
    private void decision_time_counter(boolean selected) 
    {
       if(selected)
       {
           write_newline("StartTime := A_TickCount");
       }
    }
    
    private void decision_time_counter_end(boolean selected) 
    {
       if(selected)
       {
           write_newline("ElapsedTime := A_TickCount - StartTime");
           write_newline("ElapsedTimeSeconds := ElapsedTime/1000");
           write_newline("OneJumperTime := ElapsedTimeSeconds/" 
                   + textfields[4].getText());
           write_newline("MsgBox, "
           + "It took %ElapsedTimeSeconds% seconds to create jumpers. "
           + "Average time of creating one jumper: %OneJumperTime% seconds.");
       }
    }
    
    private void decision_write_country() 
    {
        if(!"".equals(textfields[3].getText())) 
        {
           write_newline("MouseMove, 0.174*A_ScreenWidth, "
                         + "0.221*A_ScreenHeight, 0");
           write_newline("MouseClick, left");
           write_newline("Send, " + textfields[3].getText());
        }
    }
    
    private void decision_write_name(boolean selected) 
    {
        if(!"".equals(textfields[2].getText()))
        {
            write_newline("MouseMove, 0.263*A_ScreenWidth, "
                    + "0.1809*A_ScreenHeight, 0");
            write_newline("MouseClick, left");
            //piszemy nazwe gracza z kodem
            write("Send, " + textfields[2].getText());
            if (selected)
            {
                write_newline(" %counter%");
                write_newline("len := StrLen(counter)+1");
                write_newline("Sleep, 50");
                write_newline("Loop, %len%");
                write_newline("{");
                write_newline("  Send, `b");
                write_newline("}");
                write_newline("counter := counter+1");
            }
            else
                write_newline("");
        }
    }
    
    private void clearhex() 
    {
         write_newline("MouseMove, 0.362*A_ScreenWidth, "
                       + "0.661*A_ScreenHeight, 0"); //hex
         write_newline("MouseClick, left");
         write_newline("Loop, 6");
         write_newline("{");
         write_newline("Send, `b");
         write_newline("}");
    }
     
    private void set_custom_part(String width, String height, 
                                 String quickness, ColorPicker colorpicker)
    {
         write_newline("MouseMove, " + width + "*A_ScreenWidth, " 
                          + height + "*A_ScreenHeight" + quickness);
         write_newline("MouseClick, left");
         clearhex();
         write_newline("Send, " + 
                       colorpicker.getValue().toString().substring(2, 8));
    }
    
    private void instance_change() 
    {
        write_newline("#SingleInstance force");
    }
    
    private void set_run_dsj() 
    {
        write("Run, ");
        write_newline(textfields[1].getText()); //uruchamiamy gre DSJ4
        write_newline("Process, Exist, DSJ4"); //sprawdzamy czy sie uruchomila
        write_newline("while ErrorLevel");
        write_newline("{");
        write_newline("   Sleep, 200");
        write_newline("}");
        write_newline("Sleep, 3000"); 
    }
    
    private void set_no_players() 
    {
        write("Loop, "); //i tworzymy okreslona w textfield5 ilosc graczy
        write_newline(textfields[4].getText());
    }
    
    private void go_to_new_player(String mode) 
    {
      write_newline("MouseMove, 0.573*A_ScreenWidth, 0.949*A_ScreenHeight" 
                    + mode);
      write_newline("MouseClick, left");
    }
    
    private void end_create_player(String mode) 
    {
        write_newline("MouseMove, 0.142*A_ScreenWidth, 0.949*A_ScreenHeight" 
                      + mode);
        write_newline("MouseClick, left");
    }
    
    @Override
    public String get_string() 
    {
      try
        {
            File file = new File(textfields[0].getText());
            
            if(file.createNewFile() || file.exists()) file.setWritable(true);

            try (FileWriter writer = new FileWriter(file)) 
            {
            
                String q = set_cursor_quickness(
                        (String) choiceboxes[0].getValue()
                );
                instance_change();
                set_run_dsj();
                decision_jumpers_counter(checkboxes[1].isSelected());
                decision_time_counter(checkboxes[0].isSelected());
                set_no_players();
                write_newline("{");
                go_to_new_player(q);
                decision_write_name(checkboxes[1].isSelected());
                decision_write_country();
                decision_suit_mode(q);
                end_create_player(q);
                write_newline("}");
                decision_time_counter_end(checkboxes[0].isSelected());
                write_newline("Esc::ExitApp");

                writer.write(script_content);
                writer.close();

                decision_run_script(q, file);
                AlertCreator creator = new AlertCreator();
                creator.setAlert(Alert.AlertType.INFORMATION, 
                        "Plik utworzony pomyslnie", 
                        "Plik utworzony pomyslnie", 
                        "Przerwanie skryptu: przycisk Esc. Nie ruszac myszka"
                                + " write trakcie wykonywania skryptu! "
                                + "Pamietaj zeby miec"
                                + " zainstalowany program Autohotkey ");
            }
        }
        catch (IOException e)
        {
            AlertCreator creator = new AlertCreator();
            creator.setAlert(Alert.AlertType.ERROR, 
                            "Blad przy tworzeniu pliku", 
                            "Tresc bledu:",
                            e.getMessage());
        }
        return script_content;
    }

}
