package university.CSC380.assignment2;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.viewer.WaypointPainter;



public class MapObject {
	public JFrame frame; 
	public JXMapKit kit; 
	public JPanel parentPanel; 
	public WaypointPainter<MyWaypoint> painter;
	public Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(); 
	
	
//	public JFrame getFrame() {
//		return frame;
//	}
//	public void setFrame(JFrame frame) {
//		this.frame = frame;
//	}
//	public JXMapKit getKit() {
//		return kit;
//	}
//	public void setKit(JXMapKit kit) {
//		this.kit = kit;
//	}
//	public JPanel getParentPanel() {
//		return parentPanel;
//	}
//	public void setParentPanel(JPanel parentPanel) {
//		this.parentPanel = parentPanel;
//	} 
}
