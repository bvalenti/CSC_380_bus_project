package university.CSC380.assignment2;
import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalTime;

import org.junit.Test;



public class BusTest {
	
	@Test
	public void testBusArrivalPrediction() throws IOException {
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
	
	
}
