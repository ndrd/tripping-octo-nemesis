package mx.unam.ciencias.cv.views;

import mx.unam.ciencias.cv.utils.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
  Rectangle rect = new Rectangle(0, 0, 100, 50);

  int[] xPoints = new int[0];
  int[] yPoints = new int[4];

  boolean pointsReady = false;


  Graphics2D g2;

  int preX, preY;

  boolean isFirstTime = true;

  Rectangle area;

  boolean pressOut = false;

  public MyCanvas() {
    setBackground(Color.white);
    setVisible(true);
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  public void mousePressed(MouseEvent e) {
    preX = rect.x - e.getX();
    preY = rect.y - e.getY();

    if (rect.contains(e.getX(), e.getY()))
      updateLocation(e);
    else {
      pressOut = true;
    }
  }

  public void mouseDragged(MouseEvent e) {
    if (!pressOut)
      updateLocation(e);
    else
      return;
  }

  public void mouseReleased(MouseEvent e) {
    if (rect.contains(e.getX(), e.getY()))
      updateLocation(e);
    else {
      pressOut = false;
    }
  }

  /**
  * Draws a new rectangle with corner points 
  */
  public void newRectangle(int x1, int y1, int w, int h) {
    xPoints =  new int[4];
    yPoints =  new  int[4];

    xPoints[0] = x1;
    xPoints[1] = x1 + w;
    xPoints[3] = x1;
    xPoints[2] = x1 + w;   

    yPoints[0] = y1;
    yPoints[1] = y1;
    yPoints[2] = y1 + h;
    yPoints[3] = y1 + h; 

    pointsReady =  true;
    repaint();
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void updateLocation(MouseEvent e) {
    rect.setLocation(preX + e.getX(), preY + e.getY());
    repaint();
  }

  public void paint(Graphics g) {
    update(g);
  }

  public void update(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Dimension dim = getSize();
    int w = (int) dim.getWidth();
    int h = (int) dim.getHeight();
    g2.setStroke(new BasicStroke(4.0f));

    if (isFirstTime) {
      area = new Rectangle(dim);
      rect.setLocation(w / 2 , h / 2);
      isFirstTime = false;
    }

    // Clears the rectangle that was previously drawn.
    g2.setPaint(Color.black);
    g2.fillRect(0, 0, w, h);

    g2.setColor(Color.white);
    //g2.draw(rect);

    if (pointsReady) {
      g.drawPolygon(xPoints, yPoints, xPoints.length);
    }

    g2.setColor(Color.black);
    g2.fill(rect);
  }

  boolean checkRect() {
    if (area == null) {
      return false;
    }

    if (area.contains(rect.x, rect.y, 100, 50)) {
      return true;
    }
    int new_x = rect.x;
    int new_y = rect.y;

    if ((rect.x + 100) > area.getWidth()) {
      new_x = (int) area.getWidth() - 99;
    }
    if (rect.x < 0) {
      new_x = -1;
    }
    if ((rect.y + 50) > area.getHeight()) {
      new_y = (int) area.getHeight() - 49;
    }
    if (rect.y < 0) {
      new_y = -1;
    }
    rect.setLocation(new_x, new_y);
    return false;
  }

  public void transformTest(int a) throws Exception {
    int [][] matrix = {{1,a,0},
                       {0,1,0},
                       {0,0,1}};

    Transformation2D t =  new Transformation2D(matrix);
    if (pointsReady) {
        t.transformPoints(xPoints, yPoints);
    }
  }
}