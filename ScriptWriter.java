package dsjgenerator;

/**
 *
 * @author Tomasz Karciarz
 */
public class ScriptWriter implements Writer 
{
    private String script_content; 
    
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

    @Override
    public String get_string() 
    {
        return script_content;
    }
    
}
