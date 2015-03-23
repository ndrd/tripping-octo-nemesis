package mx.unam.ciencias.cv.core.filters;
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

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import mx.unam.ciencias.cv.utils.models.FastImage;


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
        return gaussianBlur4(img, sigma);
    }

    public static BufferedImage gaussianBlur4(BufferedImage img, int sigma) {

        setName("GaussianBlur with Raster");

        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage wimg = new BufferedImage(width,height, img.getType());
        BufferedImage wimg2 = new BufferedImage(width,height, img.getType());

        WritableRaster src = img.getRaster();
        WritableRaster in = wimg.getRaster();
        WritableRaster out = wimg2.getRaster();
        double rgb[] = new double[3];
        double srgb[] = new double[3];


        int ssigma = 2 * sigma;
        double [] kernel = kernel(sigma);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                rgb = src.getPixel(x,y, rgb);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {
                    if(pxInRange(width, height, x+i, y))
                        srgb = src.getPixel(x+i,y,srgb);
                    else if(x+i < 0 )
                        srgb = src.getPixel(Math.abs(x+i), Math.abs(y+i),srgb);
                    else if(x+i > width )
                        srgb = src.getPixel((x-i), Math.abs(y-i),srgb);

                    red += srgb[0] * kernel[n];
                    green += srgb[1] * kernel[n];
                    blue += srgb[2] * kernel[n];
               }

                rgb[0] = red;
                rgb[1] = green;
                rgb[2] = blue;

                in.setPixel(x,y,rgb);
            }
        }

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                rgb  = in.getPixel(x,y,rgb);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {

                    if(pxInRange(width, height, x, y+i))
                        srgb = in.getPixel(x,y+i,srgb);
                    else if(y+i < 0 )
                        srgb = in.getPixel(Math.abs(x),Math.abs(y+i),srgb);
                    else if(y+i > height )
                        srgb = in.getPixel(Math.abs(x-i),y-i,srgb);

                    red += srgb[0] * kernel[n];
                    green += srgb[1] * kernel[n];
                    blue += srgb[2] * kernel[n];
               }

                rgb[0] = red;
                rgb[1] = green;
                rgb[2] = blue;

                out.setPixel(x,y,rgb);
            }
        }
        return wimg2;
    }

    public static BufferedImage gaussianBlur1(BufferedImage img, int sigma) {
        return gaussianVertical(gaussianHorizontal(img, sigma), sigma);
    }

    public static BufferedImage gaussianVertical(BufferedImage img, int sigma) {
        
        int ssigma = 2 * sigma;
        double [] kernel = kernel(sigma);

        int height = img.getHeight();
        int width = img.getWidth();

        FastImage in = new FastImage(img);
        FastImage out = new FastImage(width, height, img.getType());

        short[] rgb = new short[3];
        int[] srgb = new int[3];

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                rgb  = in.getPixel(x,y);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {

                    if(pxInRange(width, height, x, y+i))
                        srgb = in.getPixeli(x,y+i);
                    else if(y+i < 0 )
                        srgb = in.getPixeli(Math.abs(x),Math.abs(y+i));
                    else if(y+i > height )
                        srgb = in.getPixeli(Math.abs(x-i),y-i);

                    red += (int)(srgb[0] * kernel[n]);
                    green += (int)(srgb[1] * kernel[n]);
                    blue += (int)(srgb[2] * kernel[n]);
               }

                rgb[0] = (short)red;
                rgb[1] = (short)green;
                rgb[2] = (short)blue;

                out.setPixel(x,y,rgb);
            }
        }

        return out.getImage();
    }

    public static BufferedImage gaussianHorizontal(BufferedImage img, int sigma) {
        
        int ssigma = 2 * sigma;
        double [] kernel = kernel(sigma);

        int height = img.getHeight();
        int width = img.getWidth();

        FastImage in = new FastImage(img);
        FastImage out = new FastImage(width, height, img.getType());

        short[] rgb = new short[3];
        int[] srgb = new int[3];

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                rgb  = in.getPixel(x,y);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {
                    
                    if(pxInRange(width, height, x+i, y))
                        srgb = in.getPixeli(x+i,y);
                    else if(x+i < 0 )
                        srgb = in.getPixeli(Math.abs(x+i), Math.abs(y+i));
                    else if(x+i > width )
                        srgb = in.getPixeli((x-i), Math.abs(y-i));;

                    red += (int)(srgb[0] * kernel[n]);
                    green += (int)(srgb[1] * kernel[n]);
                    blue += (int)(srgb[2] * kernel[n]);
               }

                rgb[0] = (short)red;
                rgb[1] = (short)green;
                rgb[2] = (short)blue;

                out.setPixel(x,y,rgb);
            }
        }

        return out.getImage();
    }


    	
}
