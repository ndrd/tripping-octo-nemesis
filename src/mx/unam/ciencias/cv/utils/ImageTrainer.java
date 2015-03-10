package mx.unam.ciencias.cv.utils;

/*
 * This file is part of visual-cosmic-rainbown
 *
 * Copyright Jonathan Andrade 2015
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

/**
* Use to get the mean Value of a set of training and the values of sigma
* to categorize a new speciment
* (Ougth to be Generic)
*/
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;

import mx.unam.ciencias.cv.utils.*;

public class ImageTrainer implements java.io.Serializable {

	/* Must be a folder */
	private File folderTraining;
	private LinkedList<ImageD> specimens;
	private final int CATEGORIES = 255;
	private Histogram red;
	private Histogram green;
	private Histogram blue;

	private Color meanColor;
	private Color lowerBound;
	private Color upperBound;

	private	double percentaje;
	private static ImageTrainer instance;


	private ImageTrainer () {
		specimens = new LinkedList<ImageD>();	
	}

	public static ImageTrainer getInstance() {
		if (instance == null)
			instance = new ImageTrainer();
		return instance;
	}

	public Color getMeanColor() {
		return meanColor;
	}

	public Color getLowerBound() {
		return lowerBound;
	}

	public Color getUpperBound() {
		return upperBound;
	}

	public void trainingFromDir(String path) {
		folderTraining = new File(path);
		File[] files = folderTraining.listFiles();
		/* read and list images to manipulate*/
		double i = 0;
		percentaje = 0;
		for(File f : files) {
			percentaje = ++i/(files.length + 0.0);
			BufferedImage specimen =  null;
			try {
				 specimen = ImageIO.read(f);
			} catch (Exception  e ) {}

            if (specimen != null) {
             	specimens.add(new ImageD(specimen));
            }
		}

		/* Now work with the meanColors of each image */
		red =  new Histogram(CATEGORIES);
		green =  new Histogram(CATEGORIES);
		blue =  new Histogram(CATEGORIES);

		for (ImageD specimen : specimens) {
			int [] averageRGB = specimen.getMeanColor();
			
			red.add(averageRGB[0]);
			green.add(averageRGB[1]);
			blue.add(averageRGB[2]);
		}

		double r = red.getMeanValue();
		double g = green.getMeanValue();
		double b = blue.getMeanValue();

		meanColor = new Color((int)r, (int)g, (int)b);

		double rs = red.getStdDeviation();
		double gs = green.getStdDeviation();
		double bs = blue.getStdDeviation();

		System.out.println("meanColor " + meanColor);
		System.out.println("Sigma RGB (" + rs + ", " + gs + ", " + bs );

		lowerBound = new Color((int)(r-rs),(int)(g-gs),(int)(b-bs));
		upperBound = new Color((int)(r+rs),(int)(g+gs),(int)(b+bs));

	}

	public double getProgress() {
		return (int)(percentaje * 100);
	}



}