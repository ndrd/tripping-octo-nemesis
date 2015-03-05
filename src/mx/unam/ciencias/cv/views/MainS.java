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
import mx.unam.ciencias.cv.filters.*;
import mx.unam.ciencias.cv.utils.*;
import mx.unam.ciencias.cv.test.SpeedTest;

/**
* Main GUI class, created with NetBeans
*/

public class MainS extends javax.swing.JFrame {

    /**
     * Creates new form MainS
     */
    public MainS() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        tabImages = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        canvas1 = new Leinen();
        jPanel4 = new javax.swing.JPanel();
        undo = new javax.swing.JButton();
        k01 = new java.awt.Label();
        k02 = new java.awt.Label();
        k03 = new java.awt.Label();
        k13 = new java.awt.Label();
        k12 = new java.awt.Label();
        k11 = new java.awt.Label();
        k23 = new java.awt.Label();
        k22 = new java.awt.Label();
        k21 = new java.awt.Label();
        k04 = new java.awt.Label();
        k14 = new java.awt.Label();
        k24 = new java.awt.Label();
        k34 = new java.awt.Label();
        k33 = new java.awt.Label();
        k32 = new java.awt.Label();
        k31 = new java.awt.Label();
        tTranslation = new javax.swing.JButton();
        tSimilarity = new javax.swing.JButton();
        tEuclidian = new javax.swing.JButton();
        newPolygon = new javax.swing.JButton();
        labelTransformation = new javax.swing.JLabel();
        tprojective = new javax.swing.JButton();
        tAffine = new javax.swing.JButton();
        labelKernel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuOpen = new javax.swing.JMenuItem();
        menuSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();

        workedImg = new javax.swing.JLabel();
        srcImg = new javax.swing.JLabel();

        kernelLabel = new LinkedList<Label>();
        
        workedImg.setBackground(Color.black);
        srcImg.setBackground(Color.black);

        srcImg.setOpaque(true);
        workedImg.setOpaque(true);

        engine = Filters.getInstance();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.white);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        );

        kernelLabel.add(k01);
        kernelLabel.add(k02);
        kernelLabel.add(k03);
        kernelLabel.add(k04);

        kernelLabel.add(k11);
        kernelLabel.add(k12);
        kernelLabel.add(k13);
        kernelLabel.add(k14);

        kernelLabel.add(k21);
        kernelLabel.add(k22);
        kernelLabel.add(k23);
        kernelLabel.add(k24);

        kernelLabel.add(k31);
        kernelLabel.add(k32);
        kernelLabel.add(k33);
        kernelLabel.add(k34);

        tabImages.addTab("Images", jPanel1);


        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        undo.setText("Undo");
        undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canvas1.undo();
            }
        });


        tTranslation.setText("Translation");
        tTranslation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translateTransform(evt);
            }
        });

        tEuclidian.setText("Euclidian");
        tEuclidian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                euclidianTransform(evt);
            }
        });

        tSimilarity.setText("Similarity");
        tSimilarity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                similarityTransform(evt);
            }
        });

        tAffine.setText("Affine");
        tAffine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affineTransform(evt);
            }
        });

        tprojective.setText("projective");
        tprojective.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectiveTransform(evt);
            }
        });

        newPolygon.setText("New Polygon");
        newPolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRectangle(evt);
            }
        });

        labelTransformation.setText("Transformations");


        labelKernel.setText("Kernel");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tSimilarity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tEuclidian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tTranslation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tprojective, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tAffine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(newPolygon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(undo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelTransformation)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(k31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(k32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(k33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(k34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(k21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(k01, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k02, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k03, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(k11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(k24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k04, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(labelKernel))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(undo)
                    .addComponent(newPolygon))
                .addGap(10, 10, 10)
                .addComponent(labelTransformation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tTranslation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tEuclidian)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tSimilarity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tAffine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tprojective)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelKernel)
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(k01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(k11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(k21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(k04, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(k14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(k24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(k31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabImages.addTab("Canvas", jPanel2);

        getContentPane().add(tabImages);

        jMenu1.setText("File");

        menuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOpen.setText("Open...");
        menuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openImage(evt);
            }
        });
        jMenu1.add(menuOpen);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSave.setText("Save..");
        menuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImg(evt);
            }
        });
        jMenu1.add(menuSave);
        jMenu1.add(jSeparator1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Image");

        jMenuItem1.setText("Filters");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
           

            }

        });

        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Training");

        jMenu4.setText("ImageColorTraining");

        jMenuItem3.setText("New Training Set");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuItem4.setText("Save Current Set");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);
        jMenu4.add(jSeparator2);

        jMenuItem5.setText("Load Training File");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);
        jMenu4.add(jSeparator3);

        jMenuItem6.setText("Search Matchs");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenu3.add(jMenu4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        jScrollPane3.setViewportView(workedImg);
        jScrollPane4.setViewportView(srcImg);

        fillMatrix();
        pack();
    }// </editor-fold>                        

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

    

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        openDir(evt);
    }                                          

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        /* Serialize the trainer */
        serialize(ImageTrainer.getInstance());
    }

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        zombie = deserialize();

    }                                          

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) { 
        try {
            ImageTrainer t = ImageTrainer.getInstance();
            BufferedImage response = ColorFilters.colorSelector(engine.getLastImage(),t.getLowerBound(), t.getUpperBound());
            engine.setLastWork(response);
            putImageOnScreen(response, workedImg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ECannot display image", JOptionPane.ERROR_MESSAGE);
        }
    }

    /***
    * Methods that deal the things
    ***/

    ImageTrainer zombie = null;



    private boolean openFail = false;

    private void putImageOnScreen(BufferedImage inputImage, javax.swing.JLabel srcImg) {
        srcImg.setIcon(new javax.swing.ImageIcon(inputImage));
        srcImg.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        srcImg.setVerticalAlignment(javax.swing.JLabel.CENTER);
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

        int result = chooser.showOpenDialog(this);
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
  
         
        int userSelection = fileChooser.showSaveDialog(this);
        try {
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String path = fileToSave.getName();
                Keeper.saving(o,path);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot be saved :(");
        }
    }


    private void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save"); 

        FileNameExtensionFilter fExt = new FileNameExtensionFilter(" JPEG (*.jpg), PNG  (*.png)", 
                                                                         "jpg", "png");
        String[] ext = fExt.getExtensions();
        fileChooser.setFileFilter(fExt);
  
         
        int userSelection = fileChooser.showSaveDialog(this);
        try {
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                ImageIO.write(engine.getLastWork(), "jpeg", fileToSave);
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot be saved :(");
        }
    }

    private void openDir(AWTEvent evt) {
        String path = openDirectory();
        if(path != null) {
            ImageTrainer trainer =  ImageTrainer.getInstance();

            JDialog dlg = new JDialog(this, "Progress Dialog", true);
            dlg.setSize(300, 100);   
            dlg.setLocationRelativeTo(this);

            JOptionPane pane = new JOptionPane("Your message", JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(this, "Title");

            Thread t = new Thread()
            {
                public void run() {
                    trainer.trainingFromDir(path);
                }
            };
            t.start();

            new Thread()
            {
                public void run() {
                    dialog.setVisible(true);

                }
            }.start();

            while (t.isAlive()) {
            }
            dialog.dispose();

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

        int result = chooser.showOpenDialog(this);
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

        int result = chooser.showOpenDialog(this);
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

    private javax.swing.JProgressBar pBar =  new javax.swing.JProgressBar();

    private void parsingImages() {
        pBar.setIndeterminate(true);
    }                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainS().setVisible(true);
            }
        });
    }

    private Leinen canvas1;
    private javax.swing.JButton undo;
    private javax.swing.JButton tTranslation;
    private javax.swing.JButton tSimilarity;
    private javax.swing.JButton tEuclidian;
    private javax.swing.JButton newPolygon;
    private javax.swing.JButton tprojective;
    private javax.swing.JButton tAffine;
    private javax.swing.JLabel labelTransformation;
    private javax.swing.JLabel labelKernel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTabbedPane tabImages;
    private javax.swing.JMenuItem menuOpen;
    private javax.swing.JMenuItem menuSave;
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

    private javax.swing.JLabel workedImg;
    private javax.swing.JLabel srcImg;

    private Filters engine;

    private LinkedList<Label> kernelLabel;    
}