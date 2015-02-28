package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.utils.Bilder;
import mx.unam.ciencias.cv.filters.Filters;
import org.junit.Assert;
import org.junit.Test;


public class BilderTest {
	
	private BufferedImage img;
	private Bilder bld; 
	private final int ITERS = 20; 

	public BilderTest() {
		try {
			img = ImageIO.read(BilderTest.class.getResource("12000X12000.jpg"));
		} catch (Exception e) {
			Assert.fail();
		}
		bld = new Bilder(img);
	}

	@Test public void dimensions() {
		Assert.assertTrue(img.getWidth() ==  bld.getWidth());
		Assert.assertTrue(img.getHeight() ==  bld.getHeight());

	}

	@Test public void indexPixelsTest() {
		for (int x = 0; x < img.getWidth(); x++ ) {
			for (int y = 0; y < bld.getHeight() ; y++ ) {
				Assert.assertTrue(img.getRGB(x,y) == bld.getPixel(x,y));
			}
		}
	}

	@Test public void timeDeltaBufferedImage() {
		try {
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				Filters.grayScale(img);
				long endTime = System.nanoTime();
		        System.out.println(String.format("BufferedImage: %-2d: %s", (i + 1), SpeedTest.timeFormat(endTime - startTime)));
			}
			System.out.println();

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test public void timeDeltaBufferedImageV1() {
		try {
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				Bilder.toBIF(Filters.grayScale(bld));
				long endTime = System.nanoTime();
		        System.out.println(String.format("Bilder V1: %-2d: %s", (i + 1), SpeedTest.timeFormat(endTime - startTime)));
			}
			System.out.println();

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test public void timeDeltaBufferedImageV2() {
		try {
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				Bilder.toBIF(Filters.grayScale2(bld));
				long endTime = System.nanoTime();
		        System.out.println(String.format("Bilder V2: %-2d: %s", (i + 1), SpeedTest.timeFormat(endTime - startTime)));
			}
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test public void timeDeltaBufferedImageV3() {
		try {
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				Bilder.toBIF(Filters.grayScale3(bld));
				long endTime = System.nanoTime();
		        System.out.println(String.format("Bilder V3: %-2d: %s", (i + 1), SpeedTest.timeFormat(endTime - startTime)));
			}
		} catch (Exception e) {
			Assert.fail();
		}
	}


}