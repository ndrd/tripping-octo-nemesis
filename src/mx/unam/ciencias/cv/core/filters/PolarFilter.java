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

import java.awt.image.BufferedImage;
import mx.unam.ciencias.cv.utils.models.FastImage;


public class PolarFilter extends ImageFilter {

	/* Calculates sin with a arbitraty interval */
	public static double [] sinTabula(int size) {
		double step = (Math.PI / 2) /  size;

		System.out.printf("%s%1.2f ", "Step " , step);

		double [] data =  new double[size];
		for (int i = 0; i < data.length ; data[i++] = (double) Math.sin(i * step)); 
		return data;
	}

	/* Calculates sin with a arbitraty interval */
	public static double [] cosTabula(int size) {
		double step = (Math.PI / 2) / size;
		double [] data =  new double[size];
		for (int i = 0; i < data.length ; data[i++] = (double) Math.cos(i * step)); 
		return data;
	}

	public static void printA(double[] a, int w) {
		for (int i = 0; i < a.length ;i++ ) {
			System.out.printf("%s%1.2f ",(a[i] > 0) ? " " : "", a[i]);
			if(i % w == 0)
				System.out.println();
		}
	}

	
	public static BufferedImage rectangle2Polar(BufferedImage img) {
		/* We suppose that is a square*/
		int width = img.getWidth();
		int diameter = (int)(Math.PI * width); 
		int radius = width / 2;

		FastImage src = new FastImage(img);
		FastImage polar = new FastImage(width, width, img.getType());

		double[] cosTab = cosTabula(diameter);
		double[] sinTab = sinTabula(diameter);

		int x = 0;
		int y = 0;

		short [] srgb = new short[3];

		for (int tetha = 0; tetha < diameter; ++tetha) {
			for (int r = 0, c = 0; r < radius  ; r++) {

				double factor = 1/((width-2) / 2.0);
				x = (int)(2 * r * cosTab[(int)(tetha)]);
				y = (int)(2 *r * sinTab[(int)(tetha)]);
				
				if (pxInRange(width, width, x, y))
					srgb = src.getPixel(x,y);

				int rr = (int)(radius + r * Math.cos(tetha * factor));
				int tt = (int)(radius + r * Math.sin(tetha * factor));

				if (pxInRange(width, width, rr, tt))
					polar.setPixel(rr,tt, srgb);

			}

	}

		return polar.getImage();
	}

	public static BufferedImage rectangle2PolarA(BufferedImage img) {
		
		int width = img.getWidth();
		int height = img.getHeight();

		FastImage src = new FastImage(img);
		FastImage polar = new FastImage(width, height, img.getType());
		short[] rgb =  new short[3];

		int cx = (int) (width / 2.0);
		int cy = (int) (height / 2.0);
		double factor = (width /(Math.PI * 2.0) );

		for (int x = 0; x < width ; x++) {
			for ( int y = 0; y < height ; y++) {
				
				int r = (int) (Math.sqrt((cx-x)*(cx-x) + (cy-y)*(cy-y))  * 1.2);
				int tetha = (int) ((Math.atan2(cy-y, cx-x) * factor) + cy);

				if (r > height)
					r = (int)((r/2.0) * (height/r));

				if (pxInRange(width, height, width - tetha, height - r))
					rgb = src.getPixel(width - tetha, height -  r);

				polar.setPixel(x,y,rgb);
			}
		}

		return polar.getImage();

	}

}