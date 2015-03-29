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

	protected static FastImage convolution(FastImage img, Matrix kernel) {
		
		int w = img.getWidth();
		int h = img.getHeight();

		FastImage out  = new FastImage(w, h, img.getType());

		int kx = (kernel.nRows() - 1) / 2;
		int ky = (kernel.nCols() - 1) / 2;

		short [] rgb = new short[3];
		short [] nrgb =  new short[3];

		for (int x = 0; x < w ; x++ ) {
			for (int y = 0; y < h ; y++) {
				
				for (int k = -kx, i = 0; k < (kernel.nRows() - kx) ; k++, i++ ) {
					for (int l = -ky, j = 0; l < (kernel.nCols() - ky) ; l++, j++ ) {
						
						if (pxInRange(w, h, x+k, y+l))
							rgb = img.getPixel(x+k,y+l);
						else if((x+k < 0 || y+l < 0 )&& ( x+k < w && y+l < h))
                         	rgb = img.getPixel(Math.abs(x+k),Math.abs(y+l));

                        nrgb[0] = (short) (rgb[0] * kernel.get(i,j)); 
                        nrgb[1] = (short) (rgb[1] * kernel.get(i,j)); 
                        nrgb[2] = (short) (rgb[2] * kernel.get(i,j)); 
					}
				}

				nrgb[0] = (nrgb[0] > 255) ? 255 : (nrgb[0] < 0 ) ? 0 : nrgb[0];
				nrgb[1] = (nrgb[1] > 255) ? 255 : (nrgb[1] < 0 ) ? 0 : nrgb[1];
				nrgb[2] = (nrgb[2] > 255) ? 255 : (nrgb[2] < 0 ) ? 0 : nrgb[2];

				out.setPixel(x, y, rgb);
			}
		}
		return out;
	}

	protected static boolean pxInRange(int width, int height, int x, int y) { 
         return (x < width && y < height && x >= 0 && y >= 0);
    }

	
}
