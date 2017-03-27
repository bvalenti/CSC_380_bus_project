/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.CSC380.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;

//import javax.net.ssl.HttpsURLConnection;

//import java.net.URL;
//import java.net.URLConnection;
import java.text.ParseException;
import java.util.HashMap;

//import javax.json.*;

/**
 *
 * @author bill
 */
// Google Geocoding API key : AIzaSyDVO746CwOhnxOo6KQOrEL1L6as-Ag_sKw
public class Main {
	
    public static void main(String[] args) throws FileNotFoundException,
            IOException,
            ParseException,
            org.json.simple.parser.ParseException, Exception {
    	
    	File vehicle = new File(Main.class.getResource( "/vehicle-monitoring.json" ).toURI());
    	HashMap<String, Bus> busses = Utility.jsonParser(vehicle.getAbsolutePath());
    	
    	File trips_txt = new File(Main.class.getResource( "/trips.txt" ).toURI());
    	HashMap<String, Trip> trips = Utility.parseTrips(trips_txt.getAbsolutePath());
    	
    	File stops_txt = new File(Main.class.getResource( "/stops.txt" ).toURI());
    	HashMap<String, Stop> stops = Utility.parseStops(stops_txt.getAbsolutePath());
    	
    	File shapes_txt = new File(Main.class.getResource( "/shapes.txt" ).toURI());
    	HashMap<String, Shape> shapes = Utility.parseShapes(shapes_txt.getAbsolutePath());
    	
    	File stop_times_txt = new File(Main.class.getResource( "/stop_times.txt" ).toURI());
    	trips = Utility.parseStopTimes(stop_times_txt.getAbsolutePath(), trips);
    	
        busses = Utility.assignTrips(busses, trips, stops, shapes);
        
        HashMap<String, Bus> mymap = new HashMap();
        Bus mybusarray[] = new Bus[busses.size()];
        busses.values().toArray(mybusarray);
        
        mymap.put("B1", mybusarray[10]);
        mymap.put("B2", mybusarray[11]);
        mymap.put("B3", mybusarray[12]);
        mymap.put("B4", mybusarray[13]);
        mymap.put("B5", mybusarray[14]);
        mymap.put("B6", mybusarray[15]);
        mymap.put("B7", mybusarray[16]);
        
        MapObject map = MapInterface.makeMapInterface();
        MapInterface.showSetOfBusLocations(mymap, map);
//        MapInterface.showSetOfBusLocations(busses, map);

        Bus a = mybusarray[1];
//        MapInterface.plotBusRoute(a, map);
    }
}
