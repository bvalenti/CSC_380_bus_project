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

/**
 *
 * @author bill
 */
public final class Utility {
    
    private static Timer apiPoller;
    
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
    
    // downloads File and returns File Name
    public static String getFile(String url, String saveLocation) 
            throws MalformedURLException, IOException{
        URL urlToGet = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) 
                urlToGet.openConnection();
        int responseCode = httpConn.getResponseCode();
        String fileName = "";
        if (responseCode == HttpURLConnection.HTTP_OK){
            // next line will break if MTA Bus API url with key not used
            fileName = url.substring(url.lastIndexOf("/") + 1,
                        url.lastIndexOf("?"));
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
}
