package mx.unam.ciencias.cv.utils;

public class Transformation2D{
	private int [][] matrix;

	public Transformation2D(int [][] matrix) {
		this.matrix = matrix;
	}

	public void transformPoints(int []x, int []y) throws Exception {
		for (int i = 0;i < x.length ;i++ ) {
			int [][] newPoint = transformPoint(x[i], y[i]);
			System.out.println("(" + newPoint[0][1] + ", " + newPoint[0][1] + " )");
		}
	}

	public int[][] transformPoint(int x, int y) throws Exception{
		int [][] hCoords = {{x, y, 1}};
		int[][] newPoint = multiplicationMatrix(matrix, hCoords);
		System.out.println("Response" + newPoint.length);
		return newPoint;
	}

	private int[][] multiplicationMatrix(int[][] a, int [][] b) throws Exception {
		int x1 = a.length;
		int x2 = b.length;
		int y1 = a[0].length;
		int y2 = b[0].length;
		int [][] prod = new int [x1][y2];

		if ( x1 == y2) {
			for (int i = 0; i < x1 ;i++ ) {
				for (int j = 0;j <  y2; j++) {
					for (int k =0;k < y1; k++) {
						prod[i][j] += a[i][k] * b[k][j];
					}
				}
			}
		} else {
			throw new Exception("Invalid Transformation2D");
		}

		return prod;
	}
}