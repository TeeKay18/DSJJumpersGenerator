package dsjgenerator;

/**
 *
 * @author Tomasz Karciarz
 */
public class SettingsWriter implements Writer 
{
    private String settings_content;

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
    
}
