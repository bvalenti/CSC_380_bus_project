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
    
    
    public void testStartupTasks() throws InterruptedException, IOException{
        
        Utility.startupTasks();
        
        int first = Utility.updateModelExecuteCount;
        
        Thread.sleep(60000);
        
        int last = Utility.updateModelExecuteCount;
        
        assertTrue(first != last);
    }
    
}
