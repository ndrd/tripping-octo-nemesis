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

public class GaussianBlur extends ImageFilter {

	/** Calculates a 1D for the gaussian blur */
	private static double[] kernel(int sigma) {

		double[] kernel = new double[2 * sigma + 1];
		double ssigma = 2 * sigma * sigma;
		double K = (1 / Math.sqrt(ssigma * Math.PI));
		double weightSum = 0;

		for (int x = -sigma, i = 0; x < ((2 * sigma + 1) - sigma) ; x++, i++) {
			kernel[i] = K * Math.pow(Math.E, -(x * x / ssigma));
			weightSum += kernel[i];
		}

		for (int i = 0; i < kernel.length; i++ ) 
			kernel[i] = kernel[i] / weightSum;

		return kernel;
	}

	private static boolean pxInRange(int width, int height, int x, int y) { 
         return (x < width && y < height && x >= 0 && y >= 0);
    }

    public static BufferedImage gaussianBlur(BufferedImage img, int sigma) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage wimg = new BufferedImage(width,height, img.getType());
        BufferedImage wimg2 = new BufferedImage(width,height, img.getType());

        double [] kernel = kernel(sigma);

        horizontalBlur(0, 0, width, height, sigma, kernel, img, wimg);

        verticalBlur(0, 0, width, height, sigma, kernel, wimg, wimg2);

        return wimg2;
    }

    public static BufferedImage threatedGaussianBlur(BufferedImage img, int sigma) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage wimg = new BufferedImage(width,height, img.getType());
        BufferedImage wimg2 = new BufferedImage(width,height, img.getType());

        double [] kernel = kernel(sigma);

        /* thread horizontal stage */
        Thread horizontal = new Thread(){
            @Override public void run() {
                horizontalBlur(0, 0, width, height/2, sigma, kernel, img, wimg);
            }
        };
        horizontal.start();
        horizontalBlur(0, height/2, width, height, sigma, kernel, img, wimg);
        
        try {
            horizontal.join();
        } catch (Exception e) {}

        /* threat vetical stage */
        Thread vertical = new Thread(){
            @Override public void run() {
                verticalBlur(0, 0, width/2, height, sigma, kernel, wimg, wimg2);
            }
        };
        vertical.start();
        verticalBlur(width/2, 0, width, height, sigma, kernel, wimg, wimg2);
        
        try {
            vertical.join();
        } catch (Exception e) {}

        return wimg2;
    }

    private static void verticalBlur(int x0, int y0, int w, int h, int sigma, double[] kernel,
    									BufferedImage in, BufferedImage out) {
        double red = 0;
        double green = 0;
        double blue = 0;

        int width =  in.getWidth();
        int height =  in.getHeight();

        WritableRaster rI = in.getRaster();
        WritableRaster rO = out.getRaster();
        double rgb[] = new double[3];
        double srgb[] = new double[3];

    	for (int y = y0; y < h; y++) {
    		for (int x = x0; x < w; x++) { 
                
                rgb = rI.getPixel(x,y, srgb);

    			for (int s = -sigma, i = 0; s < (2 * sigma + 1) - sigma; s++, i++ ) {

                    if(pxInRange(width, height, x, y+s))
                        srgb = rI.getPixel(x,y+s, srgb);
                    else if(y+s < 0 )
                        srgb = rI.getPixel(Math.abs(x),Math.abs(y+s), srgb);
                    else if(y+s > height )
                        srgb = rI.getPixel(Math.abs(x-i),y-i, srgb);

                    red += srgb[0] * kernel[i];
                    green += srgb[1] * kernel[i];
                    blue += srgb[2] * kernel[i];
    			}

                rgb[0] = red;
                rgb[1] = green;
                rgb[2] = blue;
                rO.setPixel(x,y,rgb);
    		}
    	}
	}

	private static void horizontalBlur(int x0, int y0, int w, int h, int sigma, double[] kernel,
    									BufferedImage in, BufferedImage out) {
        double red = 0;
        double green = 0;
        double blue = 0;

        int width =  in.getWidth();
        int height =  in.getHeight();

        WritableRaster rI = in.getRaster();
        WritableRaster rO = out.getRaster();
        double rgb[] = new double[3];
        double srgb[] = new double[3];

    	for (int y = y0; y < h; y++) {
    		for (int x = x0; x < w; x++) {

                rgb = rI.getPixel(x,y, srgb);

    			for (int s = -sigma, i = 0; s < (2 * sigma + 1) - sigma; s++, i++ ) {

                    if(pxInRange(width, height, x+s, y))
                        srgb = rI.getPixel(x,y+s, srgb);
                    else if(y+s < 0 )
                        srgb = rI.getPixel(x,y+s, srgb);
                    else if(y+s > height )
                        srgb = rI.getPixel(x,y+s, srgb);

                    red += srgb[0] * kernel[i];
                    green += srgb[1] * kernel[i];
                    blue += srgb[2] * kernel[i];
    			}
                rgb[0] = red;
                rgb[1] = green;
                rgb[2] = blue;
                rO.setPixel(x,y,rgb);

    		}
    	}
	}

}