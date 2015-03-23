package mx.unam.ciencias.cv.core.miscellaneous;

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
import java.awt.image.WritableRaster;
import mx.unam.ciencias.cv.utils.FastImage;

public class ScaleImage {

	public static BufferedImage scaleInterpolate(BufferedImage img, int  nWidth, int nHeight) {

		if (nWidth < 0 || nHeight < 0)
			throw new IllegalArgumentException("Not suported yet");

		int width = img.getWidth();
		int height = img.getHeight();
		int nx = 0;
		int ny = 0;

		double rx = (double) (width * 1.0 / nWidth * 1.0);
		double ry = (double) (height * 1.0  / nHeight * 1.0);
		double deltaX = 0;
		double deltaY = 0;

		short[] a = new short[3];
		short[] b = new short[3];
		short[] c = new short[3];
		short[] d = new short[3];

		short[] dest = new short[3];

		FastImage src =  new FastImage(img);
		FastImage out = new FastImage(nWidth, nHeight, img.getType());

		for (int y = 0; y < nHeight ; y++ ) {
			for (int x = 0; x < nWidth ; x++ ) {
				
				nx = (int)(x * rx);
				ny = (int)(y * ry);
				deltaX = (x * rx) - nx;
				deltaY = (y * ry) - ny;

				if (pxInRange(width, height, nx, ny))
					a = src.getPixel(nx, ny);

				if (pxInRange(width, height, nx+1, ny))
					b = src.getPixel(nx+1, ny);

				if (pxInRange(width, height, nx, ny+1))
					c = src.getPixel(nx, ny+1);

				if (pxInRange(width, height, nx+1, ny+1))
					d = src.getPixel(nx+1, ny+1);

				dest[0] = (short) Math.rint(
					a[0] * (1 - deltaX) * (1 - deltaY) + b[0] * (1 - deltaX) * (1 - deltaY) +
					c[0] * (1 - deltaX) * (1 - deltaY) + d[0] * (1 - deltaX) * (1 - deltaY)
				);

				dest[1] = (short) Math.rint(
					a[1] * (1 - deltaX) * (1 - deltaY) + b[1] * (1 - deltaX) * (1 - deltaY) +
					c[1] * (1 - deltaX) * (1 - deltaY) + d[1] * (1 - deltaX) * (1 - deltaY)
				);

				dest[2] = (short) Math.rint(
					a[2] * (1 - deltaX) * (1 - deltaY) + b[2] * (1 - deltaX) * (1 - deltaY) +
					c[2] * (1 - deltaX) * (1 - deltaY) + d[2] * (1 - deltaX) * (1 - deltaY)
				);

				out.setPixel(x,y,dest);

			}
		}

		return out.getImage();
	}

	public static BufferedImage scaleInterpolateB(BufferedImage img, int  nWidth, int nHeight) {

		if (nWidth < 0 || nHeight < 0)
			throw new IllegalArgumentException("Not suported yet");

		int width = img.getWidth();
		int height = img.getHeight();
		int nx = 0;
		int ny = 0;

		double rx = (double) (width / nWidth /1.0);
		double ry = (double) (height / nHeight/1.0);
		double deltaX = 0;
		double deltaY = 0;

		double[] a = new double[3];
		double[] b = new double[3];
		double[] c = new double[3];
		double[] d = new double[3];

		double[] dest = new double[3];

		BufferedImage out = new BufferedImage(nWidth, nHeight, img.getType());
		WritableRaster src = img.getRaster();
		WritableRaster outr = out.getRaster();

		for (int y = 0; y < nHeight ; y++ ) {
			for (int x = 0; x < nWidth ; x++ ) {
				
				nx = (int)(x * rx);
				ny = (int)(y * ry);
				deltaX = (x * rx) - nx;
				deltaY = (y * ry) - ny;

				if (pxInRange(width, height, nx, ny))
					a = src.getPixel(nx, ny, a);

				if (pxInRange(width, height, nx+1, ny))
					b = src.getPixel(nx+1, ny,b);
				else
					b = src.getPixel(nx-1, ny,b);


				if (pxInRange(width, height, nx, ny+1))
					c = src.getPixel(nx, ny+1,c);
				else
					c = src.getPixel(nx, ny-1,c);

				if (pxInRange(width, height, nx+1, ny+1))
					d = src.getPixel(nx+1, ny+1,d);
				else if (pxInRange(width, height, nx-1, ny+1))
					d = src.getPixel(nx-1, ny+1,d);
				else if (pxInRange(width, height, nx-1, ny-1))
					d = src.getPixel(nx-1, ny-1,d);

				dest[0] = (
					a[0] * (deltaX) * (deltaY) + b[0] * (1 - deltaX) * (deltaY) +
					d[0] * (deltaX) * (1 - deltaY) + c[0] * (1 - deltaX) * (1 - deltaY)
				);

				dest[1] = (
					a[1] * (deltaX) * (deltaY) + b[1] * (1 - deltaX) * (deltaY) +
					d[1] * (deltaX) * (1 - deltaY) + c[1] * (1 - deltaX) * (1 - deltaY)
				);

				dest[2] = (
					a[2] * (deltaX) * (deltaY) + b[2] * (1 - deltaX) * (deltaY) +
					d[2] * (deltaX) * (1 - deltaY) + c[2] * (1 - deltaX) * (1 - deltaY)
				);
				outr.setPixel(x,y,dest);

			}
		}

		return out;
	}

	private static boolean pxInRange(int width, int height, int x, int y) { 
         return (x < width && y < height && x >= 0 && y >= 0);
    }

}
