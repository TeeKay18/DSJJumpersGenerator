package dsjgenerator;

import javafx.scene.control.Alert;

/**
 *
 * @author Tomasz Karciarz
 */
public class AlertCreator 
{

    public void setAlert(Alert.AlertType alertType, String title, String header, 
            String content) 
    {
       Alert alert = new Alert(alertType);
       if(!"".equals(title)) alert.setTitle(title);
       if(!"".equals(header))alert.setHeaderText(header);
       if(!"".equals(content)) alert.setContentText(content);
       alert.show();
    }
    
}
