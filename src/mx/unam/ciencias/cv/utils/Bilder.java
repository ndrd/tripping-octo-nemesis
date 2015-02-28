package mx.unam.ciencias.cv.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;


/**
* trying to speed up pixels read using byte arrays
*/
public class Bilder {

	private byte[] pixels;
	private int width;
	private int height;
	private boolean hasAlfa;
	private final int ALFA_RED_SHIFT = 3;
	private final int ALFA_GREEN_SHIFT = 2;
	private final int ALFA_BLUE_SHIFT = 1;
	private final int WITH_ALFA = 4;
	private final int NO_ALFA = 3;


	public Bilder (BufferedImage img) {
		width = img.getWidth();
		height = img.getHeight();
		hasAlfa = img.getAlphaRaster() != null;
		pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
	}

	public Bilder (int width, int height, boolean hasAlfa) {
		this.width = width;
		this.height = height;
		this.hasAlfa = hasAlfa;
		int pxLength = (hasAlfa) ? WITH_ALFA : NO_ALFA;
		this.pixels = new byte[width * height * pxLength];

		for (int i = 0; i < pixels.length ; i++ )
			pixels[i] = 0;

	}

	public int getPixel(int x, int y) throws IllegalArgumentException{
		if (x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");
		int argb = 0;
		int index = (y * width + x);

		if (hasAlfa) {
			index *= WITH_ALFA;
			argb += (((int) pixels[index] &  0x000000FF) << 24); // alpha
            argb += ((int) pixels[index + 1] & 0x000000FF); // blue
            argb += (((int) pixels[index + 2] & 0x000000FF) << 8); // green
            argb += (((int) pixels[index + 3] & 0x000000FF) << 16); // red
		} else {
           	argb += -16777216; // 255 alpha
           	index *= NO_ALFA;
            argb += ((int) pixels[index] & 0x000000FF); // blue
            argb += (((int) pixels[index + 1] & 0x000000FF) << 8); // green
            argb += (((int) pixels[index + 2] & 0x000000FF) << 16); // red
		}
		return argb;
	}

	public int getRed(int x, int y) {
		if (hasAlfa)
			return getColor(x, y,ALFA_RED_SHIFT);
		return getColor(x, y, ALFA_RED_SHIFT - 1);
	}

	public int getGreen(int x, int y) {
		if (hasAlfa)
			return getColor(x, y,ALFA_GREEN_SHIFT);
		return getColor(x, y, ALFA_GREEN_SHIFT - 1);
	}

	public int getBlue(int x, int y) {
		if (hasAlfa)
			return getColor(x, y,ALFA_BLUE_SHIFT);
		return getColor(x, y, ALFA_BLUE_SHIFT - 1);
	}

	public int[] getRGB(int x, int y) {
		int [] rgb = new int[3];
		if ( x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");
		int index = (y * width + x);
		if (hasAlfa) {
			rgb[0] = pixels[index + ALFA_RED_SHIFT];
			rgb[1] = pixels[index + ALFA_GREEN_SHIFT];
			rgb[2] = pixels[index + ALFA_BLUE_SHIFT];
		} else {
			rgb[0] = pixels[index + ALFA_RED_SHIFT-1];
			rgb[1] = pixels[index + ALFA_GREEN_SHIFT-1];
			rgb[2] = pixels[index + ALFA_BLUE_SHIFT-1];
		}
		return rgb;
	}

	public void setRGB(int x, int y, int[] rgb) {
		if (x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");
		int index = (y * width + x);
		if (hasAlfa) {
			pixels[index + ALFA_RED_SHIFT] = (byte) rgb[0];
			pixels[index + ALFA_GREEN_SHIFT] = (byte) rgb[1];
			pixels[index + ALFA_BLUE_SHIFT] = (byte) rgb[2];
		} else {
			pixels[index + ALFA_RED_SHIFT-1] = (byte) rgb[0];
			pixels[index + ALFA_GREEN_SHIFT-1] = (byte) rgb[1];
			pixels[index + ALFA_BLUE_SHIFT-1] = (byte) rgb[2];
		}
	}

	protected byte getColor(int x, int y, int i) throws IllegalArgumentException {
		if (i < 0 || i > 3 || x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");
		int index = (y * width + x);
		return pixels[index + i];
	}

	protected byte setColor(int x, int y, int i, int color) throws IllegalArgumentException {
		if (i < 0 || i > 3 || x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");
		int index = (y * width + x);
		return pixels[index + i] = (byte) color;
	}

	public void setRed(int x, int y, int color) {
		color  = (color < 0 || color > 255) ? 0 : color;
		if(hasAlfa)
			setColor(x,y,ALFA_RED_SHIFT, color);
		else
			setColor(x,y,ALFA_RED_SHIFT-1, color);
	}

	public void setGreen(int x, int y, int color) {
		color  = (color < 0 || color > 255) ? 0 : color;
		if(hasAlfa)
			setColor(x,y,ALFA_GREEN_SHIFT, color);
		else
			setColor(x,y,ALFA_GREEN_SHIFT-1, color);
	}

	public void setBlue(int x, int y, int color) {
		color  = (color < 0 || color > 255) ? 0 : color;
		if(hasAlfa)
			setColor(x,y,ALFA_BLUE_SHIFT, color);
		else
			setColor(x,y,ALFA_BLUE_SHIFT-1, color);
	}

	public void setPixel(int x, int y, int pixel) throws IllegalArgumentException{
		if (x < 0 || x > width || y  < 0 || y > height)
			throw new IllegalArgumentException("Invalid Coordinates");
		int index = (y * width + x);

		int alfa = (pixel >> 24) & 0x000000FF;
        int red = (pixel >> 16) & 0x000000FF;
        int green = (pixel >> 8 ) & 0x000000FF;
        int blue = (pixel) & 0x000000FF;

		if (hasAlfa) {
			index *= WITH_ALFA;
			pixels[index] = (byte)  alfa;
            pixels[index + 1] = (byte) blue;
            pixels[index + 2] = (byte) green;
            pixels[index + 3] = (byte) red;
		} else {
           	index *= NO_ALFA;
            pixels[index] = (byte) blue;
            pixels[index + 1] =  (byte) green;
            pixels[index + 2] = (byte) blue;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasAlfa() {
		return hasAlfa;
	}

	public static BufferedImage toBIF(Bilder b) throws IOException {
	    ByteArrayInputStream bais = new ByteArrayInputStream(b.pixels);
	    return ImageIO.read(bais);
	}
}