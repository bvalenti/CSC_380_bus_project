/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bill
 */
public class UpdateModel extends TimerTask{
    
    public void run(){
        System.out.println("Executing TimerTask UpdateModel");
        Utility.updateModelExecuteCount++;
        try {
            HttpURLConnection conn = Utility.openMTAApiConnection();
            Utility.getFile(conn, "/home/bill/SchoolWork/csc380/CSC_380_bus_project", "vehicle-monitoring.json", false);
        } catch (IOException ex) {
            System.out.println("IO Exception getting Vehicle Data.");
        }
    }
    
}
