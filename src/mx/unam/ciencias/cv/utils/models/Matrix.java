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
* Class for common operations with 2D matrix
*/
public class Matrix { 

	protected double matrix [][];
	protected int rows;
	protected int columns;

	public Matrix (int rows, int columns) {
		matrix =  new double[rows][columns];
	}

	public Matrix(int [][] matrix) {
		this.matrix =  new double[matrix.length][matrix[0].length];
		this.rows = matrix.length;
		this.columns = matrix[0].length;

		for (int i = 0; i < matrix.length; i++ ) {
			for (int j = 0; j < matrix[0].length;j++ ) {
				this.matrix[i][j] = matrix[i][j] + 0.0;
			}
		}
	}

	public Matrix(double [][] matrix) {
		this.matrix =  new double[matrix.length][matrix[0].length];
		this.rows = matrix.length;
		this.columns = matrix[0].length;

		for (int i = 0; i < matrix.length; i++ ) {
			for (int j = 0; j < matrix[0].length;j++ ) {
				this.matrix[i][j] = matrix[i][j] + 0.0;
			}
		}
	}

	public static Matrix scalarMultiply(Matrix a, double k) {
		Matrix b = a.clone();
		for (int i = 0;i < a.rows; i++ ) {
			for (int j = 0; j < a.columns;j++ ) {
				b.set(k * a.get(i,j), i, j);
			}
		}
		return b;
	}

	public Matrix clone() {
		double [][] mm = new double[rows][columns];
		for (int i = 0; i < rows ;i++ ) {
			for (int j = 0; j < columns; j++ ) {
				mm[i][j] = matrix[i][j];
			}
		}
		return new Matrix(mm);
	}

	public static Matrix multiplicate(Matrix a, Matrix b) {
		if (a.columns != b.rows)
			throw new IllegalArgumentException("Cannot multiply this matrix");
		double [][] c = new double[a.rows][b.columns];

		double[][] subA = a.matrix;
		double [][] subB = b.matrix;

		for (int i = 0; i < a.rows; i++) {
			for (int j = 0; j < b.columns; j++ ) {
				for (int k = 0; k < a.columns; k++ ) {
					c[i][j] += subA[i][k] * subB[k][j];
				}
			}
		}
 		return new Matrix(c);
	}

	public static HCoords multiplicate(Matrix a, HCoords bb) {
		Matrix b = (Matrix) bb;
		if (a.columns != b.rows)
			throw new IllegalArgumentException("Cannot multiply this matrix");
		double [][] c = new double[a.rows][b.columns];

		double[][] subA = a.matrix;
		double [][] subB = b.matrix;

		for (int i = 0; i < a.rows; i++) {
			for (int j = 0; j < b.columns; j++ ) {
				for (int k = 0; k < a.columns; k++ ) {
					c[i][j] += subA[i][k] * subB[k][j];
				}
			}
		}

		HCoords coords = null;
		/* affine transforms */
		if (c.length == 2)
			coords = new HCoords(c[0][0], c[1][0], 1);
		else
			coords = new HCoords(c[0][0], c[1][0], c[2][0]);

		return coords;
	}

	public int nRows() {
		return this.rows;
	}

	public int nCols(){
		return this.columns;
	}

	public double get(int x,int y) {
		if (x > matrix.length || y > matrix[0].length)
			throw new IllegalArgumentException("Out of Dimentions");
		else
			return matrix[x][y];
	}

	public void set(double value, int x,int y) {
		if (x > matrix.length || y > matrix[0].length)
			throw new IllegalArgumentException("Out of Dimentions");
		else
			matrix[x][y] = value;
	}

	@Override public String toString() {
		String s = "";
		for (int i = 0; i < rows ; i++) {
			for (int j = 0; j < columns; j++ ) {
				s += " " + matrix[i][j];
			}
			s += "\n";
		}
		return s;
	}
}