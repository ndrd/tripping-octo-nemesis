package mx.unam.ciencias.cv.core.filters;
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
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class ImageFilter {

	protected static int xSize;
	protected static int ySize;
	private static String name;


	public static int intersect(int d1, int d2) {
		return Math.max(d1,d2);
	}

	public static void debug(Object o) {
		System.out.println(o);
	}

	public static void setName(String a) {
		name = a;
	}

	public static String getName() {
		return name;
	}

	public static FastImage convolution(FastImage img, Matrix kernel) {

		System.out.println("Convolving...");
		System.out.println(kernel);

		int w = img.getWidth();
		int h = img.getHeight();

		FastImage out  = new FastImage(w, h, img.getType());

		int kx = kernel.nRows() / 2;
		int ky = kernel.nCols() / 2;

		short [] rgb = new short[3];
		short [] nrgb =  new short[3];

		for (int x = 0; x < w ; x++ ) {
			for (int y = 0; y < h ; y++) {
				
				for (int k = -kx, i = 0; k < (kernel.nRows() - kx) ; k++, i++ ) {
					for (int l = -ky, j = 0; l < (kernel.nCols() - ky) ; l++, j++ ) {
						
						if (pxInRange(w, h, x+k, y+l)){
							rgb = img.getPixel(x+k,y+l);
							nrgb[0] += (short) (rgb[0] * kernel.get(i,j)); 
	                        nrgb[1] += (short) (rgb[1] * kernel.get(i,j)); 
	                        nrgb[2] += (short) (rgb[2] * kernel.get(i,j)); 

						}

					}
				}
				
				nrgb[0] = (short) ((nrgb[0] >= 255) ? 0 : (nrgb[0] <= 0 ) ? 255 : 255 - nrgb[0]);
				nrgb[1] = (short) ((nrgb[1] >= 255) ? 0 : (nrgb[1] <= 0 ) ? 255 : 255 - nrgb[1]);
				nrgb[2] = (short) ((nrgb[2] >= 255) ? 0 : (nrgb[2] <= 0 ) ? 255 : 255 - nrgb[2]);
				
				out.setPixel(x, y, nrgb);
			}
		}
		return out;
	}

	public static BufferedImage convolution(BufferedImage img, double [][]kernel) {
		
		int width = img.getWidth();
		int height = img.getHeight();

		BufferedImage outImg = new BufferedImage(width, height, img.getType());
		WritableRaster out = outImg.getRaster();
		WritableRaster in = img.getRaster();

		double rgb[] = new double[3];
		double srgb[] = new double[3];
		int kx = (int)Math.floor(kernel.length / 2.0f);
		int ky = (int)Math.floor(kernel[0].length / 2.0f);

		for (int x = 0; x < width ; x++ ) {
			for (int y = 0; y < height ; y++) {

				for (int k = -kx, i = 0; k < (kernel.length - kx) ; k++, i++ ) {
					for (int l = -ky, j = 0; l < (kernel[0].length - ky) ; l++, j++ ) {
						if (pxInRange(width, height, x+k, y+l)){
							rgb = in.getPixel(x+k, y+l, rgb);
							srgb[0] += kernel[i][j] * rgb[0];
							srgb[1] += kernel[i][j] * rgb[1];
							srgb[2] += kernel[i][j] * rgb[2];
						}
					}
				}
				
				normalizeColor(srgb);
				out.setPixel(x,y, srgb);
			}
		}

		return outImg;

	}

	static void normalizeColor(double[] rgb) {
		rgb[0] = (rgb[0] < 0) ? 0 : (rgb[0] > 255) ? 255 : rgb[0];
		rgb[1] = (rgb[1] < 0) ? 0 : (rgb[1] > 255) ? 255 : rgb[1];
		rgb[2] = (rgb[2] < 0) ? 0 : (rgb[2] > 255) ? 255 : rgb[2];
	}

	protected static boolean pxInRange(int width, int height, int x, int y) { 
         return (x < width && y < height && x >= 0 && y >= 0);
    }

	
}
