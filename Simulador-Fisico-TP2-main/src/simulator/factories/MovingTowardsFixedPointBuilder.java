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

}
