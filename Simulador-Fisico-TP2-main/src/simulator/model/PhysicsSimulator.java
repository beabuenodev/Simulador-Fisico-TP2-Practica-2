package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private double tpaso;
	private ForceLaws forcelaws;
	private Map<String,BodiesGroup> groups;
	private List<String> idorder;
	private double t;
	
	public PhysicsSimulator(ForceLaws forcelaws, double tpaso) {
		if (tpaso > 0 && forcelaws != null) {
			this.tpaso = tpaso;
			this.forcelaws = forcelaws;
			this.groups = new HashMap<String,BodiesGroup>();
			this.idorder = new ArrayList<String>();
			t = 0.0;
		} else
			throw new IllegalArgumentException();
	}
	
	public void advance() {
		for (Entry<String, BodiesGroup> entry: groups.entrySet()) {
			entry.getValue().advance(tpaso);
		}
		t += tpaso;
	}
	
	public void addGroup(String id) {
		if (groups.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		BodiesGroup group = new BodiesGroup(id, forcelaws);
		idorder.add(id);
		groups.put(id, group);
	}
	
	public void addBody(Body b) {
		if (groups.containsKey(b.getgId())) {
			groups.get(b.getgId()).addBody(b);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) {
		if (groups.containsKey(id)) {
			for (Entry<String, BodiesGroup> entry: groups.entrySet()) {
				if (entry.getKey() ==  id) {
					entry.getValue().setForceLaws(fl);
				}
			}
		} else
			throw new IllegalArgumentException();
	}
	
	public JSONObject getState() {
		JSONObject simjson = new JSONObject();
		JSONArray auxjson = new JSONArray();
		for (String key: idorder) {
			auxjson.put(groups.get(key).getState());
		}
		simjson.put("groups", auxjson);
		simjson.put("time", t);
		return simjson;
	}
	
	public String toString() {
		return getState().toString();
	}
	
}
