package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double G;
		Vector2D c;
		
		if(data.has("c")) 
			c = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
		else
			c = new Vector2D();
		
		if(data.has("g")) 
			G = data.getDouble("g");
		else
			G = 9.81;
		
		return new MovingTowardsFixedPoint(c,G);
	}
	
	@Override
	public void fillData(JSONObject json2) {
		json2.put("c", "the point towards which bodies move (e.g., [100.0,50.0])");
		json2.put("g", "the length of the acceleration vector (a number)");
	}
	
	
	public JSONObject getInfo() {
		JSONObject json1 = new JSONObject();
		json1.put("type", "mtfp");
		json1.put("desc", "Moving towards a fixed point");
		JSONObject json2 = new JSONObject();
		fillData(json2);
		json1.put("data", json2);
		return json1;
	}

}
