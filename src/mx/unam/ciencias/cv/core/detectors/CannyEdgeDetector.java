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

	private final static float MAGNITUDE_SCALE = 100F;
	private final static float MAGNITUDE_LIMIT = 1000F;
	private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);
	private final static float RGBIZE = (float) 1.0f / 127.0f;

	public static class CannyParams  {

	}

	private BufferedImage edges;
	private BufferedImage in;
	private static int INITIAL_SIGMA = 3;

	public static BufferedImage detect (BufferedImage in, int sigma, int radius) {
		/* Smoth image with gaussian filter */
		FastImage out =  new FastImage(in);
		FastImage ini = new FastImage(GaussianBlur.gaussianBlur(in, sigma));
		/* compute gradients and orientation */
		float [][] gx = Detector.sobelX(ini);
		float [][] gy = Detector.sobelY(ini);


		 /* non-maximum supression */
		 float [][] maximus = nonMaximumSuppresion(gx, gy, radius);
		 drawMaximum(out, maximus);
		 return   out.getImage();

	}

	public static BufferedImage xGradient(BufferedImage img) {
		return Detector.xSobel(new FastImage(img)).getImage();
	}

	public static BufferedImage  yGradient(BufferedImage img) {
		return Detector.ySobel(new FastImage(img)).getImage();
	}

	static void drawMaximum(FastImage in, float [][] maximus) {
		
		int width = in.getWidth();
		int height = in.getHeight();
		
		short [] mark = {0,0,0};
		short [] back = {255,255,255};

		for (int x = 0; x < width ;x++ ) {
			for (int y = 0; y < height ; y++ ) {
				mark[0] = mark[1] = mark[2] = (short) maximus[x][y];
				in.setPixel(x,y, mark);
			}
		}

	}

	static float[][] nonMaximumSuppresion(float[][] gx, float[][] gy, int radius) {

			float normalize = 1f / (255f * 255f);

			float [][] magnitudes = new float[gx.length][gy[0].length];
			short [][] orientation = new short[gx.length][gy[0].length];
			/* neighbords magnitudes */
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
						/* East and West */
						n1 = gx[x+1][y] * gy[x+1][y];
						n2 = gx[x-1][y] * gy[x-1][y];
						mm = (mx * my >= n1 && mx * my > n2) ? mx * my : 0;

					} else if (angle > 45 && angle <= 90) {
						/* NorthEast and SouthWest */
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

					magnitudes[x][y] = mm;
				}	
			}

			return magnitudes;
	}

	static boolean pxInRange(int width, int height, int x, int y) { 
         return (x < width && y < height && x >= 0 && y >= 0);
    }

	void drawEdges(int data[], int width, int height) {
		edges = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		edges.getWritableTile(0, 0).setDataElements(0, 0, width, height, data);
	}

}