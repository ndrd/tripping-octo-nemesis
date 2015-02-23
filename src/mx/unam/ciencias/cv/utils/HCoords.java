package mx.unam.ciencias.cv.utils;

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