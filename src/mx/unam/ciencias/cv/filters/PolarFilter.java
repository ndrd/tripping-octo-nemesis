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
	public static float [] sinTabula(int size) {
		double step = 2 * Math.PI / size;
		float [] data =  new float[size];

		for (int i = 0; i < data.length ; data[i++] = (float) Math.sin(Math.PI * i * step)); 
		return data;
	}

	/* Calculates sin with a arbitraty interval */
	public static float [] cosTabula(int size) {
		double step = 2 * Math.PI / size;
		float [] data =  new float[size];

		for (int i = 0; i < data.length ; data[i++] = (float) Math.cos(Math.PI * i * step)); 
		return data;
	}

	public static void printA(float[] a, int w) {
		for (int i = 0; i < a.length ;i++ ) {
			System.out.println(a[i] + " ");
			if(i % w == 0)
				System.out.println();
		}
	}

	public static BufferedImage rectangle2Polar(BufferedImage img) {
		/* We suppose that is a square*/
		int width = img.getWidth();
		int diameter = (int)(Math.PI * width-1); 
		int radius = width / 2;

		FastImage src = new FastImage(img);
		FastImage polar = new FastImage(width, width, img.getType());

		float[] cosTab = cosTabula(diameter);
		float[] sinTab = sinTabula(diameter);

		int x = 0;
		int y = 0;

		short [] srgb = new short[3];

		for (int tetha = 0; tetha < diameter; tetha++) {
			for (int r = 0; r < radius; r++) {

				if (cosTab[tetha] > 0 && sinTab[tetha] > 0) {
					x = (int)(r * cosTab[tetha]);
					y = (int)(r * sinTab[tetha]);
					srgb = src.getPixel(x,y);

					int s = (int)(radius - (r * cosTab[tetha]));
					int t = (int)(radius - (r * sinTab[tetha]));
					polar.setPixel(s,t, srgb);

				}

			}
		}

		return polar.getImage();
	}

}