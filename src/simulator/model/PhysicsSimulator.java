package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver>{
	
	private double tpaso;
	private ForceLaws forcelaws;
	private Map<String,BodiesGroup> groups;
	private List<String> idorder;
	private double t;
	private List<SimulatorObserver> observers;
	private Map<String, BodiesGroup> groupsRO;
	
	public PhysicsSimulator(ForceLaws forcelaws, double tpaso) {
		if (tpaso > 0 && forcelaws != null) {
			this.tpaso = tpaso;
			this.forcelaws = forcelaws;
			this.groups = new HashMap<String,BodiesGroup>();
			this.idorder = new ArrayList<String>();
			this.observers = new ArrayList<SimulatorObserver>();
			this.groupsRO = Collections.unmodifiableMap(groups);
			t = 0.0;
		} else
			throw new IllegalArgumentException();
	}
	
	public void advance() {
		for (Entry<String, BodiesGroup> entry: groups.entrySet()) {
			entry.getValue().advance(tpaso);
		}
		t += tpaso;
		for (SimulatorObserver observer:observers) {
			observer.onAdvance(groups, tpaso);
		}
	}
	
	public void reset() {
		groups.clear();
		idorder.clear();
		t = 0.0;
		for (SimulatorObserver observer:observers) {
			observer.onReset(groups, t, tpaso);
		}
	}
	
	public void addGroup(String id) {
		if (groups.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		BodiesGroup group = new BodiesGroup(id, forcelaws);
		idorder.add(id);
		groups.put(id, group);
		for (SimulatorObserver observer:observers) {
			observer.onGroupAdded(groups, group);
		}
	}
	
	public void addBody(Body b) {
		if (!groups.containsKey(b.getgId())) {
			throw new IllegalArgumentException();
		}
		groups.get(b.getgId()).addBody(b);
		for (SimulatorObserver observer:observers) {
			observer.onBodyAdded(groups, b);
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) {
		if (!groups.containsKey(id)) {
			throw new IllegalArgumentException();
		} 
		for (Entry<String, BodiesGroup> entry: groups.entrySet()) {
			if (entry.getKey() ==  id) {
				entry.getValue().setForceLaws(fl);
			}
			for (SimulatorObserver observer:observers) {
				observer.onForceLawsChanged(entry.getValue());
			}
		}

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
	
	public void setDeltaTime(double tpaso) {
		this.tpaso = tpaso;
		for (SimulatorObserver observer:observers) {
			observer.onDeltaTimeChanged(tpaso);
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		observers.add(o);
		o.onRegister(groups, t, tpaso);
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		observers.remove(o);
	}
	
}
