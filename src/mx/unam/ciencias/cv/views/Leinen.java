package mx.unam.ciencias.cv.views;
/*
 * This file is part of visual-cosmic-rainbown
 *
 * Copyright Jonathan Andrade 2015
 *
 * visual-cosmic-rainbown is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * visual-cosmic-rainbown is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with visual-cosmic-rainbown. If not, see <http://www.gnu.org/licenses/>.
 */
import mx.unam.ciencias.cv.utils.models.*;
import mx.unam.ciencias.cv.core.geometry.*;

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

/**
* Extends canvas with the needed operations
*/
public class Leinen extends Canvas {
	
	private int [] xPoints;
	private int [] yPoints;
	private int [] oldXPoints;
	private int [] oldYPoints;

	private boolean pointsReady;
	private boolean isBackUp;
	private Graphics2D g2;

	public Leinen () {
		setBackground(Color.black);
		setVisible(true);
		pointsReady =  false;
		isBackUp =  false;
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

	public void undo() {
		if (isBackUp) {
			xPoints = oldXPoints;
			yPoints = oldYPoints;
			repaint();
		}
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
		if (!pointsReady)
			throw new IllegalArgumentException("Build something to Transform");

		int[][] points = transformation.transformPoints(xPoints, yPoints);
		/* backup for undo */
		oldXPoints = xPoints;
		oldYPoints = yPoints;
		isBackUp =  true;
		/* asign new points */
		xPoints = points[0];
		yPoints = points[1];

		repaint();
	}

	public void applyNormTransform(Transformation2D transformation) throws Exception {
		if (!pointsReady)
			throw new IllegalArgumentException("Build something to Transform");

		int[][] points = transformation.transformPointsN(xPoints, yPoints);
		/* backup for undo */
		oldXPoints = xPoints;
		oldYPoints = yPoints;
		isBackUp =  true;
		/* asign new points */
		xPoints = points[0];
		yPoints = points[1];

		repaint();
	}



} 