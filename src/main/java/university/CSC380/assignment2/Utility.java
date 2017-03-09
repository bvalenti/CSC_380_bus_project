/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class Utility {

    private static Timer apiPoller;
    private static TimerTask apiPollerTask;
    public static int updateModelExecuteCount;

    private Utility() {
    }

    // parses Schedule in txt file and returns hashmap of <id, Bus>
    public static HashMap parseSchedule(String fn, HashMap<String, Bus> hm) throws FileNotFoundException {
        File f = new File(fn);
        ArrayList<Stop> stops = new ArrayList();
        Bus b[] = new Bus[2];
        b[0] = new Bus();
        b[1] = new Bus();
        Scanner fs = new Scanner(f);
        String buffer;
        int direction = 0;
        String[] keys = new String[2];
        String id = null, origin1 = null, origin2 = null, 
                destinationName1 = null, destinationName2 = null;

        while (fs.hasNextLine()) {
            buffer = fs.nextLine();
            // if start of set of routes
            if (buffer.contains("RouteStart")) {
                id = buffer.split(": ")[1].split(" ")[0];
//                origin1 = buffer.split(": ")[1].split(" -")[0];
//                destinationName1 = buffer.split(": ")[1].split("- ")[1];
//                origin2 = buffer.split(": ")[1].split("- ")[1];
//                destinationName2 = buffer.split(": ")[1].split(" -")[0];
                
                //find first bus in hashmap with same route and direction
                for (Bus bus : hm.values()){
                    if (bus.busRoute == null && bus.id != null
                            && bus.id.equals(id) 
                            && bus.direction == 1){
                        b[0] = bus;
                        for (String k : hm.keySet()){
                            if (hm.get(k) == bus){
                                keys[0] = k;
                                break;
                            }
                        }
                        break;
                    }
                }
                
                //find second bus in hashmap with same route and direction
                for (Bus bus : hm.values()){
                    if (bus.busRoute == null && bus.id != null
                            && bus.id.equals(id) 
                            && bus.direction == 0){
                        b[1] = bus;
                        for (String k : hm.keySet()){
                            if (hm.get(k) == bus){
                                keys[1] = k;
                                break;
                            }
                        }
                        break;
                    }
                }
                
                stops = new ArrayList();
                
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
            } else if (buffer.contains("RouteStops")) {
                Stop stopArray[] = new Stop[stops.size()];
                stops.toArray(stopArray);
                b[direction].busRoute = stopArray;
                
                //replace busses in hashmap
                hm.put(keys[0], b[0]);
                hm.put(keys[1], b[1]);
                
                //copy route to all other relevant busses
                for (Bus bus : hm.values()){
                    if (bus.busRoute == null && bus.id != null 
                            && bus.id.equals(id) 
                            && bus.direction == 1){
                        bus.busRoute = b[0].busRoute;
                    } else if (bus.busRoute == null && bus.id != null
                            && bus.id.equals(id) 
                            && bus.direction == 0) {
                        bus.busRoute = b[1].busRoute;
                    }
                }
                
                b[0] = new Bus();
                b[1] = new Bus();
                // if line contains stop data
            } else {
                Stop s = new Stop(buffer);
                stops.add(s);
            }
        }

        return hm;
    }
    
    // opens connection to MTA api
    public static HttpURLConnection openMTAApiConnection() throws MalformedURLException, IOException {
        //URL urlToGet = new URL("https://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880");
        URL urlToGet = new URL("http://api.prod.obanyc.com/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880");

        return (HttpURLConnection) urlToGet.openConnection();
    }

    // opens connection to Google Geocoding api
    public static HttpsURLConnection openGoogleApiConnection(String address)
            throws MalformedURLException, IOException {
        address = address.replace(" ", "%20");
        URL urlToGet = new URL("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDVO746CwOhnxOo6KQOrEL1L6as-Ag_sKw&address=" + address);
        System.out.println("URL = " + urlToGet.toString());
        return (HttpsURLConnection) urlToGet.openConnection();
    }

    // downloads File and returns File Name
    public static String getFile(URLConnection conn, String saveLocation,
            String fileName, boolean https) throws MalformedURLException, IOException {
        int responseCode;
        if (https) {
            responseCode = ((HttpsURLConnection) conn).getResponseCode();
        } else {
            responseCode = ((HttpURLConnection) conn).getResponseCode();
        }
        if (responseCode == 200) {
            InputStream inputStream = conn.getInputStream();
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
        if (https) {
            ((HttpsURLConnection) conn).disconnect();
        } else {
            ((HttpURLConnection) conn).disconnect();
        }

        return fileName;
    }

    // begins apiPoller task executed regularly to update vehicle-data
    public static void startupTasks() {
        apiPoller = new Timer();
        apiPollerTask = new UpdateModel();
        apiPoller.schedule(apiPollerTask, 0, 70000);
    }

    // stops the apiPoller task that is regularly executed
    public static void stopApiPoller() {
        apiPollerTask.cancel();
        apiPoller.cancel();
    }

    public static HashMap getCoordinatesForStops(HashMap<String, Bus[]> busses) throws IOException {
        for (Bus b[] : busses.values()) {
            for (Stop s : b[0].busRoute) {
                String address = s.stopName.split("/")[0] + ", "
                        + s.stopName.split("/")[1];
                HttpsURLConnection conn
                        = Utility.openGoogleApiConnection(address);
                String fileName1 = Utility.getFile(conn,
                        "/home/bill/Documents", "address-data.json", true);
                // parse to json object and update busses
            }
            // repeat for opposite direction
        }

        return busses;
    }

    public static JsonObject createJsonObject(String fn) throws FileNotFoundException {
        File f = new File(fn);
        FileReader fr = new FileReader(f);
        JsonReader reader = Json.createReader(fr);
        return reader.readObject();
    }
    
    public static HashMap jsonParser(String fn) throws IOException, ParseException, org.json.simple.parser.ParseException {
        //String fn = Utility.getFile("http://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880", "C:/Users/dt817/OneDrive/Documents" , "jsonFile.json");
        HashMap<String, Bus> busses = new HashMap();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uu  hh:mm");

        JSONParser parser = new JSONParser();
        Object objfile = parser.parse(new FileReader(fn));//Add your file location before fn: Ex. "C://steve/"+fn

        JSONObject obj = (JSONObject) objfile;//START OBJECT

        JSONObject siri = (JSONObject) obj.get("Siri");//SIRI OBJECT
        JSONObject serviceDelivery = (JSONObject) siri.get("ServiceDelivery");//SERVICE DELIVERY OBJECT
        JSONArray vmd = (JSONArray) serviceDelivery.get("VehicleMonitoringDelivery");
        JSONObject vmdP = (JSONObject) vmd.get(0);//VEHICLE MONITORING DELIVERY OBJECT
        JSONArray va = (JSONArray) vmdP.get("VehicleActivity"); //VEHICLE ACTIVTY ARRAY

        for (int i = 0; i < va.size(); i++) {
            JSONObject mvj = (JSONObject) va.get(i);

            JSONObject mvjP = (JSONObject) mvj.get("MonitoredVehicleJourney");//MONITORED VEHICLE JOURNEY OBJECT

            JSONObject vl = (JSONObject) mvjP.get("VehicleLocation");//VEHICLE LOCATION OBJECT

            JSONObject mc = (JSONObject) mvjP.get("MonitoredCall");//MONITORED CALL OBJECT

            String busIDRoute = mvjP.get("PublishedLineName").toString();
            if (busIDRoute.charAt(0) == 'M') {
                int direction = Integer.parseInt(mvjP.get("DirectionRef").toString());
                String destinationName = mvjP.get("DestinationName").toString();
                float longitude = Float.parseFloat(vl.get("Longitude").toString());
                float latitude = Float.parseFloat(vl.get("Latitude").toString());
                String busID = mvjP.get("VehicleRef").toString();
                if (!mc.containsKey("ExpectedArrivalTime") || !mc.containsKey("ExpectedDepartureTime")) {
                    continue;
                }
                String expectedArrivalTime = formatter.format(ZonedDateTime.parse(mc.get("ExpectedArrivalTime").toString()));
                // System.out.println(expectedArrivalTime);
                String expectedDepartureTime = formatter.format(ZonedDateTime.parse(mc.get("ExpectedDepartureTime").toString()));
                //if(longitude != 0 && latitude != 0 && busID != null && destinationName != null && expectedArrivalTime != null && expectedDepartureTime != null && direction != 0){
                Bus newBus = new Bus(longitude, latitude, busIDRoute, destinationName, expectedArrivalTime, expectedDepartureTime, direction);
                busses.put(busID, newBus);
                // }

            }
        }
        
        return busses;

    }

}
