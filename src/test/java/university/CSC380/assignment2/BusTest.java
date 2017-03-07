package university.CSC380.assignment2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class BusTest {

   /* @Test
    public void getExpectedArrivalTimeTest() {
        Bus testBus = new Bus();
        Stop testStop = new Stop();

        testBus.destinationName = "TheFirstStop";
        testBus.expectedArrivalTime = LocalTime.now().plusMinutes(15);
        testBus.longitude = (float) -74.00;
        testBus.latitude = (float) 40.65;
        testBus.longitudeOfLastPolling = (float) -74.00;
        testBus.latitudeOfLastPolling = (float) 40.63;
        testBus.busRoute = new Stop[3];
        testBus.busRoute[0] = new Stop();
        testBus.busRoute[1] = new Stop();
        testBus.busRoute[2] = new Stop();

        testStop.stopName = "TheSecondStop";
        testStop.latitude = (float) 40.75;
        testStop.longitude = (float) -73.9;

        testBus.busRoute[0].latitude = (float) 40.7;
        testBus.busRoute[0].longitude = (float) -74.00;
        testBus.busRoute[0].stopName = "TheFirstStop";
        testBus.busRoute[1].latitude = testStop.latitude;
        testBus.busRoute[1].longitude = testStop.longitude;
        testBus.busRoute[1].stopName = testStop.stopName;
        testBus.busRoute[2].latitude = (float) 40.8;
        testBus.busRoute[2].longitude = (float) -73.8;
        testBus.busRoute[2].stopName = "TheThirdStop";

        LocalTime testTime = testBus.getExpectedArrivalTime(testStop);
        LocalTime expectedTime = LocalTime.now().plusMinutes((long) 11).plusSeconds((long) 40);
        assertEquals(expectedTime, testTime);
    }

    @Test
    public void getExpectedArrivalTimeWrongStopInputTest() throws IllegalArgumentException {
        Bus testBus = new Bus();
        Stop testStop = new Stop();

        testBus.destinationName = "TheFirstStop";
        testBus.busRoute = new Stop[3];
        testBus.busRoute[0] = new Stop();
        testBus.busRoute[1] = new Stop();
        testBus.busRoute[2] = new Stop();
        testBus.busRoute[0].stopName = "TheFirstStop";
        testBus.busRoute[1].stopName = "TheSecondStop";
        testBus.busRoute[2].stopName = "TheThirdStop";

        testStop.stopName = "NotInBusRoute";

        LocalTime testTime = testBus.getExpectedArrivalTime(testStop);
        assertNull(testTime);
    }

    // @author dt817
    @Test
    public void VehicleLocationTest() {
        Bus testBus = new Bus();
        Bus testBus2 = new Bus();
        HashMap<String, Bus> busses = new HashMap();
        testBus.destinationName = "TheFirstStop";
        testBus.longitude = (float) -74.00;
        testBus.lattitude = (float) 40.65;
        testBus2.longitude = (float) 23.54;
        testBus2.lattitude = (float) 50.00;
        busses.put("M4-32", testBus);
        busses.put("M2-56", testBus2);

        VehicleLocation testLocation = new VehicleLocation(busses);
        testLocation.getVehicleLocation();
    }*/

    @Test
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

    public static void jsonParserTest(String fn) throws JSONException {
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

    jsonParserTest(getFile());
	    
	}
