package university.CSC380.assignment2;

import javax.swing.*;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



public class MapInterface {
	
	public static MapObject makeMapInterface() {
		MapObject map = new MapObject();
		map.frame = new JFrame();
		map.kit = new JXMapKit();
		map.parentPanel = new JPanel();
		map.kit.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
		map.parentPanel.setLayout(new BorderLayout());
		map.parentPanel.add(map.kit, BorderLayout.CENTER);
		map.frame.setContentPane(map.parentPanel);
		map.frame.pack();
		map.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.frame.setVisible(true);
		map.kit.setAddressLocation(new GeoPosition(40.75,-73.9));		
		return map;
	}
	
	public static void showBusLocation(Bus b, MapObject map) {
		map.kit.setAddressLocation(new GeoPosition(40.75,-73.9));
		MyWaypoint busLocation = new MyWaypoint(b.latitude,b.longitude);
		map.waypoints.add(busLocation); 
		map.painter = new WaypointPainter<MyWaypoint>();
		map.painter.setWaypoints(map.waypoints);
		
		
		MyWaypointRenderer n = new MyWaypointRenderer();
		map.painter.setRenderer(n);
		
//		map.painter.setRenderer(new WaypointRenderer() 
//		{ public boolean paintWaypoint(Graphics2D g, JXMapViewer map, MyWayPoint wp) 
//		{ g.setColor(Color.RED); 
//		g.drawLine(-5,-5,+5,+5); 
//		g.drawLine(-5,+5,+5,-5); 
//		return true; } });
		
		map.kit.getMainMap().setOverlayPainter(map.painter); 
	}
	
	public void showSetOfBusLocations(HashMap<String, Bus> busses, MapObject map) {
		for (Bus b : busses.values()) {
			showBusLocation(b, map);
		}
	}
	
	public static void clearWaypoints (MapObject map) {
		map.waypoints = new HashSet<MyWaypoint>(); 
	}
	
	public static void main (String[] args) {
		MapObject map = makeMapInterface();
		Bus b = new Bus();
		Bus c = new Bus();
		b.latitude = (float) 40.7;
		b.longitude = (float) -74.00;
		c.latitude = (float) 40.8;
		c.longitude = (float) -74.00;
		
		showBusLocation(b, map);
		showBusLocation(c, map);
		clearWaypoints(map);
		showBusLocation(c, map);
	}
}
