package mx.unam.ciencias.cv.core.miscellaneous;
/*
 * This file is part of visual-cosmic-rainbown
 *
 * Copyright Jonathan Andrade 2015
 *
 * visual-cosmic-rainbown is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * visual-cosmic-rainbown is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with visual-cosmic-rainbown. If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.image.BufferedImage;
import mx.unam.ciencias.cv.utils.models.FastImage;

import mx.unam.ciencias.cv.core.miscellaneous.ScaleImage;
import mx.unam.ciencias.cv.core.filters.PolarFilter;
import mx.unam.ciencias.cv.core.filters.ImageFilter;

public class SmallPlanet extends ImageFilter {

	public static BufferedImage cosmogenesis(BufferedImage img) {
		img = ScaleImage.scaleInterpolate(img, img.getWidth(), img.getWidth());
		return PolarFilter.rectangle2PolarA(img);
	}

}