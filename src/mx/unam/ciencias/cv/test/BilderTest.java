package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.utils.*;
import org.junit.Assert;
import org.junit.Test;


public class BilderTest {
	
	private BufferedImage img;
	private Bilder bld; 
	private final int ITERS = 100; 

	public BilderTest() {
		try {
			img = ImageIO.read(BilderTest.class.getResource("test.jpg"));
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

}