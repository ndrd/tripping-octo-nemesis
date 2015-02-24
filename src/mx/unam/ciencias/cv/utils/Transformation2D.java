package mx.unam.ciencias.cv.utils;
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

 /**
 * Works with the points that going to be transformed by a transformationMatrix
 */

public class Transformation2D {
	private Matrix matrix;

	public Transformation2D(int [][] kernel) {
		this.matrix = new Matrix(kernel);
	}

	public Transformation2D(Matrix kernel) {
		matrix = kernel;
	}

	/**
	* Transforms a list of points (to draw @link {java.awt.Canvas}) 
	*/
	public int [][] transformPoints(int []x, int []y) throws Exception {
		int [][] points = new int[2][x.length];

		for (int i = 0;i < x.length ;i++ ) {
			HCoords newPoint = transformPoint(x[i], y[i]);
			System.out.println(newPoint);
			points[0][i] = (int) newPoint.getX();
			points[1][i] = (int) newPoint.getY();
		}

		return points;
	}

	private HCoords transformPoint(int x, int y) throws Exception{
		HCoords coords = new HCoords(x, y, 1);
		return (HCoords) Matrix.multiplicate(matrix, coords);
	}

	public Matrix getKernel() {
		return matrix;
	}

}