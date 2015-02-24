package mx.unam.ciencias.cv.filters;
/*
 * This file is part of tom
 *
 * Copyright Jonathan Andrade 2015
 *
 * tom is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * tom is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with tom. If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.awt.Color;

public class Filters {

	private static Filters instance;
	private LinkedList<BufferedImage> images;
	private BufferedImage lastWork;
	private double percentage;
	private boolean ready;
	private final double FACTOR = 1/3;

	private Filters () {
		images = new LinkedList<BufferedImage>();
		percentage = 0;
		ready = false;
	}

	public static Filters getInstance() {
		if (instance == null)
			instance = new Filters();
		return instance;
	}

	public void addImage(BufferedImage img) {
		images.add(img);
		ready = true;
	}

	public BufferedImage getLastImage() {
		if ( images.size() > 0) 
			return images.getLast();
		return null;
	}

	public BufferedImage getLastWork() {
		return lastWork;
	}

	public void setLastWork(BufferedImage img) {
		lastWork = img;
	}

	public static BufferedImage grayScale(BufferedImage src) {
		WritableRaster rIn = src.getRaster();
		int width = rIn.getWidth();
		int height = rIn.getHeight();
		int type = src.getType();

		BufferedImage r = new BufferedImage(width, height, type);
        WritableRaster rOut = r.getRaster();

        double[] rgb = new double[3];
	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rIn.getPixel(x, y, rgb);
                double media = (rgb[0]  + rgb[1] + rgb[2]) * FACTOR;
                rgb[0] = rgb[1] = rgb[2] =  media;
               	rOut.setPixel(x, y, rgb);
            }
        }
		
		return r;
	}

	public static BufferedImage colorSelector(BufferedImage src, Color lowerBound, Color upperBound) {
		WritableRaster rIn = src.getRaster();
		int width = rIn.getWidth();
		int height = rIn.getHeight();
		int type = src.getType();

		BufferedImage r = new BufferedImage(width, height, type);
        WritableRaster rOut = r.getRaster();

        double[] rgb = new double[3];
	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rIn.getPixel(x, y, rgb);
                
                if (rgb[0] >= lowerBound.getRed() && rgb[0] <= upperBound.getRed()) {
                	if (rgb[1] >= lowerBound.getGreen() && rgb[1] <= upperBound.getGreen()) {
                		if (rgb[2] >= lowerBound.getBlue() && rgb[2] <= upperBound.getBlue()) {
                			/* seems legit */
                		}
                	}
                } else {
                	double media = (rgb[0] + rgb[1] + rgb[2]) * FACTOR;
                	rgb[0] = rgb[1] = rgb[2] = media;
                }
                
               	rOut.setPixel(x, y, rgb);
            }
        }
		
		return r;
	}
}