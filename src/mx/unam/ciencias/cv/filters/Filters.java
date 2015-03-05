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
	private double percentage;
	private boolean ready;
	private final static double FACTOR = 1/3;

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

	public static BufferedImage mixChannels(BufferedImage img) {
		FastImage in = new FastImage(img);
		FastImage out =  new FastImage(img.getWidth(), img.getHeight(), img.getType());

		for (int x = 0; x < img.getWidth() ; x++ ) {
			for (int y = 0; y < img.getHeight() ;y++ ) {
				short [] rgb = in.getPixel(x,y);
				short r = rgb[0];
				short g = rgb[2];
				rgb[0] = rgb[1];
				rgb[1] = g;
				rgb[2] = r;
				out.setPixel(x,y,rgb);
			}
		}
		return out.getImage();
	}

    public static BufferedImage grayScale(BufferedImage img) {
      int width = img.getWidth();
      int height = img.getHeight();
      
      FastImage in = new FastImage(img);
      FastImage out = new FastImage(width, height, img.getType());
      short[] rgb = new short[3];
      for(int y = 0; y < height; y++) {
          for(int x= 0; x < width; x++) {
          	rgb = in.getPixel(x,y);
             short prom = (short)((rgb[0] + rgb[1] +  rgb[2]) * 0.333333);
             rgb[0] = rgb[1] = rgb[2] = prom;
             out.setPixel(x, y,rgb);
          }
      }
      return out.getImage();
    }

	public static BufferedImage colorSelector(BufferedImage src, Color lowerBound, Color upperBound) {
		FastImage in = new FastImage(src);
		int width = src.getWidth();
		int height = src.getHeight();
		FastImage out = new FastImage(width, height, src.getType());
		short [] rgb =  new short[3];
	    for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rgb = in.getPixel(x, y);
                
                if (rgb[0] >= lowerBound.getRed() && rgb[0] <= upperBound.getRed()) {
                	if (rgb[1] >= lowerBound.getGreen() && rgb[1] <= upperBound.getGreen()) {
                		if (rgb[2] >= lowerBound.getBlue() && rgb[2] <= upperBound.getBlue()) {
                			/* seems legit */
                		}
                	}
                } else {
                	short media = (short)((rgb[0] + rgb[1] + rgb[2])/3);
                	rgb[0] = rgb[1] = rgb[2] = media;
                }
               	out.setPixel(x, y, rgb);
            }
        }
		return out.getImage();
	}

	public static BufferedImage incrementChanelColor(BufferedImage img, int deltaR, int deltaG, int deltaB) {
		int width = img.getWidth();
		int height =  img.getWidth();
		FastImage in =  new FastImage(img);
		FastImage out = new FastImage(width, height, img.getType());
		
		for (int x = 0; x < width ; x++ ) {
			for (int y = 0; y < height ; y++ ) {
				short rgb[] = in.getPixel(x,y);
				rgb[0] = (short)(((rgb[0] + deltaR) > 255) ? 255 : ((rgb[0] + deltaR) < 0) ? 0 : rgb[0] + deltaR); 
				rgb[1] = (short)(((rgb[1] + deltaR) > 255) ? 255 : ((rgb[1] + deltaR) < 0) ? 0 : rgb[1] + deltaR); 
				rgb[2] = (short)(((rgb[2] + deltaR) > 255) ? 255 : ((rgb[2] + deltaR) < 0) ? 0 : rgb[2] + deltaR); 
			}
		}

		return out.getImage();
	}

	public static BufferedImage blending(BufferedImage imgA, BufferedImage imgB, int percentage) {
		/* Intersect images */
		int width = (imgA.getWidth() < imgB.getWidth()) ? imgA.getWidth() : imgB.getWidth();
        int height = (imgA.getHeight() < imgB.getHeight()) ? imgA.getHeight() : imgB.getHeight();
        double alfa = (percentage < 0 || percentage > 100) ? 50 : percentage / 100;
        double beta = 1 - alfa;
        
        FastImage a = new FastImage(imgA);
        FastImage b = new FastImage(imgB);
        FastImage out = new FastImage(width, height, imgA.getType());
        
        short[] rgbA = new short[3];
        short[] rgbB = new short[3];
        
        for (int x = 0; x < width ; x++ ) {
        	for (int y = 0; y < height ; y++ ) {
        		rgbA = a.getPixel(x,y);
        		rgbB = b.getPixel(x,y);
        		rgbA[0] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
        		rgbA[1] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
        		rgbA[2] = (short)(rgbA[0] * alfa + rgbB[0]  * beta);
        		out.setPixel(x,y,rgbA);
        	}
        }
        return out.getImage();
	}

	public static void print(Object o) {
		System.out.println(o);
	}

	public static BufferedImage histogramEqualization(BufferedImage img) {
		final double oDelta = 255;
		ImageD imageDetails = new ImageD(img);

		int minR = imageDetails.getRedHistogram().getMinColor();
		int minG = imageDetails.getGreenHistogram().getMinColor();
		int minB = imageDetails.getBlueHistogram().getMinColor();

		int maxR = imageDetails.getRedHistogram().getMaxColor();
		int maxG = imageDetails.getGreenHistogram().getMaxColor();
		int maxB = imageDetails.getBlueHistogram().getMaxColor();

		double ratioR = oDelta / (maxR - minR);
		double ratioG = oDelta / (maxG - minG);
		double ratioB = oDelta / (maxB - minB);

		short rgb[] =  new short[3];

		for (int x = 0; x < img.getWidth() ; x++ ) {
			for (int y = 0; y < img.getHeight(); y++ ) {
				rgb = imageDetails.getPixel(x,y);
				rgb[0] = (short)((rgb[0] - minR) * ratioR);		
				rgb[1] = (short)((rgb[1] - minG) * ratioG);		
				rgb[2] = (short)((rgb[2] - minB) * ratioB);	
				imageDetails.setPixel(x,y,rgb);	
			}
		}

		return imageDetails.getImage();
	}

	public static BufferedImage gaussianBlur(BufferedImage img, int sigma) {
		FastImage in =  new FastImage(img);
		FastImage out =  new FastImage(img.getWidth(), img.getHeight(), img.getType());
		int width = img.getWidth();
		int height = img.getHeight();

		double[] kernel =  kernel(sigma);
		int ssigma = 2 * sigma;

		short[] rgb =  new short[3];
		short[] rgbAux =  new short[3];

		float red = 0;
		float green = 0;
		float blue = 0;

		for (int y = 0; y < height; y++ ) {
			for (int x = 0; x < width; x++ ) {
				rgb = in.getPixel(x,y);	

				for (int i = -sigma, n = 0; i < ((ssigma + 1) -  sigma); i++, n++) {
					for (int j = -sigma, m = 0; j < ((ssigma + 1) - sigma); j++, m++ ) {

						if (x+i < width && y+j < height && x+i >= 0 && y+j >= 0)
							rgbAux =  in.getPixel(x+i, y+j);
						else if((x+i < 0 || y+j < 0 )&& ( x+i < width && y+j < height))
                            rgbAux = in.getPixel(Math.abs(x+i),Math.abs(y+j));

                        red += (rgbAux[0] * kernel[n] * kernel[m]);
                        green += (rgbAux[1] * kernel[n] * kernel[m]);
                        blue += (rgbAux[2] * kernel[n] * kernel[m]);
					}
				}

				rgb[0] = (short)Math.round(red);
				rgb[1] = (short)Math.round(green);
				rgb[2] = (short)Math.round(blue);

				out.setPixel(x,y, rgb);
			}
		}

		return out.getImage();
	}

	private static boolean pxInRange(int width, int height, int x, int y)
     { 
         return (x < width && y < height && x >= 0 && y >= 0);
     }

	public static BufferedImage gaussianBlur2(BufferedImage img, int sigma) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage wimg = new BufferedImage(width,height, img.getType());

        int ssigma = 2 * sigma;
        double [] kernel = kernel(sigma);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int pixel = img.getRGB(x,y);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {
                    for(int j = -sigma, m = 0; j < ((ssigma + 1) - sigma); j++, m++) {
                        int px = 0;

                        if(pxInRange(width, height, x+i, y+j))
                            px = img.getRGB(x+i,y+j);
                        else if((x+i < 0 || y+j < 0 )&& ( x+i < width && y+j < height))
                            px = img.getRGB(Math.abs(x+i),Math.abs(y+j));

                        red += ((px >> 16) & 0x000000FF) * kernel[n] * kernel[m];
                        green += ((px >> 8) & 0x000000FF) * kernel[n] * kernel[m];
                        blue += ((px) & 0x000000FF) * kernel[n] * kernel[m];

                    }
                }

                pixel = (pixel & ~(0x000000FF << 16)) | ((int) (red) << 16);
                pixel = (pixel & ~(0x000000FF << 8)) | ((int) (green) << 8);
                pixel = (pixel & ~(0x000000FF )) | ((int)(blue));

                wimg.setRGB(x,y,pixel);
            }
        }
        return wimg;
    }

    public static BufferedImage gaussianBlur3(BufferedImage img, int sigma) {
        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage wimg = new BufferedImage(width,height, img.getType());
        BufferedImage wimg2 = new BufferedImage(width,height, img.getType());

        int ssigma = 2 * sigma;
        double [] kernel = kernel(sigma);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int pixel = img.getRGB(x,y);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {
                    int px = 0;

                    if(pxInRange(width, height, x+i, y))
                        px = img.getRGB(x+i,y);
                    else if(x+i < 0 )
                        px = img.getRGB(Math.abs(x+i), Math.abs(y+i));
                    else if(x+i > width )
                        px = img.getRGB((x-i), Math.abs(y-i));

                    red += ((px >> 16) & 0x000000FF) * kernel[n];
                    green += ((px >> 8) & 0x000000FF) * kernel[n];
                    blue += ((px) & 0x000000FF) * kernel[n];
               }

                pixel = (pixel & ~(0x000000FF << 16)) | ((int) (red) << 16);
                pixel = (pixel & ~(0x000000FF << 8)) | ((int) (green) << 8);
                pixel = (pixel & ~(0x000000FF )) | ((int)(blue));

                wimg.setRGB(x,y,pixel);
            }
        }

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int pixel = wimg.getRGB(x,y);
                double red = 0, green = 0, blue = 0;

                for(int i = -sigma, n = 0; i < ((ssigma + 1) - sigma); i++, n++) {
                    int px = 0;

                    if(pxInRange(width, height, x, y+i))
                        px = wimg.getRGB(x,y+i);
                    else if(y+i < 0 )
                        px = wimg.getRGB(Math.abs(x),Math.abs(y+i));
                    else if(y+i > height )
                        px = wimg.getRGB(Math.abs(x-i),y-i);

                    red += ((px >> 16) & 0x000000FF) * kernel[n];
                    green += ((px >> 8) & 0x000000FF) * kernel[n];
                    blue += ((px) & 0x000000FF) * kernel[n];
               }

                pixel = (pixel & ~(0x000000FF << 16)) | ((int) (red) << 16);
                pixel = (pixel & ~(0x000000FF << 8)) | ((int) (green) << 8);
                pixel = (pixel & ~(0x000000FF )) | ((int)(blue));

                wimg2.setRGB(x,y,pixel);
            }
        }
        return wimg2;
    }

	/** Calculates a linear dimention kernel for the gaussian blur */
	public static double[] kernel(int sigma) {

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

	private static void printArray(double[] a) {
		for (int i = 0;i < a.length ; i++) {
			System.out.print(" " + a[i]);
		}
	}
	private static void printArray(float[] a) {
		for (int i = 0;i < a.length ; i++) {
			System.out.print(" " + a[i]);
		}
	}




}