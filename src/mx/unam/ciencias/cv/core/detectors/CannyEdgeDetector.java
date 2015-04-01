package mx.unam.ciencias.cv.core.detectors;
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
import mx.unam.ciencias.cv.utils.models.FastImage;
import mx.unam.ciencias.cv.core.miscellaneous.*;
import mx.unam.ciencias.cv.core.filters.*;


public class CannyEdgeDetector extends Detector {

	private final static float BLACK_SHIFT = 10f;
	private final static float RGBIZE = (float) 1.0f / 127.0f;

	public static class CannyParams  {
		int gSigma;
		int lowThreadshold;
		int highThreadshold;
		boolean hysteresis;
		boolean equalize;

		public CannyParams() {
			gSigma = 3;
			lowThreadshold = 15;
			highThreadshold = 50;
			hysteresis = true;
			equalize = false;
		}

		public CannyParams(int sigma, int low, int hight, boolean hist, boolean eq) {
			gSigma = sigma;
			lowThreadshold = low;
			highThreadshold = hight;
			hysteresis = hist;
			equalize = eq;
		}


	}

	public  static CannyParams defaultParams() {
		return new CannyParams();
	}
	public  static CannyParams newParams(int sigma, boolean equalize, int low, int hight) {
		return new CannyParams(sigma, low, hight, true, equalize);
	}

	public static BufferedImage detect (BufferedImage in, CannyParams params) {
		/* Smoth image with gaussian filter */
		FastImage out =  new FastImage(in);

		if (params.equalize)
			in = ColorFilters.contrastEqualization(in);

		FastImage ini = new FastImage(GaussianBlur.gaussianBlur(in, params.gSigma));
		/* compute gradients and orientation */
		float [][] gx = Detector.sobelX(ini);
		float [][] gy = Detector.sobelY(ini);

		/* non-maximum supression */
		float [][] maximums = nonMaximumSuppresion(gx, gy);

		if (params.hysteresis)
			hysteresis(maximums, params.lowThreadshold, params.highThreadshold);
		
		drawMaximum(out, maximums);

		return   out.getImage();

	}

	public static BufferedImage xGradient(BufferedImage img) {
		return Detector.xSobel(new FastImage(img)).getImage();
	}

	public static BufferedImage yGradient(BufferedImage img) {
		return Detector.ySobel(new FastImage(img)).getImage();
	}

	static void drawMaximum(FastImage in, float [][] maximus) {
		
		int width = in.getWidth();
		int height = in.getHeight();
		
		short [] mark = {0,0,0};
		short [] back = {255,255,255};

		for (int x = 0; x < width ;x++ ) {
			for (int y = 0; y < height ; y++ ) {
				mark[0] = mark[1] = mark[2] = (short) Math.ceil(maximus[x][y]);
				in.setPixel(x,y, mark);
			}
		}

	}

	static float[][] nonMaximumSuppresion(float[][] gx, float[][] gy) {

			float [][] magnitudes = new float[gx.length][gy[0].length];
			short [][] orientation = new short[gx.length][gy[0].length];
			
			// neighbords magnitudes 
			float mm, mx, my, n1, n2;

			for (int x = 0; x < gx.length  ; x++) 
				for (int y = 0; y < gy[0].length  ; y++) 
					orientation[x][y] = (short) Math.abs(Math.toDegrees(Math.atan2(gx[x][y],gy[x][y])));
				
			for (int x = 1; x < gx.length - 1 ; x++) {
				for (int y = 1; y < gy[0].length - 1 ; y++) {
					
					mx = gx[x][y];
					my = gy[x][y];
					short angle = orientation[x][y];

					mm = 0;
					
					if (angle >= 0 && angle <= 45) {
						// East and West 
						n1 = gx[x+1][y] * gy[x+1][y];
						n2 = gx[x-1][y] * gy[x-1][y];
						mm = (mx * my >= n1 && mx * my > n2) ? mx * my : 0;

					} else if (angle > 45 && angle <= 90) {
						// NorthEast and SouthWest 
						n1 = gx[x+1][y+1] * gy[x+1][y+1];
						n2 = gx[x-1][y-1] * gy[x-1][y-1];
						mm = (mx * my >= n1 && mx * my > n2) ? mx * my : 0;

					} else if (angle > 90 && angle <= 135) {
						/* Nort and South */
						n1 = gx[x][y+1] * gy[x][y+1];
						n2 = gx[x][y-1] * gy[x][y-1];
						mm = (mx * my >= n1 && mx * my > n2) ? mx * my : 0;

					} else if (angle > 135 && angle <= 180) {
						/* SouthEast and NorthWest */
						n1 = gx[x+1][y-1] * gy[x+1][y-1];
						n2 = gx[x-1][y+1] * gy[x-1][y+1];
						mm = (mx * my >= n1 && mx * my > n2) ? mx * my : 0;

					}

					magnitudes[x][y] = (mm == 0) ?  0 : mm + BLACK_SHIFT;
				}	
			}

			return magnitudes;
	}

	static float[][] hysteresis (float[][] maximums, int low, int high) {
		
		low = low < 0 || low > 255 ? 10 : low; 
		high = high < 0 || high > 255 ? 20 : low == high ? high + 10 : high; 

		for (int x = 0; x < maximums.length; x++ ) {
			for (int y = 0; y < maximums[0].length; y++) {
				if (maximums[x][y] >= high) 
					persecute(maximums,x,y,low);
			}
		}

		return maximums;
	}

	static void persecute(float[][] data, int x, int y, int low) {

		int x0 = (x-1 < 0) ? 0 : x-1;
		int x2 = (x+1 >= data.length -1) ? data.length - 1 : x+1;
		int y0 = (y-1 < 0) ? 0 : y-1;
		int y2 = (y+1 >= data[0].length -1) ? data[0].length - 1 : y+1;

		data[x][y] = 200;

		for (int x1 = x0; x1 < x2; x1++) {
			for (int y1 = y0; y1 < y2; y1++) {
				if ((x != x1 || y != y1) && data[x1][y1] >= low) {
					persecute(data,x1,y1,low);
					return;
				}
			}
		}
	}


	static boolean pxInRange(int width, int height, int x, int y) { 
         return (x < width && y < height && x >= 0 && y >= 0);
    }


}