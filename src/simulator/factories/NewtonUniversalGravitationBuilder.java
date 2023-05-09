package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of universal gravitation");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if (data.has("G")) {
			return new NewtonUniversalGravitation(data.getDouble("G"));
		}
			return new NewtonUniversalGravitation(6.67E-11);
	}
	
	@Override
	protected void fillData(JSONObject data) {
		data.put("G", "the gravitational constant (a number)");
	}
	
	public JSONObject getInfo() {
		JSONObject json1 = new JSONObject();
		json1.put("type", "nlug");
		json1.put("desc", "Newton's law of universal gravitation");
		JSONObject json2 = new JSONObject();
		fillData(json2);
		json1.put("data", json2);
		return json1;
	}

}
