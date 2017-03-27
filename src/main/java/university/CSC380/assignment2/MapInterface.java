package university.CSC380.assignment2;

import javax.swing.*;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
//import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


//import javafx.scene.shape.
//import javafx.scene.shape.Polyline;

public final class MapInterface {
	
	public static MapObject makeMapInterface() {
		MapObject map = new MapObject();
		map.frame = new JFrame();
		map.kit = new JXMapKit();
		map.parentPanel = new JPanel();
		map.kit.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
		map.kit.setMiniMapVisible(false);
		map.parentPanel.setLayout(new BorderLayout());
		map.parentPanel.add(map.kit, BorderLayout.CENTER);
		map.frame.setContentPane(map.parentPanel);
		map.frame.pack();
		map.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.frame.setVisible(true);
//		map.frame.setBounds(10, 10, 10, 10);
		map.kit.setAddressLocation(new GeoPosition(40.75,-73.9));	
//		map.kit.setZoom(500);
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
		map.kit.getMainMap().setOverlayPainter(map.painter); 
	}
	
	public static void showSetOfBusLocations(HashMap<String, Bus> busses, MapObject map) {
		for (Bus b : busses.values()) {
			showBusLocation(b, map);
		}
	}
	
	public static void clearWaypoints (MapObject map) {
		map.waypoints = new HashSet<MyWaypoint>(); 
	}
	
//	public static void plotBusRoute (Bus bus, MapObject map) {
//		MyWaypoint location = new MyWaypoint(bus.latitude,bus.longitude);
//		location.getPosition();
		
//		map.painter = new WaypointPainter<MyWaypoint>();
//		Set<MyWaypoint> pathpoints = new HashSet<MyWaypoint>(); 
//		MyWaypoint a = new MyWaypoint(bus.route_shape.points.get(i).shape_pt_lat, bus.route_shape.points.get(i).shape_pt_lon);
//		pathpoints.add(a);
//		line.getPoints().add(bus.route_shape.points.get(i));
//		ArrayList<GeoPosition> region = new ArrayList<GeoPosition>();
		
		
//		Polyline line = new Polyline();
//		for (int i = 0; i < bus.route_shape.points.size(); i++) {
//			line.getPoints().addAll(new Double[]{bus.route_shape.points.get(i).shape_pt_lat,
//					bus.route_shape.points.get(i).shape_pt_lon});
//		}


		
		
//		map.painter.setWaypoints(pathpoints);
//		map.kit.getMainMap().setOverlayPainter(map.painter); 		
//}
	
	
//	public static void main (String[] args) {
//		MapObject map = makeMapInterface();
//		Bus b = new Bus();
//		Bus c = new Bus();
//		Bus d = new Bus();
//		b.latitude = (float) 40.7;
//		b.longitude = (float) -74.00;
//		c.latitude = (float) 40.8;
//		c.longitude = (float) -74.00;
//		d.latitude = (float) 40.691737;
//		d.longitude = (float) -73.998835;
//		
//		
////		showBusLocation(b, map);
////		showBusLocation(c, map);
////		showBusLocation(d,map);
////		clearWaypoints(map);
////		showBusLocation(c, map);	
//		
//	}
}
