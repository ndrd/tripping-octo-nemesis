package mx.unam.ciencias.cv.core.filters;


import mx.unam.ciencias.cv.utils.models.*;

public class Kernel {

	public static Matrix SOBEL_HORIZONTAL() {
		int [][] kernel = {
							{-1, 0, 1},
							{-2, 0, 2},
							{-1, 0, 1 }};
		return new Matrix(kernel);
	}

	public static Matrix SOBEL_VERTICAL() {
		int [][] kernel = {
							{-1, -2, -1},
							{ 0,  0,  0},
							{ 1,  2,  1}};
		return new Matrix(kernel);
	}
}