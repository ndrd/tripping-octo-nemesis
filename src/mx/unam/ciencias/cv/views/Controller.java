package mx.unam.ciencias.cv.views;
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
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.LinkedList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import mx.unam.ciencias.cv.core.filters.*;
import mx.unam.ciencias.cv.core.miscellaneous.*;
import mx.unam.ciencias.cv.core.geometry.*;
import mx.unam.ciencias.cv.utils.models.*;
import mx.unam.ciencias.cv.utils.trainer.*;
import mx.unam.ciencias.cv.test.SpeedTest;

public class Controller {

	private Leinen canvas1;

    private BufferedImage source;
    private BufferedImage newImage;

    private java.awt.Component component;
    private javax.swing.JLabel workedImg;
    private javax.swing.JLabel srcImg;
    private boolean colorSearchReady;

	public Controller(Component cc, Leinen c, JLabel w, JLabel s) {
		canvas1 = c;
		component = cc;
		workedImg = w;
		srcImg = s;
        colorSearchReady =  false;

	}

	private boolean openFail = false;

    private void putImageOnScreen(BufferedImage inputImage, JLabel srcImg) {
        srcImg.setIcon(new ImageIcon(inputImage));
        srcImg.setHorizontalAlignment(JLabel.CENTER);
        srcImg.setVerticalAlignment(JLabel.CENTER);
    }

    public void openImage(AWTEvent evt) {

        source = getImage();
        newImage = source;
        if(source != null) {
            putImageOnScreen(source, srcImg);
            putImageOnScreen(source, workedImg);
        }
    }

    public void saveImage(AWTEvent evt) {
        if (newImage == null)
            return;
        save();
        
    }

    private ImageTrainer deserialize() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        openFail = false;
        
        FileNameExtensionFilter fExt = new FileNameExtensionFilter(" SER  (*.ser)", 
                                                                    "ser");
        String[] ext = fExt.getExtensions();
        chooser.setFileFilter(fExt);

        int result = chooser.showOpenDialog(component);
        if (result == JFileChooser.CANCEL_OPTION) {
            openFail = true;
            return null;
        }
        else if (result == JFileChooser.APPROVE_OPTION) {
            String dir = chooser.getSelectedFile().getPath();
            if (dir == null || dir.equals("")) {
                openFail = true;
                System.out.print("Archivo invalido, saliendo");
                System.exit((0));
            }
            try {
                return (ImageTrainer) Keeper.read(dir);               
            } 
            catch(Exception e) {
                openFail = true;
            }
        }
        return null;
    }

    public void canvasUndo() {
        canvas1.undo();
    }

    public void showFilter(BufferedImage img) {
        newImage = img;
        putImageOnScreen(img, workedImg);
        putImageOnScreen(source, srcImg);

    }

    public BufferedImage getOriginalImage() {
        if (source == null)
            source =  getImage();
        return source;
    }

    public BufferedImage getNewImage() {
        if (newImage == null)
            newImage =  getImage();
        return newImage;
    }


    private void serialize(Object o) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save"); 

        FileNameExtensionFilter fExt = new FileNameExtensionFilter(" SER (*.ser),", 
                                                                         "ser");
        String[] ext = fExt.getExtensions();
        fileChooser.setFileFilter(fExt);
  
         
        int userSelection = fileChooser.showSaveDialog(component);
        try {
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String path = fileToSave.getName();
                Keeper.saving(o,path);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(component, "Cannot be saved :(");
        }
    }


    private void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save"); 

        FileNameExtensionFilter fExt = new FileNameExtensionFilter(" JPEG (*.jpg), PNG  (*.png)", 
                                                                         "jpg", "png");
        String[] ext = fExt.getExtensions();
        fileChooser.setFileFilter(fExt);
  
         
        int userSelection = fileChooser.showSaveDialog(component);
        try {
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                ImageIO.write(newImage, "jpeg", fileToSave);
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(component, "Cannot be saved :(");
        }
    }

    private void openDir(AWTEvent evt) {
        String path = openDirectory();
        if(path != null) {
            ImageTrainer trainer =  ImageTrainer.getInstance();
            trainer.trainingFromDir(path);
            colorSearchReady = true;
        }
    }

 

    public BufferedImage getImage() {
        BufferedImage result = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        openFail = false;
        
        FileNameExtensionFilter fExt = new FileNameExtensionFilter("BMP  (*.bmp), GIF  (*.gif), JPEG (*.jpg), PNG  (*.png)", 
                                                                    "bmp", "gif", "jpg", "png");
        String[] ext = fExt.getExtensions();
        chooser.setFileFilter(fExt);

        int r = chooser.showOpenDialog(component);
        if (r == JFileChooser.CANCEL_OPTION) {
            openFail = true;
            throw new IllegalArgumentException("Cannot open file");
        } else if (r == JFileChooser.APPROVE_OPTION) {
            String dir = chooser.getSelectedFile().getPath();
            if (dir == null || dir.equals("")) {
                openFail = true;
                System.out.print("Archivo invalido, saliendo");
                System.exit((0));
            }
            try {
                result = ImageIO.read(new File(dir));               
            } 
            catch(Exception e) {
                openFail = true;
            }
        }
        
        if (result == null)
            throw new IllegalArgumentException("Cannot open file");

        return result;
    }

    private String openDirectory() {
        File dir = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        openFail = false;

        int result = chooser.showOpenDialog(component);
        if (result == JFileChooser.CANCEL_OPTION) {
            openFail = true;
        }
        else if (result == JFileChooser.APPROVE_OPTION) {
             dir = chooser.getSelectedFile();
            if (dir == null || dir.equals("")) {
                openFail = true;
                System.out.print("Archivo invalido, saliendo");
                System.exit((0));
            } else {
                System.out.print("Abriendo..." + dir.getAbsolutePath());

            }
        }
        return dir.getAbsolutePath();
     }


    public void newRectangle(java.awt.event.ActionEvent evt) {
        int x = Integer.parseInt(JOptionPane.showInputDialog("(x0) coordinate"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("(y0) coordinate"));
        int w = Integer.parseInt(JOptionPane.showInputDialog("width"));
        int h = Integer.parseInt(JOptionPane.showInputDialog("heigth"));
        canvas1.drawRectangle(x, y, w, h);  

    }

    public void showHistograms(BufferedImage img) {
        new HistogramsWindow();
    }

    public void showInfo() {
        JOptionPane.showMessageDialog(null, "visual-cosmic-rainbown\n" + 
                                                            "Jonathan Andrade, 2015\n" + 
                                                            "ndrd@ciencias.unam.mx\n" + 
                                                            "'Love u Magda'", "About",
                                     JOptionPane.INFORMATION_MESSAGE);

    }

    public int getIntegerValue(String msg) {
        int n  = 1;
        try {
            n = Integer.parseInt(JOptionPane.showInputDialog(msg));
        } catch (Exception e) {
            n = -1;
        }

        return n;

    }

    public boolean getTFValue(String msg) {
        try {
            return (JOptionPane.showConfirmDialog(null, msg) == 1);
        } catch (Exception e) {
            return false;
        }
    }

    private void fillMatrix() {
     
        
    }

    public void showMatrix(Matrix a) {
        if (a.nRows() > 4 || a.nCols() > 4)
            return;

        fillMatrix();
   
    }

    private boolean debug = true;

    private void translateTransform(java.awt.event.ActionEvent evt) {
        try{
            int x = Integer.parseInt(JOptionPane.showInputDialog("delta X"));
            int y = Integer.parseInt(JOptionPane.showInputDialog("delta Y "));
            
            Transformation2D t = Transform.translate(x,y);
            canvas1.applyTransform(t);
            showMatrix(t.getKernel());
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error on Transform", JOptionPane.ERROR_MESSAGE);
            
            if (debug)
                e.printStackTrace();
        }
    }

    private void euclidianTransform(java.awt.event.ActionEvent evt) {
        try{
            int tetha = Integer.parseInt(JOptionPane.showInputDialog("Angle: "));
            int x = Integer.parseInt(JOptionPane.showInputDialog("delta X:"));
            int y = Integer.parseInt(JOptionPane.showInputDialog("delta Y:"));

            Transformation2D t = Transform.euclidean(tetha,x,y);
            canvas1.applyTransform(t);
            showMatrix(t.getKernel());


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error on Transform", JOptionPane.ERROR_MESSAGE);
            
            if (debug)
                e.printStackTrace();
        }
    }

    private void similarityTransform(java.awt.event.ActionEvent evt) {
        try{

            double s = Double.parseDouble(JOptionPane.showInputDialog("Scale: "));
            int tetha = Integer.parseInt(JOptionPane.showInputDialog("Angle: "));
            int x = Integer.parseInt(JOptionPane.showInputDialog("delta X:"));
            int y = Integer.parseInt(JOptionPane.showInputDialog("delta Y:"));

            Transformation2D t = Transform.similarity(s, tetha, x, y);
            canvas1.applyTransform(t);
            showMatrix(t.getKernel());



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error on Transform", JOptionPane.ERROR_MESSAGE);
            
            if (debug)
                e.printStackTrace();
        }
    }

    private void affineTransform(java.awt.event.ActionEvent evt) {
        try{

            double a = Double.parseDouble(JOptionPane.showInputDialog("A: "));
            double b = Double.parseDouble(JOptionPane.showInputDialog("B: "));
            double c = Double.parseDouble(JOptionPane.showInputDialog("C: "));
            double d = Double.parseDouble(JOptionPane.showInputDialog("D: "));
            double e = Double.parseDouble(JOptionPane.showInputDialog("E: "));
            double f = Double.parseDouble(JOptionPane.showInputDialog("F: "));

            Transformation2D t = Transform.affine(a,b,c,d,e,f);
            canvas1.applyTransform(t);
            showMatrix(t.getKernel());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error on Transform", JOptionPane.ERROR_MESSAGE);
            
            if (debug)
                e.printStackTrace();
        }
    }

    private void projectiveTransform(java.awt.event.ActionEvent evt) {
        try{

            double a = Double.parseDouble(JOptionPane.showInputDialog("A: "));
            double b = Double.parseDouble(JOptionPane.showInputDialog("B: "));
            double c = Double.parseDouble(JOptionPane.showInputDialog("C: "));
            
            double d = Double.parseDouble(JOptionPane.showInputDialog("D: "));
            double e = Double.parseDouble(JOptionPane.showInputDialog("E: "));
            double f = Double.parseDouble(JOptionPane.showInputDialog("F: "));

            double g = Double.parseDouble(JOptionPane.showInputDialog("G: "));
            double h = Double.parseDouble(JOptionPane.showInputDialog("H: "));
            double i = Double.parseDouble(JOptionPane.showInputDialog("I: "));

            Transformation2D t = Transform.projective(a,b,c, d,e,f, g,h,i);
            canvas1.applyNormTransform(t);
            showMatrix(t.getKernel());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage(), "Error on Transform", JOptionPane.ERROR_MESSAGE);
            
            if (debug)
                e.printStackTrace();
        }
    }

}