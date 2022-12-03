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
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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
            textfield2, textfield4, textfield5, textfield6;
//    @FXML
//    private ColorPicker colorpicker1, colorpicker2, colorpicker3, colorpicker4,
//            colorpicker5, colorpicker6, colorpicker7, colorpicker8, 
//            colorpicker9, colorpicker10, colorpicker11;
    @FXML
    private ChoiceBox<String> choicebox1, choicebox2;
    //pomiar czasu tworzenia skoczkow, licznik skoczkow
    @FXML
    private CheckBox checkbox1, checkbox2;
   
    String script = "";
    String errors = "";
    String settings = "";
    String q = "";
    int errors_no;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        choicebox1.setValue("Szybki");
        choicebox1.setItems(FXCollections.observableArrayList("Szybki", 
                "Superszybki"));
        choicebox2.setValue("Brak");
        choicebox2.setItems(FXCollections.observableArrayList("Brak", "Losowy",
                "Własny"));
        read_settings();
    }   
   
    private void wn(String string) //write with newline
    {
        script += string;
        script += "\n";
    }
    
    private void w(String string) //write
    {
        script += string;
    }
    
    private void error(String error_msg)
    {
        errors += error_msg;
        errors_no++;
    }
    
    @FXML
    protected void save_script(ActionEvent e) throws IOException 
    {
        script("save");
    }
    
    @FXML
    protected void run_script(ActionEvent e) throws IOException 
    {
        script("run");
    }
    
    
    @FXML
    public void script(String mode) throws IOException
    {
       reset_values();
      
       check_textfield0();
       check_textfield1();
       check_textfield23();
       check_textfield4();
       check_textfield5();
       
       if(errors_no == 0)
       {
        try
        {
            File file = new File(textfield0.getText());
            
            if(file.createNewFile() || file.exists()) file.setWritable(true);

            try (FileWriter writer = new FileWriter(file)) 
            {
                q = set_cursor_quickness(choicebox1.getValue());
                instance_change();
                run_dsj();
                decision_jumpers_counter(checkbox2.isSelected());
                decision_time_counter(checkbox1.isSelected());
                set_no_players();
                wn("{");
                go_to_new_player();
                decision_write_name(checkbox2.isSelected());
                decision_write_country();
                decision_suit_mode();
                end_create_player();
                wn("}");
                decision_time_counter_end(checkbox1.isSelected());
                wn("Esc::ExitApp");

                writer.write(script);
                writer.close();
                
                decision_run_script(mode, file);
                setAlert(AlertType.INFORMATION, "Plik utworzony pomyslnie", 
                        "Plik utworzony pomyslnie", 
                        "Przerwanie skryptu: przycisk Esc. Nie ruszac myszka"
                                + " w trakcie wykonywania skryptu! "
                                + "Pamietaj zeby miec"
                                + " zainstalowany program Autohotkey ");
            }
        }
        catch (IOException e)
        {
            setAlert(AlertType.ERROR, "Blad przy tworzeniu pliku", "Tresc bledu:",
            e.getMessage());
        }
        
       }
       else
       {
           setAlert(AlertType.ERROR, "Wykryto " + errors_no + " bledow",
           "Wykryto " + errors_no + " bledow", errors);
       }
    }

    private void check_textfield0() 
    {
        String a = textfield0.getText();
        if(a.length() < 4) 
            error("Nie okreslono sciezki do zapisu skryptu. \n");
        else if(!".ahk".equals(a.substring(a.length()-4)))
            error("Zle rozszerzenie pliku! Poprawne to .ahk \n");
    }

    private void check_textfield1() 
    {
        String a = textfield1.getText();
        if(a.length() < 4) 
            error("Nie okreslono sciezki do pliku DSJ4.exe. \n");
        else if(!".exe".equals(a.substring(a.length()-4)))
            error("Zle rozszerzenie pliku! Poprawne to .exe \n");
    }

    private void check_textfield23() 
    {
        String a = textfield2.getText();
        int max_len = 24;
        if(checkbox2.isSelected())
        {
           a += textfield5.getText();
           max_len = 23;
        }
        if (a.length() > max_len)
           error("Za dluga nazwa - nie zmiesci sie w grze! \n" + a);
    }

    private void check_textfield4() 
    {
       String a = textfield4.getText();
       for (int b = 0; b < 10; b++)
       {
          if (a.contains(Integer.toString(b)))
          {
              error("Kraj nie moze miec cyfry, wpisana zostala cyfra: " + 
                     Integer.toString(b) + "\n");
          }
       }
       if (a.length() > 3)
       {
           error("Skrot kraju jest maksymalnie 3-literowy. \n");
       }
    }

    private void check_textfield5() 
    {
       String a = textfield5.getText();
       if (a.length() == 0)
           error("Nie podano ilosci skoczkow! \n");
       else
       {
           try
           {
                int b = Integer.parseInt(a);
                if (b < 1)
                    error("Musi byc co najmniej jeden skoczek. \n");
           }
           catch (NumberFormatException e)
           {
               error("Podaj liczbe skoczkow co najmniej 1 lub wiecej, "
                       + "nie kombinuj :) \n");
           }
           
       }
    }
    
    public void info()
    {
        setAlert(AlertType.INFORMATION, "Informacje o programie", 
         "DSJ4 Jumpers Generator v.1.0.3", "Autor programu: Tomasz Karciarz "
        + "(e-mail: tommkar321@wp.pl) \n\n"
        + "Wszystkie potrzebne informacje na temat programu"
                 + " sa dostepne w pliku README.txt.");
    }

    private void setAlert(AlertType alertType, String title, String header, 
            String content) 
    {
       Alert alert = new Alert(alertType);
       if(!"".equals(title)) alert.setTitle(title);
       if(!"".equals(header))alert.setHeaderText(header);
       if(!"".equals(content)) alert.setContentText(content);
       alert.show();
    }

    private void reset_values() 
    {
       script = "";
       errors_no = 0;
       errors = "";
       settings = "";
       q = "";
    }
    
    private void wnew(String string)
    {
        settings += string;
        settings += "\n";
    }
    

    public void save_settings() 
    {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        
        try
        {
            File file = new File(s + "\\settings.txt");
            if(file.createNewFile() || file.exists()) file.setWritable(true);
            
            try (FileWriter writer = new FileWriter(file)) 
            {
                wnew(textfield0.getText());
                wnew(textfield1.getText());
                wnew(textfield2.getText());
                wnew(textfield4.getText());
                wnew(textfield5.getText());
                wnew(textfield6.getText());
                wnew(choicebox1.getValue());
                wnew(choicebox2.getValue());
                writer.write(settings);
                writer.close();
            }
            catch (Exception e)
            {
                setAlert(AlertType.ERROR, "Blad przy wpisywaniu do pliku", 
                    "Tresc bledu:", e.getMessage());
            }
        }
        catch (IOException e)
        {
            setAlert(AlertType.ERROR, "Blad w tworzeniu pliku", 
                    "Tresc bledu:", e.getMessage());
        }
        
    }
    
    private void read_settings()
    {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String filePath = s + "\\settings.txt";
        BufferedReader fileReader = null;
        try 
        {
            int hml = 8; //how many newlines should be in text
            fileReader = new BufferedReader(new FileReader(filePath));
            String line;
            int h_m = 0; //how many newlines read so far
            String[] array = new String[hml];
            while ((line = fileReader.readLine()) != null && h_m < hml)
            {
                array[h_m] = line;
                h_m++;
            }
            if (h_m == hml) set_values(array);
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

    private void set_values(String[] array) 
    {
        textfield0.setText(array[0]);
        textfield1.setText(array[1]);
        textfield2.setText(array[2]);
        textfield4.setText(array[3]);
        textfield5.setText(array[4]);
        textfield6.setText(array[5]);
        choicebox1.setValue(array[6]);
        choicebox2.setValue(array[7]);
    }
    
    private void set_default_values()
    {
        textfield0.setText("");
        textfield1.setText("");
        textfield2.setText("");
        textfield4.setText("");
        textfield5.setText("");
        choicebox1.setValue("");
        choicebox2.setValue("");
    }
 
    private void decision_write_country() 
    {
        if(!"".equals(textfield4.getText())) 
        {
           click_position("0.174", "0.221");
           wn("Send, " + textfield4.getText());
        }
    }

    private void decision_write_name(boolean selected) 
    {
        if(!"".equals(textfield2.getText()))
        {
            click_position("0.263", "0.1809");
            //piszemy nazwe gracza z kodem
            w("Send, " + textfield2.getText());
            if (selected)
            {
                wn(" %counter%");
                wn("len := StrLen(counter)+1");
                wn("Sleep, 50");
                wn("Loop, %len%");
                wn("{");
                wn("  Send, `b");
                wn("}");
                wn("counter := counter+1");
            }
            else
                wn("");
        }
    }
    
    private void mouse_click_left() {
        wn("MouseClick, left");
    }
    
    private void set_cursor_position(String str_width, String str_height) {
        wn("MouseMove, " + str_width + "*A_ScreenWidth, " + str_height + "*A_ScreenHeight" + q);
    }
    
    private void click_position(String str_width, String str_height) {
        set_cursor_position(str_width, str_height);
        mouse_click_left();
    }

    private void decision_suit_mode() 
    {
        String t = choicebox2.getValue(); //mode of creating suits
        if(t.equals("Własny"))
        {
            click_position("0.09375", "0.8518"); //opcja Wczytaj...
            click_position("0.505", "0.6296"); //wklep tekst
            wn("Send, " + textfield6.getText()); // wklep tekst
            click_position("0.35416", "0.6759"); // opcja Ok
        }
        else if(t.equals("Losowy"))
        {
            click_position("0.333", "0.8518");
        }
    }

    private String set_cursor_quickness(String mode) 
    {
        if(mode.equals("Superszybki"))
        {
            return ", 0";
        }
        return "";
    }

    private void go_to_new_player() 
    {
      click_position("0.573", "0.949");
    }

    private void end_create_player() 
    {
      click_position("0.142", "0.949");
    }

    private void run_dsj() 
    {
        w("Run, ");
        wn(textfield1.getText()); //uruchamiamy gre DSJ4
        wn("Process, Exist, DSJ4"); //sprawdzamy czy sie uruchomila
        wn("while ErrorLevel");
        wn("{");
        wn("   Sleep, 200");
        wn("}");
        wn("Sleep, 3000"); 
    }

    private void set_no_players() 
    {
        w("Loop, "); //i tworzymy okreslona w textfield5 ilosc graczy
        wn(textfield5.getText());
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
                setAlert(AlertType.ERROR, "Nie udalo sie uruchomic skryptu",
                        "Tresc bledu:", e.getMessage());
            }
        }
    }

    private void decision_time_counter(boolean selected) 
    {
       if(selected)
       {
           wn("StartTime := A_TickCount");
       }
    }

    private void decision_time_counter_end(boolean selected) 
    {
       if(selected)
       {
           wn("ElapsedTime := A_TickCount - StartTime");
           wn("ElapsedTimeSeconds := ElapsedTime/1000");
           wn("OneJumperTime := ElapsedTimeSeconds/" + textfield5.getText());
           wn("MsgBox, "
           + "It took %ElapsedTimeSeconds% seconds to create jumpers. "
           + "Average time of creating one jumper: %OneJumperTime% seconds.");
       }
    }

    private void instance_change() 
    {
        wn("#SingleInstance force");
    }

    private void decision_jumpers_counter(boolean selected) 
    {
       if (selected)
       {
           wn("counter := 1");
       }
    }
}