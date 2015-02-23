package mx.unam.ciencias.cv.views;

import mx.unam.ciencias.cv.utils.*;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Rectangle;

public class Leinen extends Canvas {
	
	private int [] xPoints;
	private int [] yPoints;
	private int [] oldXPoints;
	private int [] oldYPoints;

	private boolean pointsReady;
	private Graphics2D g2;

	public Leinen () {
		setBackground(Color.black);
		setVisible(true);
		pointsReady =  false;

	}

	public void drawRectangle(int x0, int y0, int w, int  h) {
		xPoints = new int[4];
		yPoints =  new int[4];

		xPoints[0] = x0;
		xPoints[1] = x0 + w;
		xPoints[2] = x0 + w;
		xPoints[3] = x0;
		
		yPoints[0] = y0;
		yPoints[1] = y0;
		yPoints[2] = y0 + h;
		yPoints[3] = y0 + h;

		pointsReady =  true;
		repaint();

	}

	public void paint (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = getSize();
		int w = (int) dim.getWidth();
		int h = (int) dim.getHeight();

		g2.setStroke(new BasicStroke(3.0f));
		g2.setColor(Color.white);

		if (pointsReady) 
			g.drawPolygon(xPoints, yPoints, xPoints.length);
	}

	public void applyTransform(Transformation2D transformation) throws Exception {
		int[][] points = transformation.transformPoints(xPoints, yPoints);
		/* backup for undo */
		oldXPoints = xPoints;
		oldYPoints = yPoints;
		/* asign new points */
		xPoints = points[0];
		yPoints = points[1];

		repaint();
	}



} 