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
import java.util.LinkedList;

/**
* Extends canvas with the needed operations
*/
public class HistogramView extends Canvas {
    
    private LinkedList<Histogram> histograms;
    private boolean pointsReady;
    private Graphics2D g2;

    public HistogramView (Histogram h) {
        setBackground(Color.black);
        setVisible(true);
        pointsReady =  false;
        histograms =  new LinkedList<Histogram>();
        histograms.add(h);
    }

    public void addHistogram(Histogram h) {
        histograms.add(h);
        repaint();
    }

    private void plotHistogram(Histogram h, Color c, int width, int height, Graphics g) {
        //awPolygon(xPoints, yPoints, xPoints.length);

    }

    public void paint (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = getSize();
        int w = (int) dim.getWidth();
        int h = (int) dim.getHeight();
    }




} 