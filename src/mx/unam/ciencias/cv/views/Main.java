package mx.unam.ciencias.cv.views;
/* -*- java -*- */
/* <PDInterfaz.java> */

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import mx.unam.ciencias.cv.filters.*;
import mx.unam.ciencias.cv.models.*;



/**
 * @author MacCG  <macfirstall_4@ciencias.unam.mx>
 * @version v0.0.1
 * @date 07-Febrero-2015
 */
public class Main extends JFrame {

	/* --------------------------------------------------------------------- */	
	/* ATRIBUTOS */
	/* ------------------------------------------------------------- */	
	
    static BufferedImage ivSrc = null;
    static BufferedImage ivWork = null;
	
    private static JLabel ivImgLabelSrc;
	
	// Barra - Menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openFile;
    private JMenuItem quit;

	private JMenu imageMenu;
	private JMenuItem brightnessAction;

	// Scroll pane
    private static JScrollPane ivScrollPaneSrc;

	// Aux
	private static boolean added;
	private static boolean openFail;

	private static double factor;

	
	/* --------------------------------------------------------------------- */	
	/* CONSTRUCTOR */
	/* ------------------------------------------------------------- */	
    public Main(String windowTitle) {	
        super(windowTitle);
        initComponents();
        this.factor = 0.4;

        //Add a listener for window events
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }  
        });
    }
	
	/* --------------------------------------------------------------------- */	
	/* -*- METODOS -*- */
	/* ------------------------------------------------------------- */	
    private void abreImagen() {
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
                this.ivSrc = ImageIO.read(new File(dir));				
                this.ivWork = ImageIO.read(new File(dir));
				this.added = true;
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


	/* --------------------------------------------------------------------- */	
	/* ACTIONS */
	/* ------------------------------------------------------------- */	
	private void openFileAction(AWTEvent evt) {
		abreImagen();

		if(!openFail)
			putImageOnScreen(this.ivSrc);
	}

	private void quitAction(AWTEvent evt) {
		System.out.println(" PDInterfaz.quitAction() --> unmodified!!");
		System.exit(0);
	}

	private void putImageOnScreen(BufferedImage inputImage) {
		this.ivImgLabelSrc.setIcon(new ImageIcon(inputImage));
		this.ivImgLabelSrc.setHorizontalAlignment(JLabel.CENTER);
		this.ivImgLabelSrc.setVerticalAlignment(JLabel.CENTER);
	}


	/**
	* Listeners
	*/
	private void brightnessAction(AWTEvent evt) {
		System.out.println("Testing Performance");
        long startTime = System.nanoTime(); 
		
		this.ivWork = Filters.grayScale(this.ivSrc);
		putImageOnScreen(this.ivWork);
		
		long estimatedTime = System.nanoTime() - startTime;
        double seconds = (double)estimatedTime / 1000000000.0;
        System.out.println("Delta: " + seconds);
        
        System.out.println("-----Testing Performance");
        startTime = System.nanoTime(); 
		
		ImageD detailed = new ImageD(this.ivSrc);
		
		estimatedTime = System.nanoTime() - startTime;
        seconds = (double)estimatedTime / 1000000000.0;
        System.out.println("Delta Hists: " + seconds);

	}



	
	/* --------------------------------------------------------------------- */	
	/* INIT */
	/* ------------------------------------------------------------- */	
    private void initComponents() {	
        menuBar = new JMenuBar();
		fileMenu = new JMenu();
        openFile = new JMenuItem();
        quit = new JMenuItem();
		
        imageMenu = new JMenu();
        brightnessAction = new JMenuItem();

        fileMenu.setText("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
        openFile.setText("Open");
		openFile.setMnemonic(KeyEvent.VK_O);
		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                openFileAction(evt);
			}
		});						
        fileMenu.add(openFile);

        quit.setText("Quit");
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                quitAction(evt);
			}
		});		
        fileMenu.add(quit);
        menuBar.add(fileMenu);

		
        imageMenu.setText("Image");
		imageMenu.setMnemonic(KeyEvent.VK_E);
		
		brightnessAction.setText("Brightness");
		brightnessAction.setMnemonic(KeyEvent.VK_B);
		brightnessAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                brightnessAction(evt);
			}
		});		
        imageMenu.add(brightnessAction);
        menuBar.add(imageMenu);

		// Adds the complete menu
        setJMenuBar(menuBar);

		
		// Dimensiones de la pantalla 
		int W = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int H = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		// Dimensiones de la ventana interfaz 
		int w = W - 70;
		int h = H - 70;
		
		// Dimension de cada scroll pane 
		int a = W/2 - 10;
		int b = H/2 - 10;
			
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));

        this.ivImgLabelSrc = new JLabel();

		this.ivScrollPaneSrc = new JScrollPane();
        this.ivScrollPaneSrc.setViewportView(this.ivImgLabelSrc);

		this.ivScrollPaneSrc.setPreferredSize(new Dimension(a, b));
		this.ivScrollPaneSrc.setBorder(BorderFactory.createCompoundBorder
								   (BorderFactory.createEmptyBorder(20, 20, 20, 20), 
									  BorderFactory.createLineBorder(Color.black, 1)));
		c.add(ivScrollPaneSrc);
				
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(w, h));
		this.pack();
		this.setVisible(true);	
    }

	
	/* --------------------------------------------------------------------- */	
	/* EJECUCION */
	/* ------------------------------------------------------------- */	
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main interfaz = new Main("Computer Vision - Image Viewer");
				//open();
			}
        });
    }
	
} //PDInterfaz.java
