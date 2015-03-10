package mx.unam.ciencias.cv.utils;

/*
 * This file is part of visual-cosmic-rainbown
 *
 * Copyright 2015-2016 Jonathan Andrade 
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

import java.util.LinkedList;
import java.util.TreeMap;

public class Histogram implements java.io.Serializable {
	
	class Tuple<X, Y> { 
	  public final X x; 
	  public final Y y; 
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	}

	double [] table;

	double maxValue;
	double minValue;
	double sumValues;

	int members;

	int minRow;
	int maxRow;
	int nRows;


	public Histogram (int rows) {
		table =  new double[rows];
		maxValue = minValue = sumValues =  members = 0;
		/* Fill with extreme negative numbers*/
		for (int i = 0; i < table.length; table[i++] = Double.MIN_VALUE);
	}

	public void add(int row, double value) {
		if (row < 0 || row >= table.length)
			return;
		
		boolean wasEmpty = (table[row] == Double.MIN_VALUE);

		table[row] += value;
		members++;
		sumValues += row * value;
		/* Update the max and min values */ 
		maxValue = (maxValue < table[row]) ? table[row] : maxValue;
		minValue = (minValue > table[row] && table[row] != Double.MIN_VALUE) ? table[row] : minValue;
		/* Update the rows */ 
		maxRow = (maxRow < row) ? row :  maxRow;
		minRow = (minRow > row) ? row :  minRow;
		nRows = (wasEmpty) ? nRows + 1 : nRows;
	}

	public void add(int row) {
		add(row, 1);
	}


	public double getMeanValue() {
		return sumValues / members;
	}

	public double getVariance() {
		double mu = getMeanValue();
		double sigma  = 0;
		for (int i = 0; i < table.length; i++) 
			sigma += Math.pow(table[i] - mu, 2);
		return sigma/members;
	}

	public double getStdDeviation() {
		return Math.sqrt(getVariance()/members);
	}

	public double getMinValue() {
		return minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public int getMinColor() {
		int i = 0;
		for (; i < table.length && table[i] == 0 ; i++);
		return i;
	}

	public int getMaxColor() {
		int i = table.length - 1;
		for (; i >= 0 && table[i] == 0 ; i--);
		return i;
	}

	/* Return a tuple with a List of values */
	public TreeMap<Integer, Integer> getCDF() {
		/* how many rows are not empty */
		TreeMap<Integer, Integer> cdf = new TreeMap<Integer, Integer>();
		int total = 0;
		for (int i = 0 ,j = 0; i < table.length; i++)
		 	if (table[i] > Double.MIN_VALUE) {
		 		total+=table[i];
		 		cdf.put(i, total);
		 	} 
		 	
		
		return cdf; 
	}


}



