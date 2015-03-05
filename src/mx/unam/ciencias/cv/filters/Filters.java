package mx.unam.ciencias.cv.filters;
/*
 * This file is part of tom
 *
 * Copyright Jonathan Andrade 2015
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

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.awt.Color;
import mx.unam.ciencias.cv.utils.ImageD;
import mx.unam.ciencias.cv.utils.FastImage;

public class Filters {

	private static Filters instance;
	private LinkedList<BufferedImage> images;
	private BufferedImage lastWork;
	private boolean ready;

	protected Filters () {
		images = new LinkedList<BufferedImage>();
		percentage = 0;
		ready = false;
	}

	public static Filters getInstance() {
		if (instance == null)
			instance = new Filters();
		return instance;
	}

	public void addImage(BufferedImage img) {
		images.add(img);
		ready = true;
	}

	public BufferedImage getLastImage() {
		if ( images.size() > 0) 
			return images.getLast();
		return null;
	}

	public BufferedImage getLastWork() {
		return lastWork;
	}

	public void setLastWork(BufferedImage img) {
		lastWork = img;
	}

}