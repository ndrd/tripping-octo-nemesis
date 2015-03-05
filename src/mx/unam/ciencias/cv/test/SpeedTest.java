package mx.unam.ciencias.cv.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import mx.unam.ciencias.cv.filters.FiltersBeta;

import org.junit.Assert;
import org.junit.Test;

public class SpeedTest {

   public static void run() throws IOException {

      BufferedImage hugeImage = ImageIO.read(SpeedTest.class.getResource("12000X12000.jpg"));

      System.out.println("Testing convertTo2DUsingGetRGB:");
      for (int i = 0; i < 10; i++) {
         long startTime = System.nanoTime();
         int[][] result = convertTo2DUsingGetRGB(hugeImage);
         long endTime = System.nanoTime();
         System.out.println(String.format("%-2d: %s", (i + 1), timeFormat(endTime - startTime)));
      }

      System.out.println("");

      System.out.println("Testing convertTo2DWithoutUsingGetRGB:");
      for (int i = 0; i < 10; i++) {
         long startTime = System.nanoTime();
         int[][] result = convertTo2DWithoutUsingGetRGB(hugeImage);
         long endTime = System.nanoTime();
         System.out.println(String.format("%-2d: %s", (i + 1), timeFormat(endTime - startTime)));
      }
   }

   private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
      int width = image.getWidth();
      int height = image.getHeight();
      int[][] result = new int[width][height];

      for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
            result[x][y] = image.getRGB(x, y);
         }
      }

      return result;
   }

   private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] result = new int[height][width];
      if (hasAlphaChannel) {
         final int pixelLength = 4;
         for (int pixel = 0, x = 0, y = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            result[x][y] = argb;
            y++;
            if (y == width) {
               y = 0;
               x++;
            }
         }
      } else {
         final int pixelLength = 3;
         for (int pixel = 0, x = 0, y = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += -16777216; // 255 alpha
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            result[x][y] = argb;
            y++;
            if (y == width) {
               y = 0;
               x++;
            }
         }
      }

      return result;
   }

   public static String timeFormat(long nanoSecs) {
      int minutes    = (int) (nanoSecs / 60000000000.0);
      int seconds    = (int) (nanoSecs / 1000000000.0)  - (minutes * 60);
      int millisecs  = (int) ( ((nanoSecs / 1000000000.0) - (seconds + minutes * 60)) * 1000);


      if (minutes == 0 && seconds == 0)
         return millisecs + "ms";
      else if (minutes == 0 && millisecs == 0)
         return seconds + "s";
      else if (seconds == 0 && millisecs == 0)
         return minutes + "min";
      else if (minutes == 0)
         return seconds + "s " + millisecs + "ms";
      else if (seconds == 0)
         return minutes + "min " + millisecs + "ms";
      else if (millisecs == 0)
         return minutes + "min " + seconds + "s";

      return minutes + "min " + seconds + "s " + millisecs + "ms";
   }

   public void test() {
      try{
         //SpeedTest.run();
      } catch (Exception e) {
         Assert.fail();
         e.printStackTrace();
      }
   }

   public void bitshiftBI() throws Exception {
      BufferedImage hugeImage = ImageIO.read(SpeedTest.class.getResource("images/12000X12000.jpg"));
      long startTime = System.nanoTime();
      FiltersBeta.grayScalei(hugeImage);
      long endTime = System.nanoTime();
      System.out.println(String.format("BS %-2d: %s", 1, timeFormat(endTime - startTime)));
   }

   public void writableRaster() throws Exception {
      BufferedImage hugeImage = ImageIO.read(SpeedTest.class.getResource("images/12000X12000.jpg"));
      long startTime = System.nanoTime();
      FiltersBeta.grayScale(hugeImage);
      long endTime = System.nanoTime();
      System.out.println(String.format("WR %-2d: %s", 1, timeFormat(endTime - startTime)));
   }
}