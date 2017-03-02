/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author bill
 */
public final class Utility {
    
    private static Timer apiPoller;
    public static int updateModelExecuteCount;
    
    private Utility(){}
    
    // parses Schedule in txt file and returns hashmap of <id, Bus[2]>
    // Bus[0] = Headed Downtown, Bus[1] = Headed Uptown
    public static HashMap parseSchedule(String fn) throws FileNotFoundException{
        File f = new File(fn);
        ArrayList<Stop> stops = new ArrayList();
        HashMap<String, Bus[]> busses = new HashMap();
        Bus b[] = new Bus[2];
        b[0] = new Bus();
        b[1] = new Bus();
        Scanner fs = new Scanner(f);
        String buffer;
        int direction = 0;
        
        while (fs.hasNextLine()){
            buffer = fs.nextLine();
            // if start of set of routes
            if (buffer.contains("RouteStart")){
                stops = new ArrayList();
                b[0].id = buffer.split(": ")[1].split(" ")[0];
                b[0].origin = buffer.split(": ")[1].split(" -")[0];
                b[0].destinationName = buffer.split(": ")[1].split("- ")[1];
                b[1].id = buffer.split(": ")[1].split(" ")[0];
                b[1].origin = buffer.split(": ")[1].split("- ")[1];
                b[1].destinationName = buffer.split(": ")[1].split(" -")[0];
                direction = 0;
            // if reached end of first in pair of routes
            } else if (buffer.length() == 0
                    && b[1].busRoute == null) {
                Stop stopArray[] = new Stop[stops.size()];
                stops.toArray(stopArray);
                b[direction].busRoute = stopArray;
                direction = 1;
                stops = new ArrayList();
            // if reached end of set of routes
            } else if (buffer.contains("RouteStops")){
                Stop stopArray[] = new Stop[stops.size()];
                stops.toArray(stopArray);
                b[direction].busRoute = stopArray;
                busses.put(b[0].id, b);
                b[0] = new Bus();
                b[1] = new Bus();
            // if line contains stop data
            } else {
                Stop s = new Stop(buffer);
                stops.add(s);
            }
        }
        
        return busses;
    }
    
    // opens connection to MTA api
    public static HttpURLConnection openMTAApiConnection() throws MalformedURLException, IOException{
        URL urlToGet = new URL("https://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880");
        return (HttpURLConnection) urlToGet.openConnection();
    }
    
    // opens connection to Google Geocoding api
    public static HttpURLConnection openGoogleApiConnection(String address) 
            throws MalformedURLException, IOException{
        URL urlToGet = new URL("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDVO746CwOhnxOo6KQOrEL1L6as-Ag_sKw&address=" + address);
        return (HttpURLConnection) urlToGet.openConnection();
    }
    
    // downloads File and returns File Name
    public static String getFile(HttpURLConnection httpConn, String saveLocation, 
            String fileName) throws MalformedURLException, IOException{
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveLocation + File.separator + fileName;
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
            int bytesRead = -1;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
        }
        httpConn.disconnect();
        
        return fileName;
    }
    
    public static void startupTasks(){
        apiPoller = new Timer();
        TimerTask update = new UpdateModel();
        apiPoller.schedule(update, 0, 60000);
    }
    
    public static HashMap getCoordinatesForStops(HashMap<String, Bus[]> busses) throws IOException{
        for (Bus b[] : busses.values()){
            for (Stop s : b[0].busRoute){
                String address = s.stopName.split("/")[0] + ", " + 
                        s.stopName.split("/")[1];
                HttpURLConnection conn = Utility.openGoogleApiConnection(address);
                String fileName1 = Utility.getFile(conn, "/home/bill/Documents", "address-data.json");
                // parse to json object and update busses
            }
            // repeat for opposite direction
        }
        
        return busses;
    }
    
    public static JsonObject parseJson(String fn) throws FileNotFoundException{
        File f = new File(fn);
        FileReader fr = new FileReader(f);
        JsonReader reader = Json.createReader(fr);
        return reader.readObject();
    }
}
