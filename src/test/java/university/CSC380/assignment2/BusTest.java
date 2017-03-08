package university.CSC380.assignment2;

import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
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

    public void jsonParserTest() throws IOException, ParseException {
        //String fn = Utility.getFile("http://bustime.mta.info/api/siri/vehicle-monitoring.json?key=7a22c3e8-61a7-40ff-9d54-714e36f56880", "C:/Users/dt817/OneDrive/Documents" , "jsonFile.json");
        HashMap<String, Bus> busses = new HashMap();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uu  hh:mm");

        JSONParser parser = new JSONParser();
        Object objfile = parser.parse(new FileReader("C:/Users/dt817/OneDrive/Documents/jsonFile.json"));
        //

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
        
            JSONObject mc = (JSONObject) mvjP.get("MonitoredCall");//MONITOR CALL OBJECT
     
            String busIDRoute = mvjP.get("PublishedLineName").toString();
            if (busIDRoute.charAt(0) == 'M') {
                int direction = Integer.parseInt(mvjP.get("DirectionRef").toString());
                String destinationName = mvjP.get("DestinationName").toString();
                float longitude = Float.parseFloat(vl.get("Longitude").toString());
                float latitude = Float.parseFloat(vl.get("Latitude").toString());
                String busID = mvjP.get("VehicleRef").toString();
                if(!mc.containsKey("ExpectedArrivalTime") || !mc.containsKey("ExpectedDepartureTime")){
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
