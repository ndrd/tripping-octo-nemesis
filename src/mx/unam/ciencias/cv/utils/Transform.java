package mx.unam.ciencias.cv.utils;

public class Transform {

	public static Transformation2D translate (int deltaX, int deltaY) {
		double [][] matrix = { 
							{1, 0, deltaX},
                       	   	{0, 1, deltaY},
                       	   	{0, 0, 1}
                       	   };
		return new Transformation2D(new Matrix(matrix));
	}

}
