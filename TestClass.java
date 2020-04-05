/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsjgenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author xxxx
 */
public class TestClass 
{
    public static void main(String[] args)
    {
        try (Stream<Path> walk = Files.walk(Paths.get("C:\\Users\\xxxx\\Documents\\Deluxe Ski Jump 4\\Players"))) 
        {
        
        String a = "C:\\Users\\xxxx\\Documents\\Deluxe Ski Jump 4\\Players";
        
        List<String> files = walk.filter(Files::isRegularFile)
                        .map(x -> x.toString().substring(a.length()+1)).collect(Collectors.toList());
       
        List<String> skj_files = new ArrayList<>();
        
        files.stream().filter((file) -> (file.endsWith(".plr"))).forEachOrdered((file) -> {
            skj_files.add(0, file.substring(0, file.length()-4));
            });
        
        System.out.println(skj_files);
        
        Collections.sort(skj_files, String.CASE_INSENSITIVE_ORDER);
        
        System.out.println(skj_files);

	} 
        catch (IOException e) 
        {
		e.getMessage();
	}
    }
}
