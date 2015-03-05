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
import mx.unam.ciencias.cv.utils.FastImage;

public class PolarFilter extends ImageFilter {

	/* Calculates sin with a arbitraty interval */
	public static double [] sinTabula(int size) {
		double step = (Math.PI / 4) /  size;

		System.out.printf("%s%1.2f ", "Step " , step);

		double [] data =  new double[size];
		for (int i = 0; i < data.length ; data[i++] = (double) Math.sin(i * step)); 
		return data;
	}

	/* Calculates sin with a arbitraty interval */
	public static double [] cosTabula(int size) {
		double step = (Math.PI / 4) / size;
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

		double[] cosTab = cosTabula(4*diameter);
		double[] sinTab = sinTabula(4*diameter);

		int x = 0;
		int y = 0;

		short [] srgb = new short[3];

		for (int tetha = 0, c = 1; tetha < diameter || c <= 4; ++tetha) {
			for (int r = 0; r < radius; r++) {

				double factor = 1 / 8.0;// / 16.0;
				
				x = (int)(2 * r * cosTab[(int)(tetha)]);
				y = (int)(2 * r * sinTab[(int)(tetha)]);
				srgb = src.getPixel(x,y);

				int rr = (int)(radius + r * Math.cos(tetha * factor));
				int tt = (int)(radius + r * Math.sin(tetha * factor));

				polar.setPixel(rr,tt, srgb);	
			}
			if(tetha % (diameter) == 0) {
				tetha = 0;
				c++;
			}
	}

		return polar.getImage();
	}

}