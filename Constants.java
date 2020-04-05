package dsjgenerator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xxxx
 */
public class Constants 
{
    private final String[] default_colors;
    private final String version, program_name, c1_value, c2_value;
    private final String[] multipliers;
    private final String[] suit_width_multipliers;
    private final String[] suit_height_multipliers;
    public Constants()
    {
        this.version = " v.1.0.3";
        this.program_name = "DSJ4 Jumpers Generator";
        
        this.default_colors = new String[]{"e46801", "0011ae", "ffffff", 
                                           "a9a9aa", "cc016b", "00129e", 
                                           "cc016b", "00129e", "a9a9aa", 
                                                     "39005f", "fbda46"};
        
        this.multipliers = new String[]{"0.096", "0.174", "0.1809", 
                                        "0.214", "0.221", "0.263", 
                                        "0.332", "0.335", "0.483", 
                                        "0.522", "0.552", "0.573",
                                        "0.582", "0.612", "0.949"};
        this.suit_width_multipliers = new String[]{"0.096", "0.214", "0.332"};
        this.suit_height_multipliers = new String[]{"0.522", "0.552", 
                                                    "0.582", "0.612"};
        this.c1_value = "Szybki";
        this.c2_value = "Brak";
    }
    
    public String[] get_default_colors()
    {
        return default_colors;
    }
    
    public String get_version()
    {
        return version;
    }
    
    public String get_program_name()
    {
        return program_name;
    }
    
    public String[] get_multipliers()
    {
        return multipliers;
    }
    
    public String[] get_suit_width_multipliers()
    {
        return suit_width_multipliers;
    }
    
    public String[] get_suit_height_multipliers()
    {
        return suit_height_multipliers;
    }
    
    public String get_choicebox1_value()
    {
        return c1_value;
    }
    
    public String get_choicebox2_value()
    {   
        return c2_value;
    }
    
}
