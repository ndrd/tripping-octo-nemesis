package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.core.filters.*;
import mx.unam.ciencias.cv.core.miscellaneous.*;
import org.junit.Assert;
import org.junit.Test;


public class ScaleImageTest extends FiltersTest {
	

	public ScaleImageTest() {
		super();
	}

	@Test public void scaleDown() {
		try {

			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage mini = ScaleImage.scaleInterpolateB(img, 200,200);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(mini,"scaleDown200x200" + i);
			}
		    System.out.println(String.format( ITERS  + " iters for scaleDown200x200b, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test public void scaleUp() {
		try {

			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage mini = ScaleImage.scaleInterpolateB(img, img.getWidth(), img.getWidth());
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(mini,"scaleDown2000x2000" + i);
			}
		    System.out.println(String.format( ITERS  + " iters for scaleDown2000x2000b, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}