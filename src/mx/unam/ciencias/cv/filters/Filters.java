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
import mx.unam.ciencias.cv.utils.Bilder;
import mx.unam.ciencias.cv.utils.FastImage;

public class Filters {

	private static Filters instance;
	private LinkedList<BufferedImage> images;
	private BufferedImage lastWork;
	private double percentage;
	private boolean ready;
	private final static double FACTOR = 1/3;

	protected Filters () {
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

	public static BufferedImage mixChannels(BufferedImage img) {
		FastImage in = new FastImage(img);
		FastImage out =  new FastImage(img.getWidth(), img.getHeight(), img.getType());

		for (int x = 0; x < img.getWidth() ; x++ ) {
			for (int y = 0; y < img.getHeight() ;y++ ) {
				short [] rgb = in.getPixel(x,y);
				short r = rgb[0];
				short g = rgb[2];
				rgb[0] = rgb[1];
				rgb[1] = g;
				rgb[2] = r;
				out.setPixel(x,y,rgb);
			}
		}
		return out.getImage();
	}

    public static BufferedImage grayScale(BufferedImage img) {
      int width = img.getWidth();
      int height = img.getHeight();
      
      FastImage in = new FastImage(img);
      FastImage out = new FastImage(width, height, img.getType());
      short[] rgb = new short[3];
      for(int y = 0; y < height; y++) {
          for(int x= 0; x < width; x++) {
          	rgb = in.getPixel(x,y);
             short prom = (short)((rgb[0] + rgb[1] +  rgb[2]) * 0.333333);
             rgb[0] = rgb[1] = rgb[2] = prom;
             out.setPixel(x, y,rgb);
          }
      }
      return out.getImage();
    }

	public static BufferedImage colorSelector(BufferedImage src, Color lowerBound, Color upperBound) {
		FastImage in = new FastImage(src);
		int width = src.getWidth();
		int height = src.getHeight();
		FastImage out = new FastImage(width, height, src.getType());
		short [] rgb =  new short[3];
	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rgb = in.getPixel(x, y);
                
                if (rgb[0] >= lowerBound.getRed() && rgb[0] <= upperBound.getRed()) {
                	if (rgb[1] >= lowerBound.getGreen() && rgb[1] <= upperBound.getGreen()) {
                		if (rgb[2] >= lowerBound.getBlue() && rgb[2] <= upperBound.getBlue()) {
                			/* seems legit */
                		}
                	}
                } else {
                	short media = (short)((rgb[0] + rgb[1] + rgb[2])/3);
                	rgb[0] = rgb[1] = rgb[2] = media;
                }
               	out.setPixel(x, y, rgb);
            }
        }
		return out.getImage();
	}

	public static BufferedImage incrementChanelColor(BufferedImage img, int deltaR, int deltaG, int deltaB) {
		int width = img.getWidth();
		int height =  img.getWidth();
		FastImage in =  new FastImage(img);
		FastImage out = new FastImage(width, height, img.getType());
		
		for (int x = 0; x < width ; x++ ) {
			for (int y = 0; y < height ; y++ ) {
				short rgb[] = in.getPixel(x,y);
				rgb[0] = (short)(((rgb[0] + deltaR) > 255) ? 255 : ((rgb[0] + deltaR) < 0) ? 0 : rgb[0] + deltaR); 
				rgb[1] = (short)(((rgb[1] + deltaR) > 255) ? 255 : ((rgb[1] + deltaR) < 0) ? 0 : rgb[1] + deltaR); 
				rgb[2] = (short)(((rgb[2] + deltaR) > 255) ? 255 : ((rgb[2] + deltaR) < 0) ? 0 : rgb[2] + deltaR); 
			}
		}

		return out.getImage();
	}

	public static BufferedImage blending(BufferedImage imgA, BufferedImage imgB, int percentage) {
		/* Intersect images */
		int width = (imgA.getWidth() < imgB.getWidth()) ? imgA.getWidth() : imgB.getWidth();
        int height = (imgA.getHeight() < imgB.getHeight()) ? imgA.getHeight() : imgB.getHeight();
        double alfa = (percentage < 0 || percentage > 100) ? 50 : percentage / 100;
        double beta = 1 - alfa;
        FastImage a = new FastImage(imgA);
        FastImage b = new FastImage(imgB);
        FastImage out = new FastImage(width, height, imgA.getType());

        for (int x = 0; x < width ; x++ ) {
        	for (int y = 0; y < height ; y++ ) {
        		short[] rgbA = a.getPixel(x,y);
        		short[] rgbB = b.getPixel(x,y);
        		rgbA[0] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
        		rgbA[1] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
        		rgbA[2] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
        		out.setPixel(x,y,rgbA);
        	}
        }

        return out.getImage();

	}

}