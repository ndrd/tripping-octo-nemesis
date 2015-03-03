package mx.unam.ciencias.cv.utils;

/*
 * This file is part of tom
 *
 * Copyright 2015-2016 Jonathan Andrade 
 *
 * tom is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * tom is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with tom. If not, see <http://www.gnu.org/licenses/>.
 */

public class Histogram implements java.io.Serializable {
	double [] table;
	double maxValue;
	double minValue;
	double sumValues;
	int members;

	public Histogram (int rows) {
		table =  new double[rows];
		maxValue = minValue = sumValues =  members = 0;
	}

	public void add(int row) {
		if (row < 0 || row >= table.length)
			return;
		table[row]++;
		members++;
		sumValues += row;
		/* Update the max and min values */ 
		if (maxValue < table[row])
			maxValue = table[row];
		else if (minValue > table[row])
			minValue = table[row];
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


}
