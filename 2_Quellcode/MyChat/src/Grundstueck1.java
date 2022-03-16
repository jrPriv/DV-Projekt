
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Grundstueck1 extends JPanel {

	Color hintergrundfarbe;
	Vector<Point> grenze;
	Vector<Polygon> gemaeht;
	
//	Robi robi;
	RobiMitSensor robi;

	JSlider links, rechts;
	
	Grundstueck1() {
		grenze = new Vector<Point>();
		gemaeht = new Vector<Polygon>();
		hintergrundfarbe = Color.GREEN;
		addMouseMotionListener(new MML());
		
//		robi = new Robi(this, 0.1, Math.PI/6, 300, 300, Color.ORANGE,70,50);
		robi = new RobiMitSensor(this,0.1, Math.PI/6, 300, 300, Color.ORANGE,70,50);

	}
	
 	class MML implements MouseMotionListener {
		public void mouseDragged(MouseEvent m) {
			grenze.add(new Point(m.getX(),m.getY()));
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		g.setColor(hintergrundfarbe);
		g.fillRect(0, 0, d.width, d.height);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));

		g.setColor(Color.BLACK);
		for (int i=1;i<grenze.size();i++) {
			Point p1 = grenze.elementAt(i-1);
			Point p2 = grenze.elementAt(i);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}

		Polygon p = robi.ausrichten();
    	gemaeht.add(p);
		
		g.setColor(new Color(0,200,100));
		for (int i=0;i<gemaeht.size();i++)
			g.fillPolygon(gemaeht.elementAt(i));
		
		robi.zeichnen(g, p);
	}

	public static void main(String[] args) {
		Rahmen.inFrame("Mein erstes GUI-Programm", new Grundstueck1(), 1500, 2000);
	}
}
