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
import mx.unam.ciencias.cv.utils.FastImage;
import mx.unam.ciencias.cv.core.filters.*;

public class MixingFilters extends ImageFilter {

	public static BufferedImage blending(BufferedImage imgA, BufferedImage imgB, int percentage) {
		
		setName("blending");

		int width = Math.min(imgA.getWidth(), imgB.getWidth());
		int height = Math.min(imgA.getHeight(),imgB.getHeight());

		double alfa = (percentage < 0 || percentage > 100) ? 50 : percentage / 100.0;
		double beta = 1 - alfa;

		FastImage a = new FastImage(imgA);
		FastImage b = new FastImage(imgB);
		FastImage out = new FastImage(width, height, imgA.getType());
		
		short[] rgbA = new short[3];
		short[] rgbB = new short[3];
		
		for (int x = 0; x < width ; x++ ) {
			for (int y = 0; y < height ; y++ ) {

				rgbA = a.getPixel(x,y);
				rgbB = b.getPixel(x,y);

				rgbA[0] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
				rgbA[1] = (short)(rgbA[1] * alfa + rgbB[1]  * beta);
				rgbA[2] = (short)(rgbA[2] * alfa + rgbB[2]  * beta);

				out.setPixel(x,y,rgbA);
			}
		}

		return out.getImage();
	}

	public static BufferedImage hibridImages (BufferedImage imgA, BufferedImage imgB, 
												int sigmaA, int sigmaB) {

		setName("Hibrid Images");

		int width = Math.max(imgA.getWidth(), imgB.getWidth());
		int height = Math.max(imgA.getHeight(),imgB.getHeight());
		
		BufferedImage blured = GaussianBlur.gaussianBlur(imgA, sigmaA);
		BufferedImage highed = (sigmaA == sigmaB) ? LaplacianFilter.aproxLaplacian(imgB, blured, sigmaB) : LaplacianFilter.aproxLaplacian(imgB, sigmaB);

		return blending(blured, highed, 50);
	}
}