package mx.unam.ciencias.cv.filters;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.awt.Color;

public class Filters {

	private static Filters instance;
	private LinkedList<BufferedImage> images;
	private double percentage;
	private boolean ready;

	private Filters () {
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

	public static BufferedImage grayScale(BufferedImage src) {
		WritableRaster rIn = src.getRaster();
		int width = rIn.getWidth();
		int height = rIn.getHeight();
		int type = src.getType();

		BufferedImage r = new BufferedImage(width, height, type);
        WritableRaster rOut = r.getRaster();

        double[] rgb = new double[3];
	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rIn.getPixel(x, y, rgb);
                double media = (rgb[0]  + rgb[1] + rgb[2])/3;
                rgb[0] = rgb[1] = rgb[2] =  media;
               	rOut.setPixel(x, y, rgb);
            }
        }
		
		return r;
	}

	public static BufferedImage colorSelector(BufferedImage src, Color lowerBound, Color upperBound) {
		WritableRaster rIn = src.getRaster();
		int width = rIn.getWidth();
		int height = rIn.getHeight();
		int type = src.getType();

		BufferedImage r = new BufferedImage(width, height, type);
        WritableRaster rOut = r.getRaster();

        double[] rgb = new double[3];
	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rIn.getPixel(x, y, rgb);
                
                if (rgb[0] >= lowerBound.getRed() && rgb[0] <= upperBound.getRed()) {
                	if (rgb[1] >= lowerBound.getGreen() && rgb[1] <= upperBound.getGreen()) {
                		if (rgb[2] >= lowerBound.getBlue() && rgb[2] <= upperBound.getBlue()) {
                		}
                	}
                } else {
                	double media = (rgb[0] + rgb[1] + rgb[2]) * 0.33;
                	rgb[0] = rgb[1] = rgb[2] = media;
                }
                
               	rOut.setPixel(x, y, rgb);
            }
        }
		
		return r;
	}
}