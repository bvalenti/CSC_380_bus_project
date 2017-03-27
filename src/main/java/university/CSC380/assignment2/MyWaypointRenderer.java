package university.CSC380.assignment2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

public class MyWaypointRenderer implements WaypointRenderer<MyWaypoint> {
	
	public void paintWaypoint(Graphics2D g, JXMapViewer map, MyWaypoint waypoint) {
		URL IconURL = this.getClass().getResource("/BusIcon.png");
		BufferedImage BusIcon = null;
		try {
			BusIcon = ImageIO.read(IconURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
		int x = (int)point.getX();
		int y = (int)point.getY();
		Image ScaledBusIcon = BusIcon.getScaledInstance(15,15,1);
		g.drawImage(ScaledBusIcon, x, y, map);
	}
}
