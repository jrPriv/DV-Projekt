
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JPanel {

	Grundstueck1 grund;
	JSlider spdLinks, spdRechts;
	
	GUI() {
		setLayout(new BorderLayout());
		JPanel oben = new JPanel();
		JPanel rechts = new JPanel();
		rechts.setLayout(new GridLayout(10,1));
		grund = new Grundstueck1();
		add(grund, BorderLayout.CENTER);
		add(oben, BorderLayout.NORTH);
		add(rechts, BorderLayout.EAST);
		
		
		JMenuBar mb = new JMenuBar();
		JMenu datei = new JMenu("Datei");
		JMenu col = new JMenu("Farbe");
		JMenuItem neu = new JMenuItem("Neu");
		JMenuItem speichern = new JMenuItem("Speichern");
		JMenuItem laden = new JMenuItem("Laden");
		JMenuItem farbe = new JMenuItem("Hintergrundfarbe");

		datei.add(neu);
		datei.add(speichern);
		datei.add(laden);
		col.add(farbe);
		mb.add(datei);
		mb.add(col);
		oben.add(mb);
		
		spdLinks = new JSlider();
		spdLinks.setBorder(new TitledBorder("links"));
		rechts.add(spdLinks);
		spdRechts = new JSlider();
		spdRechts.setBorder(new TitledBorder("rechts"));
		rechts.add(spdRechts);

		JTextField ef = new JTextField("Eingabe");
		rechts.add(ef);
        ef.addKeyListener(new KL());
		
		spdLinks.addChangeListener(new LCL());
		spdRechts.addChangeListener(new RCL());
		
		JButton fahren = new JButton("Fahren");
		rechts.add(fahren);
		fahren.addActionListener(new FahrenAL());

		speichern.addActionListener(new SpeichernAL());
		laden.addActionListener(new LadenAL());
		neu.addActionListener(new NeuAL());
		farbe.addActionListener(new FarbeAL());
	}

	class KL implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			System.out.print(arg0.getKeyChar());
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class LCL implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			grund.robi.vLinks = spdLinks.getValue();
		}
		
	}
	class RCL implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			grund.robi.vRechts = spdRechts.getValue();
		}
	}

	
	class FahrenAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			grund.robi.doMove = !grund.robi.doMove;
		}
	} 

	
	class FarbeAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Color ret = JColorChooser.showDialog(null, "Hintergrundfarbe" , Color.GREEN);
			if (ret != null) {
				grund.hintergrundfarbe = ret;
				repaint();
			}
		}
	}

	class NeuAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			grund.grenze.removeAllElements();
			repaint();
		}
	}
	
	class SpeichernAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(".");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Grundstueck-Dateien", "gnd");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				String dateiname = chooser.getSelectedFile().getName();
				try {
					RandomAccessFile datei = new RandomAccessFile(dateiname,"rw");
					for (int i=0;i<grund.grenze.size();i++) {
						Point p = grund.grenze.elementAt(i);
						datei.writeInt(p.x);
						datei.writeInt(p.y);
					}
					datei.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	class LadenAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser(".");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Grundstueck-Dateien", "gnd");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				String dateiname = chooser.getSelectedFile().getName();
				RandomAccessFile datei=null;
				try {
					datei = new RandomAccessFile(dateiname,"r");
					grund.grenze.removeAllElements();	
					while(true) {
						Point p = new Point();	
						p.x=datei.readInt();
						p.y=datei.readInt();
						grund.grenze.addElement(p);
					}
				} 
				catch (EOFException e0) {
						try {
							datei.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						repaint();
				}
				catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		Rahmen.inFrame("Mährobi", new GUI(), 1200, 1800);
	}

}
