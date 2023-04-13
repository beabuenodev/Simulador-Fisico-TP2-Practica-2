package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup {
	
	private String id;
	private ForceLaws forcelaws;
	private List<Body> bodies;
	private List<Body> bodiesRO;	
	public BodiesGroup(String id, ForceLaws forcelaws) {
		if (id != null && forcelaws != null && id.trim().length()>0) {
			this.id = id;
			this.forcelaws = forcelaws;
			this.bodies = new ArrayList<Body>();
			this.bodiesRO = Collections.unmodifiableList(bodies);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	protected void addBody(Body b) {
		if (b == null || bodies.contains(b))
			throw new IllegalArgumentException();
		for (Body bi:bodies) {
			if (bi.getId() == b.getId())
				throw new IllegalArgumentException();
		}
		bodies.add(b);
	}
	
	protected void advance(double dt) {
		if (dt > 0) {
			for (Body b: bodies) 
				b.resetForce();
			forcelaws.apply(bodies);
			for (Body b: bodies)
				b.advance(dt);
		} else 
			throw new IllegalArgumentException();
	}
	
	public JSONObject getState() {
		JSONObject bodyjson = new JSONObject();
		JSONArray arrayjson = new JSONArray();
		bodyjson.put("id", id);
		for (Body bi:bodies) 
			arrayjson.put(bi.getState());
		bodyjson.put("bodies", arrayjson);
		return bodyjson;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setForceLaws(ForceLaws forcelaws) {
		if (forcelaws == null)
			throw new IllegalArgumentException();
		this.forcelaws = forcelaws;
	}
	
	public String getForceLawsInfo() {
		return forcelaws.toString();
	}

	
}
