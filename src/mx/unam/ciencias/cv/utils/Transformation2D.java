package mx.unam.ciencias.cv.utils;

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