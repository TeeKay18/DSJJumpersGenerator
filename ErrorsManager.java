package dsjgenerator;

/**
 *
 * @author Tomasz Karciarz
 */
public class ErrorsManager
{
    private int errors_no; 
    private String all_error_messages;
    
    public ErrorsManager()
    {
        this.errors_no = 0;
        this.all_error_messages = "";
    }
    
    public void error(String err_msg)
    {
        errors_no += 1;
        all_error_messages += err_msg;
    }
    
    
    
}
