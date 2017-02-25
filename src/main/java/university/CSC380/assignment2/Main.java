/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import javax.json.*;

/**
 *
 * @author bill
 */

// Google Geocoding API key : AIzaSyDVO746CwOhnxOo6KQOrEL1L6as-Ag_sKw

public class Main {
    
    public static void main(String[] args) throws FileNotFoundException, 
            IOException{
        
        Utility.startupTasks();
        
        HashMap hm = Utility.parseSchedule("Routes.txt");
        
        String fileName1 = Utility.getFile("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDVO746CwOhnxOo6KQOrEL1L6as-Ag_sKw&address=Malcom%20x%20bl,%20w%20146%20st", "/home/bill/SchoolWork/csc380/CSC_380_bus_project", "address-data.json");
        
        String fileName2 = Utility.getFile("http://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880", "/home/bill/SchoolWork/csc380/CSC_380_bus_project", "vehicle-monitoring.json");
        
        JsonObject j = Utility.parseJson(fileName1);
        
        System.out.println("Finished creating JObject");
        
    }
    
}
