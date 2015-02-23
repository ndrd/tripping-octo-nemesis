package mx.unam.ciencias.cv.utils;

public class Transformation2D{
	private int [][] matrix;

	public Transformation2D(int [][] matrix) {
		this.matrix = matrix;
	}

	public int [][] transformPoints(int []x, int []y) throws Exception {
		int [][] points = new int[2][x.length];

		for (int i = 0; i < x.length ;i++ ) {
			System.out.println("(" + x[i] + " , " + y[i] + ")");
		}

		for (int i = 0;i < x.length ;i++ ) {
			int [][] newPoint = transformPoint(x[i], y[i]);
			points[0][i] =  newPoint[0][0];
			points[1][i] = newPoint[0][1];
		}

		for (int i = 0; i < points[0].length ;i++ ) {
			System.out.println("(" + points[0][i] + " , " + points[1][i] + ")'");
		}
		

		return points;
	}

	public int[][] transformPoint(int x, int y) throws Exception{
		int [][] hCoords = {{x},{y},{1}};
		int[][] newPoint = multiplicationMatrix(hCoords, matrix);
		for (int i = 0; i< newPoint.length ;i++ ) {
			for (int j = 0; j < newPoint[0].length ;j++ ) {
				System.out.print(newPoint[i][j] + ", ");
			}
			System.out.println();
		}
		return newPoint;
	}

	protected int[][] multiplicationMatrix(int[][] a, int [][] b) throws Exception {

		for (int i =0;i < a.length; i++ ) {
			for (int j = 0;j < a[0].length ;j++ ) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();


		for (int i =0;i < b.length ;i++ ) {
			for (int j = 0;j < b[0].length ;j++ ) {
				System.out.print(b[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();


	   int rowsInA = a.length;
       int columnsInA = a[0].length; // same as rows in B
       int columnsInB = b[0].length;
       int[][] c = new int[rowsInA][columnsInB];

		if ( columnsInB == rowsInA) {
			
	       for (int i = 0; i < rowsInA; i++) {
	           for (int j = 0; j < columnsInB; j++) {
	               for (int k = 0; k < columnsInA; k++) {
	                   c[i][j] += a[i][k] * b[k][j];
	               }
	           }
	       }
		} else {
			throw new Exception("Invalid Transformation2D");
		}

		return c;
	}
}