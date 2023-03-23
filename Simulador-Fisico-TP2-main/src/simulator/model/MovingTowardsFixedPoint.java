package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		if (g <= 0 || c == null)
			throw new IllegalArgumentException();
		this.c = c;
		this.g = g;
	}

	@Override
	public void apply(List<Body> bs) {
		for (Body bi : bs) {
			Vector2D fi = c.minus(bi.getPosition().direction().scale(g * bi.getMass()));
			bi.addForce(fi);
		}
	}

}
