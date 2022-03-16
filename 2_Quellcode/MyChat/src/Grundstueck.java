import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

public class Grundstueck extends JPanel {

	Color hintergrund;
	
	Vector<Point> grenze;
	
	Grundstueck() {
		
		grenze = new Vector<Point>();
		hintergrund =Color.GREEN;
		JButton col = new JButton("Farbe");
		col.addActionListener(new ColAL());
		add(col);
		
		JMenuBar mb = new JMenuBar();
		JMenu datei = new JMenu("Datei");
		JMenuItem speichern = new JMenuItem("Speichern");
		speichern.addActionListener(new SpeichernAL());
		JMenuItem oeffnen = new JMenuItem("Laden");
		oeffnen.addActionListener(new OeffnenAL());
		datei.add(speichern);
		datei.add(oeffnen);
		
		mb.add(datei);
		add(mb);
		
		addMouseListener(new MouseL());
		addMouseMotionListener(new MouseMotionL());
	}

	class MouseL implements MouseListener {
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	class MouseMotionL implements MouseMotionListener {
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			grenze.addElement(new Point(arg0.getX(),arg0.getY()));
			repaint();
		}
	}


	class OeffnenAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}
	class SpeichernAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	class ColAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			hintergrund = JColorChooser.showDialog(null, "Hintergrund", hintergrund);
			repaint();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		g.setColor(hintergrund);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.BLACK);

		 Graphics2D g2 = (Graphics2D) g;
		 g2.setStroke(new BasicStroke(10));
		 
		 for (int i=1;i<grenze.size();i++)
			g.drawLine(grenze.elementAt(i-1).x, grenze.elementAt(i-1).y,
					grenze.elementAt(i).x, grenze.elementAt(i).y);
			
	}
	
	public static void main(String[] args) {
		Rahmen.inFrame("Mowing Robot", new Grundstueck(), 800, 600);
	}

}
