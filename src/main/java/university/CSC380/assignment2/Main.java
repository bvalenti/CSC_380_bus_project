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

/**
 *
 * @author bill
 */
public class Main {
    
    public static void main(String[] args) throws FileNotFoundException, 
            IOException{
        HashMap hm = Utility.parseSchedule("Routes.txt");
        
        
        
        String fileName = Utility.getFile("http://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880", "/home/bill/Documents");
    }
    
}
