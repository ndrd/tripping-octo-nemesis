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
				byte [] rgb = in.getPixel(x,y);
				byte r = rgb[0];
				byte g = rgb[2];
				rgb[0] = rgb[1];
				rgb[1] = g;
				rgb[2] = r;
				out.setPixel(x,y,rgb);
			}
		}
		return out.getImage();
	}

	/**
	* Gets a grayScale im
	*/
	public static BufferedImage grayScale(BufferedImage img) {
      int width = img.getWidth();
      int height = img.getHeight();
      
      BufferedImage result = new BufferedImage(width, height, img.getType());

      for(int y = 0; y < height; y++) {
          for(int x= 0; x < width;x++) {
             int pixel = img.getRGB(x, y);
             int alfa = (pixel >> 24) & 0x000000FF;
             int red = (pixel >> 16) & 0x000000FF;
             int green = (pixel >> 8 ) & 0x000000FF;
             int blue = (pixel) & 0x000000FF;
             int prom = (red + green + blue)/3;
                          
             pixel = (pixel & ~(0x000000FF << 16)) | (prom << 16);
             pixel = (pixel & ~(0x000000FF << 8)) | (prom << 8);
             pixel = (pixel & ~(0x000000FF )) | (prom);
             result.setRGB(x, y, pixel);
          }
      }
      return result;
    }

	public static BufferedImage colorSelector(BufferedImage src, Color lowerBound, Color upperBound) {
		Bilder bild = new Bilder(src);
		int width = bild.getWidth();
		int height = bild.getHeight();
		BufferedImage out = new BufferedImage(width, height, bild.getType());
		WritableRaster raster = out.getRaster();

	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double [] rgb = bild.getRGB1(x, y);
                
                if (rgb[0] >= lowerBound.getRed() && rgb[0] <= upperBound.getRed()) {
                	if (rgb[1] >= lowerBound.getGreen() && rgb[1] <= upperBound.getGreen()) {
                		if (rgb[2] >= lowerBound.getBlue() && rgb[2] <= upperBound.getBlue()) {
                			/* seems legit */
                		}
                	}
                } else {
                	double media = (rgb[0] + rgb[1] + rgb[2]) / 3;
                	rgb[0] = rgb[1] = rgb[2] = media;
                }
               	raster.setPixel(x, y, rgb);
            }
        }
		return out;
	}

	public static BufferedImage incrementChanelColor(BufferedImage img, int deltaR, int deltaG, int deltaB) {
		Bilder bild = new Bilder(img);
		Bilder newImg =  new Bilder(img.getWidth(), img.getHeight(), true);
		BufferedImage response =  new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		WritableRaster raster =  response.getRaster();

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				double[] rgb = bild.getRGB1(x,y);
				rgb[0] = ((rgb[0] + deltaR) > 255) ? 255 : ((rgb[0] + deltaR) < 0) ? 0 : rgb[0] + deltaR;  
				rgb[1] = ((rgb[1] + deltaG) > 255) ? 255 : ((rgb[1] + deltaG) < 0) ? 0 : rgb[1] + deltaG;  
				rgb[2] = ((rgb[2] + deltaB) > 255) ? 255 : ((rgb[2] + deltaB) < 0) ? 0 : rgb[2] + deltaB;  
				raster.setPixel(x, y, rgb);
			}
		}

		return response;
	}


}