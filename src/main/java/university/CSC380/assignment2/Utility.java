package university.CSC380.assignment2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.json.*;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import javax.json.JsonObject;
//import javax.json.JsonReader;

public final class Utility {

    public static void startupTasks() {
        // TODO Auto-generated method stub

    }

    public static HashMap parseSchedule(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    public static String getFile(String url, String saveLocation,
            String fileName) throws MalformedURLException, IOException {
        URL urlToGet = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) urlToGet.openConnection();
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
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

    public void jsonParserTest(String fn) throws IOException, ParseException {
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

    }
}
