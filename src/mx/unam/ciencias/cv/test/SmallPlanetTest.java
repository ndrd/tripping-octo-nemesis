package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.utils.*;
import mx.unam.ciencias.cv.core.filters.*;
import mx.unam.ciencias.cv.core.miscellaneous.*;
import org.junit.Assert;
import org.junit.Test;


public class SmallPlanetTest extends FiltersTest {
	

	public SmallPlanetTest() {
		super();
	}


	@Test public void scaleUp() {
		try {

			img = ImageIO.read(FiltersTest.class.getResource(path + "sp.jpg"));


			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage mini = SmallPlanet.cosmogenesis(img);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(mini,"spp" + i);
			}
		    System.out.println(String.format( ITERS  + " iters for spp, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}