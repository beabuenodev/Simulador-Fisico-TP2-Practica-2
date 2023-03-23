package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {

	public MovingBody(String id, String gid, Vector2D p, Vector2D v, double m) {
		super(id, gid, p, v, m);
	}

	@Override
	void advance(double dt) {
		
		Vector2D a = new Vector2D();
		
		if (m != 0) {
			double ax = f.getX() / m;
			double ay = f.getY() / m;
			
			a = new Vector2D(ax, ay);
		}
		
		double newpx = p.getX() + (v.getX()*dt + (a.getX()*(0.5*dt*dt)));
		double newpy = p.getY() + (v.getY()*dt + (a.getY()*(0.5*dt*dt)));
		p = new Vector2D(newpx, newpy);
		
		double newvx = v.getX() + a.getX()*dt;
		double newvy = v.getY() + a.getY()*dt;
		v = new Vector2D(newvx, newvy);
		
	}

}
