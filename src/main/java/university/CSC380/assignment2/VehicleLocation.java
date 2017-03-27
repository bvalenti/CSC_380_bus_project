package university.CSC380.assignment2;

import java.util.HashMap;

/**
 * 
 * @author dt817
 */
public class VehicleLocation {
	HashMap<String, Bus> busses = new HashMap();
	
	public VehicleLocation(HashMap<String, Bus> bus) {
		busses = bus;
	}

	/*
	 * Method gets the coordinates of all available busses
	 */
	public void getVehicleLocation() {
		for (String id : busses.keySet()) {
			String coordinates = "lat: " + busses.get(id).latitude + " lon: "
					+ busses.get(id).longitude;
			System.out.println(id + " " + coordinates); 
		}
	}
}
