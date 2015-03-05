package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.utils.*;
import org.junit.Assert;
import org.junit.Test;


public class FiltersTest {
	
	protected BufferedImage img;
	protected final int ITERS = 2; 

	public FiltersTest() {
		try {
			img = ImageIO.read(BilderTest.class.getResource("test.jpg"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test public void imageLoaded() {
		Assert.assertTrue(img != null);
	}

}