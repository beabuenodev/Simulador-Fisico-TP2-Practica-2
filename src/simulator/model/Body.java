package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {
	
	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected double m;
	
	public Body (String id, String gid, Vector2D p, Vector2D v, double m) {
		if ((id != null && gid != null && p != null && v != null) 
				&& (id.trim().length() > 0 || gid.trim().length() > 0) 
				&& (m > 0)
				&& !id.isBlank() && !id.isEmpty() && !gid.isBlank() && !gid.isEmpty()) {
			this.id = id;
			this.gid = gid;
			this.f = new Vector2D();
			this.v = v;
			this.p = p;
			this.m = m;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	abstract void advance (double dt);
	
	protected void addForce(Vector2D f) {
		this.f = this.f.plus(f);
	}
	
	protected void resetForce() {
		this.f = new Vector2D();
	}
	
	public JSONObject getState() {
		JSONObject bodyjson = new JSONObject();
		bodyjson.put("p", p.asJSONArray());
		bodyjson.put("v", v.asJSONArray());
		bodyjson.put("f", f.asJSONArray());
		bodyjson.put("id", id);
		bodyjson.put("m", m);
		return bodyjson;
	}
	
	public String toString() {
		return getState().toString();
	}

	public String getId() {
		return id;
	}

	public String getgId() {
		return gid;
	}

	public Vector2D getVelocity() {
		return v;
	}

	public Vector2D getForce() {
		return f;
	}

	public Vector2D getPosition() {
		return p;
	}

	public double getMass() {
		return m;
	}

	
}
