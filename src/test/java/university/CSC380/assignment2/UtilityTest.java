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
import javax.net.ssl.HttpsURLConnection;
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
    public void testOpenMTAApiConnection() throws InterruptedException{
        System.out.println("Testing openMTAApiConnection");
        try {
            HttpURLConnection conn = Utility.openMTAApiConnection();
            if (conn!= null){
                System.out.println("Finished testing openMTAApiConnection"
                        + " - Pass");
            } else {
                System.out.println("Finished testing openMTAApiConnection "
                        + "- Fail");
            }
            assertTrue(conn != null);
        } catch (IOException ex) {
            System.out.println("Finished testing openMTAApiConnection "
                        + "- Fail");
            assertTrue(false);
        }
    }
    
    //test openGooleApiConnection
    public void testOpenGoogleApiConnection(){
        System.out.println("Testing openGoogleApiConnection");
        try {
            HttpsURLConnection conn = 
                    Utility.openGoogleApiConnection("Malcom X Bl, 146 St");
            if (conn!= null){
                System.out.println("Finished testing openGoogleApiConnection"
                        + " - Pass");
            } else {
                System.out.println("Finished testing openGoogleApiConnection "
                        + "- Fail");
            }
            assertTrue(conn != null);
        } catch (IOException ex) {
            System.out.println("Finished testing openGoogleApiConnection - "
                    + "Fail");
            assertTrue(false);
        }
    }
    
    //test getFile
    public void testGetFile(){
        System.out.println("Testing getFile");
        try {
            HttpURLConnection conn = mock(HttpURLConnection.class);
            File f = new File("testfile2.txt");
            FileInputStream finps = new FileInputStream(f);
            when(conn.getResponseCode()).thenReturn(200);
            when(conn.getInputStream()).thenReturn(finps);
            Utility.getFile(conn, 
                    "/home/bill/SchoolWork/csc380/CSC_380_bus_project", 
                    "testfile.txt", false);
            System.out.println("Finished testing getFile - Pass");
            assertTrue(true);
        } catch (IOException ex) {
            System.out.println("Finished testing getFile - Fail");
            assertTrue(false);
        }
    }
    
    // test startup tasks
    public void testStartupTasks() throws InterruptedException, IOException{
        System.out.println("Testing startupTasks");
        Utility.startupTasks();
        int first = Utility.updateModelExecuteCount;
        System.out.println("Assigned count to first");
        Thread.sleep(72000);
        int last = Utility.updateModelExecuteCount;
        System.out.println("Assigned count to last");
        Utility.stopApiPoller();
        if (first != last){
            System.out.println("Finished testing startupTasks - Pass");
        } else {
            System.out.println("Finished testing startupTasks - Fail");
        }
        assertTrue(first != last);
    }
}
