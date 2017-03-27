package university.CSC380.assignment2;

import static org.junit.Assert.*;
//import java.io.IOException;
import java.time.LocalTime;
//import java.util.HashMap;
import org.junit.Test;
//import static org.mockito.Mockito.*;


public class BusTest {
	
	@Test
	public void getExpectedArrivalTimeTest() {
		Bus testBus = new Bus();
		
		testBus.destinationName = "TheFirstStop";
//		testBus.expectedArrivalTime = LocalTime.now().plusMinutes(15); 
		testBus.longitude = (float) -74.00;
		testBus.latitude = (float) 40.65;
		testBus.lon_last_polling = (float) -74.00;
		testBus.lat_last_polling = (float) 40.63;
		testBus.busRoute = new Stop[3];
		testBus.busRoute[0] = new Stop("ID1","TheFirstStop",40.7,-74.00,1);
		testBus.busRoute[1] = new Stop("ID2","TheSecondtStop",40.75,-73.9,1);
		testBus.busRoute[2] = new Stop("ID3","TheThirdStop",40.8,-73.8,1);
		Stop testStop = testBus.busRoute[1]; //new Stop("ID","TheSecondStop",40.7,-74.00,1);
		
		
		
//		testStop.stop_name = "TheSecondStop";
//		testStop.stop_lat = (float) 40.75;
//		testStop.stop_lon = (float) -73.9;
		
//		testBus.busRoute[0].stop_lat = (float) 40.7;
//		testBus.busRoute[0].stop_lon = (float) -74.00;
//		testBus.busRoute[0].stop_name = "TheFirstStop";
//		testBus.busRoute[1].stop_lat = testStop.stop_lat;
//		testBus.busRoute[1].stop_lon = testStop.stop_lon; 
//		testBus.busRoute[1].stop_name = testStop.stop_name;
//		testBus.busRoute[2].stop_lat = (float) 40.8;
//		testBus.busRoute[2].stop_lon = (float) -73.8;
//		testBus.busRoute[2].stop_name = "TheThirdStop";
		
		String testTime = testBus.getExpectedArrivalTime(testStop);
		String expectedTime = LocalTime.now().plusMinutes((long) 11).plusSeconds((long) 40).toString();
		assertEquals(expectedTime, testTime);
	}
	
	@Test 
	public void getExpectedArrivalTimeWrongStopInputTest () throws IllegalArgumentException {
		Bus testBus = new Bus();
		Stop testStop = new Stop("ID1","NotInBusRoute",41.0,-74.00,1);
		testBus.destinationName = "TheFirstStop";
		testBus.busRoute = new Stop[3];
		testBus.busRoute[0] = new Stop("ID1","TheFirstStop",40.7,-74.00,1);
		testBus.busRoute[1] = new Stop("ID2","TheSecondtStop",40.75,-73.9,1);
		testBus.busRoute[2] = new Stop("ID3","TheThirdStop",40.8,-73.8,1);
		String testTime = testBus.getExpectedArrivalTime(testStop);
		assertNull(testTime);
	}
	
	 // @author dt817
//	    @Test
//	    public void VehicleLocationTest() {
//	        Bus testBus = new Bus();
//	        Bus testBus2=new Bus();
//	        HashMap<String, Bus> busses= new HashMap();
//	        testBus.destinationName = "TheFirstStop";
//			testBus.longitude = (float) -74.00;
//			testBus.latitude = (float) 40.65;
//	        testBus2.longitude = (float) 23.54;
//	        testBus2.latitude = (float) 50.00;
//	        busses.put("M4-32", testBus);
//	        busses.put("M2-56", testBus2);
//			
//	                
//	        VehicleLocation testLocation= new VehicleLocation(busses);
//	        testLocation.getVehicleLocation();
//	    }
//	    
	}
