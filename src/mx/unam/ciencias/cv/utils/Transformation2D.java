package mx.unam.ciencias.cv.utils;

public class Transformation2D{
	private int [][] matrix;

	public Transformation2D(int [][] matrix) {
		this.matrix = matrix;
	}

	public void transformPoints(int []x, int []y) throws Exception {
		for (int i = 0;i < x.length ;i++ ) {
			System.out.print("(" + x[i] + ", " + y[i] + " ) , ");
		}

		for (int i = 0;i < x.length ;i++ ) {
			int [][] newPoint = transformPoint(x[i], y[i]);
			System.out.print("(" + newPoint[0][0] + ", " + newPoint[0][1] + " ) , ");
		}
	}

	public int[][] transformPoint(int x, int y) throws Exception{
		int [][] hCoords = {{x},{y},{1}};
		int[][] newPoint = multiplicationMatrix(hCoords, matrix);
		return newPoint;
	}

	private int[][] multiplicationMatrix(int[][] a, int [][] b) throws Exception {
	   int rowsInA = a.length;
       int columnsInA = a[0].length; // same as rows in B
       int columnsInB = b[0].length;
       int[][] c = new int[rowsInA][columnsInB];

		if ( columnsInB == rowsInA) {
			
	       for (int i = 0; i < rowsInA; i++) {
	           for (int j = 0; j < columnsInB; j++) {
	               for (int k = 0; k < columnsInA; k++) {
	                   c[i][j] = c[i][j] + a[i][k] * b[k][j];
	               }
	           }
	       }
		} else {
			throw new Exception("Invalid Transformation2D");
		}

		return c;
	}
}