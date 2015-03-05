package mx.unam.ciencias.cv.views;
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
import mx.unam.ciencias.cv.filters.*;
import mx.unam.ciencias.cv.utils.*;
import mx.unam.ciencias.cv.test.SpeedTest;

public class Controler {

	private Leinen canvas1;

    private Filters engine;

    private LinkedList<Label> kernelLabel;    
	private java.awt.Label k01;
    private java.awt.Label k04;
    private java.awt.Label k14;
    private java.awt.Label k24;
    private java.awt.Label k34;
    private java.awt.Label k33;
    private java.awt.Label k32;
    private java.awt.Label k31;
    private java.awt.Label k03;
    private java.awt.Label k02;
    private java.awt.Label k13;
    private java.awt.Label k12;
    private java.awt.Label k11;
    private java.awt.Label k23;
    private java.awt.Label k22;
    private java.awt.Label k21;
    private java.awt.Component component;
    private javax.swing.JLabel workedImg;
    private javax.swing.JLabel srcImg;

	public Controler(Component cc, Leinen c, JLabel w, JLabel s) {
		canvas1 = c;
		component = cc;
		workedImg = w;
		srcImg = s;
	}

	private boolean openFail = false;

    private void putImageOnScreen(BufferedImage inputImage, JLabel srcImg) {
        srcImg.setIcon(new ImageIcon(inputImage));
        srcImg.setHorizontalAlignment(JLabel.CENTER);
        srcImg.setVerticalAlignment(JLabel.CENTER);
    }

    private void openImage(AWTEvent evt) {
        openImage();
        if(!openFail) {
            putImageOnScreen(engine.getLastImage(), srcImg);
            putImageOnScreen(engine.getLastImage(), workedImg);
        }
    }

    private void saveImg(AWTEvent evt) {
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
                ImageIO.write(engine.getLastWork(), "jpeg", fileToSave);
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

            Thread t = new Thread()
            {
                public void run() {
                    trainer.trainingFromDir(path);
                }
            };
            t.start();


        }
    }

 

    private void openImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        openFail = false;
        
        FileNameExtensionFilter fExt = new FileNameExtensionFilter("BMP  (*.bmp), GIF  (*.gif), JPEG (*.jpg), PNG  (*.png)", 
                                                                    "bmp", "gif", "jpg", "png");
        String[] ext = fExt.getExtensions();
        chooser.setFileFilter(fExt);

        int result = chooser.showOpenDialog(component);
        if (result == JFileChooser.CANCEL_OPTION) {
            openFail = true;
            return;
        }
        else if (result == JFileChooser.APPROVE_OPTION) {
            String dir = chooser.getSelectedFile().getPath();
            if (dir == null || dir.equals("")) {
                openFail = true;
                System.out.print("Archivo invalido, saliendo");
                System.exit((0));
            }
            try {
                engine.addImage(ImageIO.read(new File(dir)));               
            } 
            catch(OutOfMemoryError t) {
                openFail = true;
            }
            catch(IOException e) {
                openFail = true;
            }
            catch(Exception e) {
                openFail = true;
            }
        }
        return;
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


    private void newRectangle(java.awt.event.ActionEvent evt) {
        int x = Integer.parseInt(JOptionPane.showInputDialog("(x0) coordinate"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("(y0) coordinate"));
        int w = Integer.parseInt(JOptionPane.showInputDialog("width"));
        int h = Integer.parseInt(JOptionPane.showInputDialog("heigth"));
        canvas1.drawRectangle(x, y, w, h);  

    }

    private void fillMatrix() {
        int i = 0;
        for (Label l : kernelLabel) 
            l.setText("0.0");
        
    }

    public void showMatrix(Matrix a) {
        if (a.nRows() > 4 || a.nCols() > 4)
            return;

        fillMatrix();
        
        for (int i = 0, l = 0; i < a.nRows() ;i++, l += 4 ) {
            for (int j = 0; j < a.nCols(); j++ ) {
                Label ll = kernelLabel.get(l+j);
                ll.setText("" + a.get(i,j));
            }
        }
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