package mx.unam.ciencias.cv.utils.models;

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

/**
* Model Homogeneus Coordinates, a special kind of matrix
* (vector column)
*/
public class HCoords extends Matrix {
	
	public HCoords(double x, double y, double w) {
		super(1,3);
		double [][] m = { {x}, {y}, {w} };
		matrix = m;
		rows = matrix.length;
		columns = matrix[0].length;
	}

	/* We suppose that is only one row, three columns */
	public double getX() {
		return matrix[0][0];
	}

	/* We suppose that is only one row, three columns */
	public double getY() {
		return matrix[1][0];
	}

	/* We suppose that is only one row, three columns */
	public double getW() {
		return matrix[2][0];
	}
}