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


public class MixingFiltersTest extends FiltersTest {

	private BufferedImage secongImage;
	private BufferedImage firstImage;

	public MixingFiltersTest() {
		try {
			firstImage = ImageIO.read(BilderTest.class.getResource("russianbear.jpg"));
			secongImage = ImageIO.read(BilderTest.class.getResource("chew.jpg"));
		} catch (Exception e) {
			Assert.fail();
		}
	}



	@Test public void blending() {
		writeFiles =  true;
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = MixingFilters.blending(firstImage,secongImage, 50);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, MixingFilters.getName() + i);

			}
		    System.out.println(String.format( ITERS  + " iters for blending, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}



	
}