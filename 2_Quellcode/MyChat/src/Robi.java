import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Robi extends Thread {

	double richtung;
	double maxStep; // bestimmt, wie schnell sich der Roboter bewegen kann
	Color farbe;
	Point position; // bezieht sich auf den Mittelpunkt des Roboters
	
	Dimension d; // Breite und Höhe des Roboters
	
	boolean doMove; 
	Grundstueck1 grund;
	int vLinks, vRechts;
	
	Robi(Grundstueck1 grd, double maxV, double winkel, int posX, int posY, Color f, int b, int h) {
		maxStep = maxV;
		richtung = winkel;
		farbe = f;
		position = new Point(posX,posY);
		d = new Dimension(b,h);
		doMove = false;
		start();
		grund = grd;
		vLinks = 20;
		vRechts = 20;
	}
	
	public void run() {
		while (true) {
			try {
				sleep(20);
				if (!doMove)
					continue;
				fahren(vLinks, vRechts);
				grund.repaint();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	void fahren(double speedL, double speedR) {
		double spd = Math.sqrt(speedL*speedL+speedR*speedR);
		if ( spd < 1e-2)
			return;
        richtung = richtung-0.1*(Math.atan2(speedR,speedL)-Math.PI/4);
        position.x += maxStep*spd*Math.cos(richtung);
        position.y += maxStep*spd*Math.sin(richtung);
	}
	
	Polygon ausrichten() {
		// Drehen um den Winkel richtung
		// ursprüngliche Ecken: (0,0), (b,0), (b,h), (0,h)
		// Mittelpunkt: (b/2, h/2)
		// Vektoren von der Mitte zu den Eckpunkten:
		double b = d.width;
		double h = d.height;
		double [][] ecken = {{-b/2,-h/2},{ b/2,-h/2}, { b/2, h/2}, {-b/2, h/2}};
		
		double [][] eckenRot = new double[4][2];
		
		for (int i=0;i<4;i++) {
			eckenRot[i][0]=Math.cos(richtung)*ecken[i][0]-Math.sin(richtung)*ecken[i][1];
			eckenRot[i][1]=Math.sin(richtung)*ecken[i][0]+Math.cos(richtung)*ecken[i][1];
		}

		for (int i=0;i<4;i++) {
			eckenRot[i][0]+=position.x;
			eckenRot[i][1]+=position.y;
		}
		
		Polygon p = new Polygon();
		for (int i=0;i<4;i++)	
			p.addPoint((int)eckenRot[i][0], (int)eckenRot[i][1]);
		
		return p;
	}
	
	void zeichnen(Graphics g, Polygon p) {
		g.setColor(farbe);
		g.fillPolygon(p);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.DARK_GRAY);
		
		g2.drawLine(p.xpoints[1],p.ypoints[1], p.xpoints[2],p.ypoints[2]);
	}
	
}
