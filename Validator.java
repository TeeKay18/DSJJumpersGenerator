package dsjgenerator;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Tomasz Karciarz
 */
public class Validator 
{
    private final TextField[] textfields;
    private final CheckBox[] checkboxes;
    private final ErrorsManager manager;

    public Validator(Settings settings)
    {
        this.textfields = settings.get_textfields();
        this.checkboxes = settings.get_checkboxes();
        this.manager = new ErrorsManager();
    }
            
    private void textfield0_valid() 
    {
        String a = textfields[0].getText();
        if(a.length() < 4) 
            manager.error("Nie okreslono sciezki do zapisu skryptu. \n");
        else if(!".ahk".equals(a.substring(a.length()-4)))
            manager.error("Zle rozszerzenie pliku! Poprawne to .ahk \n");
    }

    private void textfield1_valid() 
    {
        String a = textfields[1].getText();
        if(a.length() < 4) 
            manager.error("Nie okreslono sciezki do pliku DSJ4.exe. \n");
        else if(!".exe".equals(a.substring(a.length()-4)))
            manager.error("Zle rozszerzenie pliku! Poprawne to .exe \n");
    }

    private void textfield2_valid() 
    {
        String a = textfields[2].getText();
        int max_len = 24;
        if(checkboxes[2].isSelected())
        {
           a += textfields[4].getText();
           max_len = 23;
        }
        if (a.length() > max_len)
           manager.error("Za dluga nazwa - nie zmiesci sie w grze! \n" + a);
    }

    private void textfield3_valid() 
    {
       String a = textfields[3].getText();
       for (int b = 0; b < 10; b++)
       {
          if (a.contains(Integer.toString(b)))
          {
              manager.error("Kraj nie moze miec cyfry, wpisana zostala cyfra: " + 
                     Integer.toString(b) + "\n");
          }
       }
       if (a.length() > 3)
           manager.error("Skrot kraju jest maksymalnie 3-literowy. \n");
    }

    private void textfield4_valid() 
    {
       String a = textfields[4].getText();
       if (a.length() == 0)
           manager.error("Nie podano ilosci skoczkow! \n");
       else
       {
           try
           {
                int b = Integer.parseInt(a);
                if (b < 1)
                    manager.error("Musi byc co najmniej jeden skoczek. \n");
           }
           catch (NumberFormatException e)
           {
               manager.error("Podaj liczbe skoczkow co najmniej 1 lub wiecej, "
                       + "nie kombinuj :) \n");
           }
       }
    }
    
    public ErrorsManager validate()
    {
        textfield0_valid();
        textfield1_valid();
        textfield2_valid();
        textfield3_valid();
        textfield4_valid();
        
        return manager;
    }
    
}
