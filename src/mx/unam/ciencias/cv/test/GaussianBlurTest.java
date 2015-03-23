package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.utils.models.*;

import mx.unam.ciencias.cv.core.filters.*;
import mx.unam.ciencias.cv.core.miscellaneous.*;
import org.junit.Assert;
import org.junit.Test;


public class GaussianBlurTest extends FiltersTest {

	public GaussianBlurTest() {
		super();
	}

	public void writableRasterBlur() {
		
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianBlur(img,5);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, GaussianBlur.getName() + i);

			}
		    System.out.println(String.format( ITERS  + " iters for writableRasterBlur, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}





	@Test  public void BsBLur4() {
		
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianBlur(img, 10);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, GaussianBlur.getName() + i);

			}
		    System.out.println(String.format( ITERS  + " iters for BsBLur4, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test  public void BsBLurVertical() {
		
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianVertical(img, 10);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, GaussianBlur.getName() + i + "vv");

			}
		    System.out.println(String.format( ITERS  + " iters for BsBLur4Ver, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test  public void BsBLurHorizontal() {
		
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianHorizontal(img, 10);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, GaussianBlur.getName() + i + "hh");

			}
		    System.out.println(String.format( ITERS  + " iters for BsBLur4Hor, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test  public void BsBLurHorizontalYVert() {
		
		try {
			long total = 0;
			for (int i = 0; i < ITERS ; i++) {
				long startTime = System.nanoTime();
				BufferedImage tmp = GaussianBlur.gaussianBlur1(img, 10);
				long endTime = System.nanoTime();
				total += (endTime - startTime);

				if (writeFiles) 
					saveImage(tmp, GaussianBlur.getName() + i + "dd");

			}
		    System.out.println(String.format( ITERS  + " iters for BsBLur4Hora, total time %s", SpeedTest.timeFormat(total)));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}



	
}