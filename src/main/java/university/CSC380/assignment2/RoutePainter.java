package university.CSC380.assignment2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
//import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
//import org.jxmapviewer.viewer.GeoPosition;

public class RoutePainter implements Painter<JXMapViewer> {

	JXMapKit kit = new JXMapKit();

	public void paint(Graphics2D g, JXMapViewer map, int a, int b) {

		// g = (Graphics2D) g.create();
		Rectangle rect = kit.getMainMap().getViewportBounds();
		g.translate(-rect.x, -rect.y);

		g.setColor(Color.RED);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(2));

//		int lastX = -1;
//		int lastY = -1;
//		for (final GeoPosition gp : region) {
//			
//			// convert geo to world bitmap pixel
//			final Point2D pt = kit.getMainMap().getTileFactory()
//					.geoToPixel(gp, kit.getMainMap().getZoom());
//			if (lastX != -1 && lastY != -1) {
//				g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
//			}
//			lastX = (int) pt.getX();
//			lastY = (int) pt.getY();
//		}

		g.dispose();

	}

	// kit.getMainMap().setOverlayPainter(lineOverlay);
	// f.setContentPane(jXMapKit1);
	// f.setVisible(true);

}
