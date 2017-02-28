/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.time.LocalTime;

/**
 *
 * @author bill
 */
public class UtilityTest extends TestCase {
    
    
    public void testGPSFeedPollsEveryMinute() throws InterruptedException, IOException{
        
        Utility.startupTasks();
        
        Thread.sleep(30000);
        
        Path p = Paths.get("vehicle-monitoring.json");
        BasicFileAttributes view = Files.getFileAttributeView(p, 
                BasicFileAttributeView.class).readAttributes();
        FileTime start = view.lastModifiedTime();
        
        Thread.sleep(60000);
        
        FileTime end = view.lastModifiedTime();
        
        assertTrue(!start.equals(end));
    }
    
}
