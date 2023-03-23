package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	
	private double G;
	
	public NewtonUniversalGravitation (double G) {
		if (G > 0) {
			this.G = G;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void apply(List<Body> bs) {
		for (Body bi : bs) {
			Vector2D fi = new Vector2D();
			for (Body bj : bs) {
				if (!(bi == bj)) {
					double p = G*bi.getMass()*bj.getMass();
					double d = bj.getPosition().distanceTo(bi.getPosition());
					d = d * d;
					double f = p/d;
					fi = bj.getPosition().minus(bi.getPosition()).direction().scale(f);
					bi.addForce(fi);
				}
			}
		}
	}

}
