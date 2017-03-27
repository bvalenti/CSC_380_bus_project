package university.CSC380.assignment2;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;


public class MyWaypoint implements Waypoint {
	private GeoPosition a;
	
	public MyWaypoint (double lat, double lon) {
		a = new GeoPosition(lat, lon);
	}
	
	public GeoPosition getPosition() {
		return a;
	}
}
