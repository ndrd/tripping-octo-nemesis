package mx.unam.ciencias.cv.utils;

import java.awt.image.*;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

public class FastImage {

	private BufferedImage original;
	private byte [] pixels;
	private int width;
	private int height;

	public FastImage(BufferedImage img) {
		original =  img;
		width = img.getWidth();
		height =  img.getHeight();
		pixels =  ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		System.out.println("pixels.lenght " + pixels.length + " w: " + width + " h:" + height);

	}

	public FastImage(int width, int height, int type ) {
		this.width = width;
		this.height =  height;
		original = new BufferedImage(width, height, type);
		pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
	}

	public byte[] getPixel(int x, int y) {
		if ( x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");

		byte [] rgb = new byte[3];
		int index = (y * width + x);

		rgb[0] = pixels[3*index + 2];
		rgb[1] = pixels[3*index + 1];
		rgb[2] = pixels[3*index + 0];
		
		return rgb;
	}

	public void setPixel(int x, int y, byte [] rgb) {
		if ( x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");

		int index = (y * width  + x);
		pixels[3*index + 2] = rgb[0];
		pixels[3*index + 1] = rgb[1];
		pixels[3*index + 0] = rgb[2];
	}


	public void mixArray() {
		for (int x = 0 ;x < width; x++ ) {
			for (int y = 0; y < height; y++ ) {
				byte [] rgb = getPixel(x,y);
				byte r = rgb[0];
				byte g = rgb[2];
				rgb[0] = rgb[1];
				rgb[1] = g;
				rgb[2] = r;
				setPixel(x,y,rgb);
			}
		}
	}

	public BufferedImage getImage() {
		return original;
	}

	private void buildXYarray() {
		int [][]  result =  new int[height][width];
		 final int pixelLength = 3;
         for (int pixel = 0, x = 0, y = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            result[x][y] = argb;
            y++;
            if (y == width) {
               y = 0;
               x++;
            }
        }

	}
}