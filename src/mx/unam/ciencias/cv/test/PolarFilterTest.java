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


public class PolarFilterTest extends FiltersTest {

	public PolarFilterTest() {
		super();
	}


	public void rect2Polar() {
		try {

			img = ImageIO.read(FiltersTest.class.getResource(path + "2polar.jpg"));

			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage polar = PolarFilter.rectangle2Polar(img);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(polar,"polarTest" + i);
			}
		    System.out.println(String.format( ITERS  + " iters for polarTest, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test public void rect2PolarA() {
		try {

			img = ImageIO.read(FiltersTest.class.getResource(path + "2polar.jpg"));

			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage polar = PolarFilter.rectangle2PolarA(img,0);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(polar,"polarTestA" + i);
			}
		    System.out.println(String.format( ITERS  + " iters for polarTest, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}