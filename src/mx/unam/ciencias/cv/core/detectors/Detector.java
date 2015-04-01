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
import mx.unam.ciencias.cv.utils.models.*;

public abstract class Detector {

	static float[][] sobelX (FastImage img) {
				System.out.println("Sobel X");


		float sobel[][] = new float[img.getWidth()][img.getHeight()];

		int x0,x2,y0,y2;
		short [][] maps =  new short[3][3];
		

		for (int x = 0; x < img.getWidth(); x++ ) {
			for (int y = 0; y < img.getHeight(); y++) {
				
				x0 = (x - 1 < 0) ? 0 : x - 1; 
				x2 = (x + 1 >= img.getWidth()) ? img.getWidth() - 1 : x + 1; 
				
				y0 = (y - 1 < 0) ? 0 : y - 1; 
				y2 = (y + 1 >= img.getHeight()) ? img.getHeight() -1 : y + 1; 

				maps[0][0] = img.getPixel(x0,y0)[0];
				maps[0][1] = img.getPixel(x0,y)[0];
				maps[0][2] = img.getPixel(x0,y2)[0];

				maps[1][0] = img.getPixel(x,y0)[0];
				maps[1][2] = img.getPixel(x,y2)[0];
			
				maps[2][0] = img.getPixel(x2,y0)[0];
				maps[2][1] = img.getPixel(x2,y)[0];
				maps[2][2] = img.getPixel(x2,y2)[0];

				sobel[x][y] = ((maps[2][0] + 2 * maps[2][1] + maps[2][2]) - (maps[0][0] + 2 * maps[0][1] + maps[0][2])) / 8.0f;

			}
		}

		return sobel;
	}

	static float[][] sobelY (FastImage img) {
		System.out.println("Sobel Y");

		float sobel[][] = new float[img.getWidth()][img.getHeight()];

		int x0,x2,y0,y2;
		short [][] maps =  new short[3][3];
		

		for (int x = 0; x < img.getWidth(); x++ ) {
			for (int y = 0; y < img.getHeight(); y++) {
				
				x0 = (x - 1 < 0) ? 0 : x - 1; 
				x2 = (x + 1 >= img.getWidth()) ? img.getWidth() - 1 : x + 1; 
				
				y0 = (y - 1 < 0) ? 0 : y - 1; 
				y2 = (y + 1 >= img.getHeight()) ? img.getHeight() -1 : y + 1; 

				maps[0][0] = img.getPixel(x0,y0)[0];
				maps[0][1] = img.getPixel(x0,y)[0];
				maps[0][2] = img.getPixel(x0,y2)[0];

				maps[1][0] = img.getPixel(x,y0)[0];
				maps[1][2] = img.getPixel(x,y2)[0];
			
				maps[2][0] = img.getPixel(x2,y0)[0];
				maps[2][1] = img.getPixel(x2,y)[0];
				maps[2][2] = img.getPixel(x2,y2)[0];

				sobel[x][y] = ((maps[0][2] + 2 * maps[1][2] + maps[2][2]) - (maps[0][0] + 2 * maps[1][0] + maps[2][0])) / 8.0f;

			}
		}

		return sobel;
	}

	static FastImage xSobel(FastImage in) {
		
		FastImage out =  new FastImage(in.getWidth(), in.getHeight(), in.getType());
		
		float [][] sobel =  sobelX(in);
		short [] rgb =  new short[3];

		for (int x = 0; x < in.getWidth() ; x++ ) { 
			for (int y = 0; y < in.getHeight() ; y++ ) {
				rgb[0] = rgb[1] = rgb[2] = (short) sobel[x][y];
				out.setPixel(x,y,rgb);
			}
		}

		return out;
	}

	static FastImage ySobel(FastImage in) {
		
		FastImage out =  new FastImage(in.getWidth(), in.getHeight(), in.getType());
		
		float [][] sobel =  sobelY(in);
		short [] rgb =  new short[3];

		for (int x = 0; x < in.getWidth() ; x++ ) { 
			for (int y = 0; y < in.getHeight() ; y++ ) {
				rgb[0] = rgb[1] = rgb[2] = (short) sobel[x][y];
				out.setPixel(x,y,rgb);
			}
		}

		return out;
	}


}
