package university.CSC380.assignment2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import javax.json.*;
import org.json.*;
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

    public static String getFile(String url, String saveLocation) throws MalformedURLException, IOException {

        URL urlToGet = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) urlToGet.openConnection();
        int responseCode = httpConn.getResponseCode();
        String fileName = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
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

    public static void jsonParser(String fn) throws JSONException {
        HashMap<String, Bus> busses = new HashMap();
        JSONObject obj = new JSONObject(fn);//START OBJECT
        JSONObject siri = obj.getJSONObject("Siri");//SIRI OBJECT
        JSONObject serviceDelivery = siri.getJSONObject("ServiceDelivery");//SERVICE DELIVERY OBJECT
        JSONObject vmd = serviceDelivery.getJSONArray("VehicleMonitoringDelivery").getJSONObject(0); //VEHICLE MONITORING DELIVERY OBJECT
        JSONArray va = vmd.getJSONArray("VehicleActivity"); //VEHICLE ACTIVTY ARRAY
        for (int i = 0; i < va.length(); i++) {
            JSONObject mvj = va.getJSONObject(i).getJSONObject("MonitoredVehicleJourney");//MONITORED VEHICLE JOURNEY OBJECT
            JSONObject vl = mvj.getJSONObject("VehicleLocation");//VEHICLE LOCATION OBJECT
            JSONObject mc = mvj.getJSONObject("MonitoredCall");//MONITOR CALL OBJECT
            String busIDRoute = mvj.get("PublishedLineName").toString();
            if (busIDRoute.charAt(0) == 'M') {
                int direction = Integer.parseInt(mvj.get("DirectionRef").toString());
                String destinationName = mvj.get("DestinationName").toString();
                float longitude = Float.parseFloat(vl.get("Longitude").toString());
                float latitude = Float.parseFloat(vl.get("Latitude").toString());
                String busID = mvj.get("VehicleRef").toString();
                LocalDateTime expectedArrivalTime = LocalDateTime.parse(mc.get("ExpectedArrivalTime").toString());
                LocalDateTime expectedDepartureTime = LocalDateTime.parse(mc.get("ExpectedDepartureTime").toString());
                Bus newBus = new Bus(longitude, latitude, busID, destinationName, expectedArrivalTime, expectedDepartureTime, direction);
                busses.put(busIDRoute, newBus);
            }

        }
    }
}
