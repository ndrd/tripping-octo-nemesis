package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.utils.*;
import mx.unam.ciencias.cv.filters.*;
import org.junit.Assert;
import org.junit.Test;


public class GaussianBlurTest extends FiltersTest {

	public GaussianBlurTest() {
		super();
	}

	public void writableRasterBlur() {
		writeFiles =  true;
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianBlur(img,5);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, "writableRasterBlur" + i);

			}
		    System.out.println(String.format( ITERS  + " iters for writableRasterBlur, total time %s", SpeedTest.timeFormat(total)));
			System.out.println();
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	public void writableRasterBlurThreated() {
		writeFiles =  true;
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.threatedGaussianBlur(img,5);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, "writableRasterBlurThreated" + i);

			}
		    System.out.println(String.format( ITERS  + " iters for writableRasterBlurThreated, total time %s", SpeedTest.timeFormat(total)));
			System.out.println();
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test public void BsBLur3() {
		writeFiles =  true;
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianBlur3(img, 10);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, "BsBLur3" + i);

			}
		    System.out.println(String.format( ITERS  + " iters for BsBLur3, total time %s", SpeedTest.timeFormat(total)));
			System.out.println();
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test public void BsBLur4() {
		writeFiles =  true;
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianBlur4(img, 10);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, "BsBLur4" + i);

			}
		    System.out.println(String.format( ITERS  + " iters for BsBLur4, total time %s", SpeedTest.timeFormat(total)));
			System.out.println();
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	
}