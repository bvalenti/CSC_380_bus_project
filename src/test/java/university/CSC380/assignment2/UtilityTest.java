/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;

/**
 *
 * @author bill
 */
public class UtilityTest extends TestCase {
    
    //test openMTAApiConnection
    public void testOpenMTAApiConnection(){
        try {
            HttpURLConnection conn = Utility.openMTAApiConnection();
            assertTrue(conn != null);
        } catch (IOException ex) {
            assertTrue(false);
        }
    }
    
    //test openGooleApiConnection
    public void testOpenGoogleApiConnection(){
        try {
            HttpURLConnection conn = 
                    Utility.openGoogleApiConnection("Malcom X Bl, 146 St");
            assertTrue(conn != null);
        } catch (IOException ex) {
            assertTrue(false);
        }
    }
    
    //test getFile
    public void testGetFile(){
        try {
            HttpURLConnection conn = mock(HttpURLConnection.class);
            InputStream inps = mock(InputStream.class);
            when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
            when(conn.getInputStream()).thenReturn(inps);
            when(inps.read()).thenReturn(-1);
            Utility.getFile(conn, "/home/bill/SchoolWork/csc380/CSC_380_bus_project", "testfile.txt");
            assertTrue(true);
        } catch (IOException ex) {
            assertTrue(false);
        }
    }
    
    // test startup tasks
    public void testStartupTasks() throws InterruptedException, IOException{
        Utility.startupTasks();
        int first = Utility.updateModelExecuteCount;
        System.out.println("Assigned count to first");
        Thread.sleep(72000);
        int last = Utility.updateModelExecuteCount;
        System.out.println("Assigned count to last");
        assertTrue(first != last);
    }
}
