package mx.unam.ciencias.cv.utils;
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

		System.out.println("Mult");
		System.out.println("A:\n" + a);
		System.out.println("B:\n" + b);

		for (int i = 0; i < a.rows; i++) {
			for (int j = 0; j < b.columns; j++ ) {
				for (int k = 0; k < a.columns; k++ ) {
					c[i][j] += subA[i][k] * subB[k][j];
				}
			}
		}

		Matrix response = new Matrix(c);
		System.out.println("C:\n" + response);

		return response;
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

		return new HCoords(c[0][0], c[1][0], c[2][0]);
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