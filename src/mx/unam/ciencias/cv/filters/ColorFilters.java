package mx.unam.ciencias.cv.filters;
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

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import mx.unam.ciencias.cv.utils.ImageD;
import mx.unam.ciencias.cv.utils.FastImage;
import mx.unam.ciencias.cv.utils.Histogram;

public class ColorFilters extends ImageFilter {

	/* Select pixels that are in the range of lowerBound and upperBound */
	public static BufferedImage colorSelector(BufferedImage src, Color lowerBound, Color upperBound) {

		setName("colorSelector");

		xSize = src.getWidth();
		ySize = src.getHeight();

		FastImage in = new FastImage(src);
		FastImage out = new FastImage(xSize, ySize, src.getType());

		short [] rgb =  new short[3];

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				rgb = in.getPixel(x, y);
				
				if (rgb[0] >= lowerBound.getRed() && rgb[0] <= upperBound.getRed()) {
					if (rgb[1] >= lowerBound.getGreen() && rgb[1] <= upperBound.getGreen()) {
						if (rgb[2] >= lowerBound.getBlue() && rgb[2] <= upperBound.getBlue()) {
							/* color in range, keep it */
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

	/* Makes a grayscale image */
	public static BufferedImage grayScale(BufferedImage img) {
	  xSize = img.getWidth();
	  ySize = img.getHeight();
	  
	  FastImage in = new FastImage(img);
	  FastImage out = new FastImage(xSize, ySize, img.getType());
	  short[] rgb = new short[3];

	  for(int y = 0; y < ySize; y++) {
		  for(int x= 0; x < xSize; x++) {

			rgb = in.getPixel(x,y);
			short prom = (short)((rgb[0] + rgb[1] +  rgb[2]) * 0.333333);
			rgb[0] = rgb[1] = rgb[2] = prom;
			out.setPixel(x, y,rgb);

		  }
	  }
	  return out.getImage();
	}

	/** 
	* Equalize an image seting lowest value of each chanel to absolute black
	* and its bigest value to absolute white, remaping the color palete.
	*/
	public static BufferedImage histogramEqualization(BufferedImage img) {
		final double oDelta = 256;
		ImageD imageDetails = new ImageD(img);

		int minR = imageDetails.getRedHistogram().getMinColor();
		int minG = imageDetails.getGreenHistogram().getMinColor();
		int minB = imageDetails.getBlueHistogram().getMinColor();

		int maxR = imageDetails.getRedHistogram().getMaxColor();
		int maxG = imageDetails.getGreenHistogram().getMaxColor();
		int maxB = imageDetails.getBlueHistogram().getMaxColor();

		double ratioR = oDelta / (maxR - minR);
		double ratioG = oDelta / (maxG - minG);
		double ratioB = oDelta / (maxB - minB);

		short rgb[] =  new short[3];

		for (int x = 0; x < img.getWidth() ; x++ ) {
			for (int y = 0; y < img.getHeight(); y++ ) {
				rgb = imageDetails.getPixel(x,y);
				rgb[0] = (short)((rgb[0] - minR) * ratioR);		
				rgb[1] = (short)((rgb[1] - minG) * ratioG);		
				rgb[2] = (short)((rgb[2] - minB) * ratioB);	
				imageDetails.setPixel(x,y,rgb);	
			}
		}

		return imageDetails.getImage();
	}

	public static BufferedImage contrastEqualization(BufferedImage img) {
		final double oDelta = 255;
		ImageD imageDetails = new ImageD(img);

		int width = img.getWidth();
		int height = img.getHeight();

		int[] newColorsR = remapingColor(imageDetails.getRedHistogram(), width, height);
		int[] newColorsG = remapingColor(imageDetails.getGreenHistogram(), width, height);
		int[] newColorsB = remapingColor(imageDetails.getBlueHistogram(), width, height);

		short rgb[] =  new short[3];

		for (int x = 0; x < img.getWidth() ; x++ ) {
			for (int y = 0; y < img.getHeight(); y++ ) {

				rgb = imageDetails.getPixel(x,y);

				rgb[0] = (short)(newColorsR[rgb[0]]);		
				rgb[1] = (short)(newColorsG[rgb[1]]);		
				rgb[2] = (short)(newColorsB[rgb[2]]);		

				imageDetails.setPixel(x,y,rgb);	

			}
		}

		return imageDetails.getImage();
	}

	private static int getMinCDF(TreeMap<Integer, Integer> cdf) {
		Iterator it = cdf.entrySet().iterator();

		if (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
        	return ((Integer)(pair.getValue())).intValue();
		} else 
			return -1;
	}

	private static int[] remapingColor(Histogram h, int width, int height) {
		
		int[] newColors = new int[256];

		TreeMap<Integer, Integer> cdf = h.getCDF();

		int minCDF = getMinCDF(cdf);
		int q = width * height - minCDF;
		Iterator it = cdf.entrySet().iterator();


		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();

        	int colorOriginal = ((Integer)(pair.getKey())).intValue();
			int hCDF = ((Integer)(pair.getValue())).intValue();
			double newColor = (hCDF - minCDF) / (q * 1.0) * 255;
			
			newColors[colorOriginal] = (int) newColor;
		}

		return newColors;
	}
}