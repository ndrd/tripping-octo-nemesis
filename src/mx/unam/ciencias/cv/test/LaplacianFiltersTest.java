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


public class LaplacianFiltersTest extends FiltersTest {

	public LaplacianFiltersTest() {
		super();
	}

	@Test public void aproximationLaplacian() {
		writeFiles =  true;
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = LaplacianFilter.aproxLaplacian(img, 7);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, LaplacianFilter.getName() + i);

			}
		    System.out.println(String.format( ITERS  + " iters for aproxLaplacian, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}