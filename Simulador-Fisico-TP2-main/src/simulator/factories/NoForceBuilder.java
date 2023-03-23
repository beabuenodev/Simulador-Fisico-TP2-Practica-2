package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	public NoForceBuilder() {
		super("nf", "No Force");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}
	
	public JSONObject getInfo() {
		JSONObject json1 = new JSONObject();
		json1.put("type", "nf");
		json1.put("desc", "No force");
		JSONObject json2 = new JSONObject();
		json1.put("data", json2);
		return json1;
	}

}
