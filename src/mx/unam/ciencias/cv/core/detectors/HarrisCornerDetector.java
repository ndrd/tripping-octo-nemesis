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

import mx.unam.ciencias.cv.utils.models.*;
import mx.unam.ciencias.cv.core.filters.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.LinkedList;

public class HarrisCornerDetector extends Detector {

	public static class HarrisParams {
		int sigma;
		int minMeasure;
		/* empirical k*/
		float k;

		public HarrisParams() {
			sigma = 3;
			k = 0.04f;
			minMeasure = 50;
		}

		public HarrisParams(int s, float k, int minMeasure) {
			sigma = s;
			this.k = k;
			this.minMeasure  = minMeasure;
		}
	}

	public static class Corner {

		int x;
		int y;
		double measure;

		public Corner(int x, int y, double mes) {
			this.x = x;
			this.y = y;
			this.measure = mes;
		}
	}

	public static HarrisParams defaultParams() {
		return new HarrisParams();
	}

	public static BufferedImage detect(BufferedImage img, HarrisParams hp) {
		
		FastImage ini = new FastImage(GaussianBlur.gaussianBlur(img, hp.sigma));

		float [][] dx = Detector.sobelX(ini);
		float [][] dy = Detector.sobelY(ini);

		double [][] measures = calculateMeasure(dx,dy, hp.k);
		LinkedList<Corner> locals = localMaximums(measures, hp.minMeasure);
		System.out.println("Corners : " + locals.size());

		return ini.getImage();
	}

	static double[][] calculateMeasure(float[][] dx, float[][] dy, float k) {

		double [][] map = new double[dx.length][dx[0].length];

		float xx,xy,yy, determinant = 0, trace = 0;
		double max = 0;

		for (int x = 0; x < dx.length  ; x++) {
			for (int y = 0; y < dy[0].length  ; y++) {

				xx = dx[x][y] * dx[x][y];
				xy = dx[x][y] * dy[x][y];
				yy = dy[x][y] * dy[x][y];
				/* calculate the measure of the point */
				determinant = xx * yy - xy * xy;
				trace = (xx + yy) * (xx + yy);
				 /* m = det - lamda * trace^2) */
				double  measure = determinant - k * trace;
				map[x][y] = measure;

				max = measure >= max ? measure : max;
			}
		}
		
		/* normalize to 0-100 */
		for (int x = 0; x < dx.length  ; x++) {
			for (int y = 0; y < dy[0].length  ; y++) {

				map[x][y] = (map[x][y] > 0) ? 0 : 1.0f - (Math.log(10 + map[x][y]) / Math.log(10 + max)) * 100 ;

				if (map[x][y] < 100){
				}

			}
		}

		return map;
	}

	static LinkedList<Corner> localMaximums(double [][] mes, int min) {

		LinkedList<Corner> corners = new LinkedList<>();

		for (int x = 1; x < mes.length - 1 ; x++) {
			for (int y = 1; y < mes[0].length - 1 ; y++) {

				boolean isMaximum = mes[x][y] >= min;

				for (int k  = -1; k < 1 && isMaximum; k++) {
					for (int j = -1; j < 1 && isMaximum; j++ ) {
						isMaximum = mes[x][y] > mes[x+k][y+k];
					}
				}

				if (isMaximum) 
					corners.add(new Corner(x,y, mes[x][y]));
			}
		}

		return corners;

	}
	
}