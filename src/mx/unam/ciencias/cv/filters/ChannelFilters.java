package mx.unam.ciencias.cv.filters;
/*
 * This file is part of tom
 *
 * Copyright Jonathan Andrade 2015
 *
 * tom is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * tom is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with tom. If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.image.BufferedImage;
import mx.unam.ciencias.cv.utils.FastImage;

public class ChannelFilters extends ImageFilter {

	/* Mix channels making people purple */
	public static BufferedImage purplePeople(BufferedImage img) {
		FastImage in = new FastImage(img);
		FastImage out =  new FastImage(img.getWidth(), img.getHeight(), img.getType());

		for (int x = 0; x < img.getWidth() ; x++ ) {
			for (int y = 0; y < img.getHeight() ;y++ ) {
				short [] rgb = in.getPixel(x,y);
				short r = rgb[0];
				short g = rgb[2];
				rgb[0] = rgb[1];
				rgb[1] = g;
				rgb[2] = r;
				out.setPixel(x,y,rgb);
			}
		}
		return out.getImage();
	}

	/* increments the color of a channel by deltas */
	public static BufferedImage incrementChanelColor(BufferedImage img, int deltaR, int deltaG, int deltaB) {
		int width = img.getWidth();
		int height =  img.getWidth();
		FastImage in =  new FastImage(img);
		FastImage out = new FastImage(width, height, img.getType());
		
		for (int x = 0; x < width ; x++ ) {
			for (int y = 0; y < height ; y++ ) {
				short rgb[] = in.getPixel(x,y);
				rgb[0] = (short)(((rgb[0] + deltaR) > 255) ? 255 : ((rgb[0] + deltaR) < 0) ? 0 : rgb[0] + deltaR); 
				rgb[1] = (short)(((rgb[1] + deltaR) > 255) ? 255 : ((rgb[1] + deltaR) < 0) ? 0 : rgb[1] + deltaR); 
				rgb[2] = (short)(((rgb[2] + deltaR) > 255) ? 255 : ((rgb[2] + deltaR) < 0) ? 0 : rgb[2] + deltaR); 
			}
		}

		return out.getImage();
	}
}