import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class RobiMitSensor extends Robi1 {

	Point sensor;
	int vLinks, vRechts;

	RobiMitSensor(Grundstueck1 gs, double maxV, double winkel, int posX, int posY, Color f, int b, int h) {
		super(gs, maxV, winkel, posX, posY, f, b, h);
		vLinks = 25;
		vRechts = 25;
		sensor = new Point(posX,posY);
	}

	// bestimmt die Entfernung zur Grenze
	double getDistance() {
		double dist = 0;
		for (int i=0;i<grund.grenze.size();i++) {
			Point p = grund.grenze.elementAt(i);
			double	d = Math.sqrt((p.x-sensor.x)*(p.x-sensor.x)+
					(p.y-sensor.y)*(p.y-sensor.y));
			if (i==0)
				dist = d;
			else 
				if (d < dist) 
					dist = d;
		}
		return dist;
	}

	public void run() {
		long zeit1=-1;
		while (true) {
			try {
				sleep(20);
				if (!doMove)
					continue;

				double dist = getDistance();

				if (dist < 10) {
					if (zeit1 < 0) {
						double richtungNeu=0, cos1=0, cos2=0;
						double z = Math.random();
						richtung += Math.PI+(z-0.5)*Math.PI/10;
						zeit1= System.currentTimeMillis();
					}
					else 
						if (System.currentTimeMillis()-zeit1 > 500) 	
							zeit1 = -1;
				}
				fahren(vLinks,vRechts);
				grund.repaint();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void zeichnen(Graphics g, Polygon p) {
		super.zeichnen(g, p);
		sensor.x = (p.xpoints[1]+p.xpoints[2])/2;
		sensor.y =  (p.ypoints[1]+p.ypoints[2])/2;
		g.setColor(Color.RED);
		g.fillOval(sensor.x-d.height/10, sensor.y-d.height/10, d.height/5,d.height/5);
	}

}
