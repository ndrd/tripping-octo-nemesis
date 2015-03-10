package mx.unam.ciencias.cv.filters;
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
import mx.unam.ciencias.cv.utils.FastImage;

public class LaplacianFilter extends ImageFilter {

    public static BufferedImage aproxLaplacian(BufferedImage img, BufferedImage bluredImg, int sigma) {
        
        setName("Laplacian Aproximation");

        int height = img.getHeight();
        int width = img.getWidth();

        FastImage original = new FastImage(img);
        FastImage blured = new FastImage(bluredImg);
        FastImage out = new FastImage(width, height, img.getType());

        int[] rgbBlur = new int[3];
        int[] rgbO = new int[3];
        int[] rgbC = new int[3];

        for (int y  = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                rgbBlur = blured.getPixeli(x,y);
                rgbO = original.getPixeli(x,y);
                /* original diference gaussian */
                rgbC[0] = (int)((rgbO[0] - rgbBlur[0] + 255) * 0.5);
                rgbC[1] = (int)((rgbO[1] - rgbBlur[1] + 255) * 0.5);
                rgbC[2] = (int)((rgbO[2] - rgbBlur[2] + 255) * 0.5);
                
                out.setPixel(x,y, rgbC);
            }
        }

        return out.getImage();
    }

    public static BufferedImage aproxLaplacian(BufferedImage img, int sigma) {
        return aproxLaplacian(img, GaussianBlur.gaussianBlur(img, sigma), sigma);
    }

}