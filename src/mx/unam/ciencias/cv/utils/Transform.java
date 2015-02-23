package mx.unam.ciencias.cv.utils;
/**
* Deals with the transformation matrix, takes the params to
* make an apropiete matrix
*/
public class Transform {

	/** 
	* Translation matrix
	* takes a delta in (x,y) an build a new matrix with the parameters
	* returns a @link {Transformation2D} ready to use.
	*/
	public static Transformation2D translate (double deltaX, double deltaY) {
		double [][] matrix = { 
							{1, 0, deltaX},
                       	   	{0, 1, deltaY},
                       	   	{0, 0, 1}
                       	   };
		return new Transformation2D(new Matrix(matrix));
	}

	public static Transformation2D scale(double xFactor, double yFactor) {
		double [][] matrix = { 
							{xFactor, 	0, 		0},
                       	   	{0,		 yFactor,	 0},
                       	   	{0,			 0,		 1}
                       	   };
		return new Transformation2D(new Matrix(matrix));
	}

	public static Transformation2D rotate(double tetha) {
		double [][] matrix = { 
							{ Math.cos(tetha), Math.sin(tetha), 0},
                       	   	{ -Math.sin(tetha), Math.cos(tetha), 0},
                       	   	{ 0,				 0, 			1}
                       	   };
		return new Transformation2D(new Matrix(matrix));
	}

	public static Transformation2D shear(double deltaX, double deltaY) {
		double [][] matrix = { 
							{1, 	deltaX, 	0},
                       	   	{deltaY,	 1,		0},
                       	   	{0, 		 0, 	1}
                       	   };
		return new Transformation2D(new Matrix(matrix));
	}



}
