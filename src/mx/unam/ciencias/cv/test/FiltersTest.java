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
	protected boolean writeFiles = false;
	protected final int ITERS = 1; 

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

	private void saveImage(BufferedImage img, String name){
		try {
			File file = new File(name + ".jpg");
			ImageIO.write(img, "jpeg", file);
		} catch (Exception e) {
			System.out.println("Cannot save" + name);
		}
	}

	protected void writeFile(String name) {
		saveImage(img, name);
	}



}