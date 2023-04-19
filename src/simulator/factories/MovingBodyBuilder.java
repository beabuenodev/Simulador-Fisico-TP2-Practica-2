package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {

	public MovingBodyBuilder() {
		super("mv_body", "Moving Body");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		
		if (!data.has("id"))
			throw new IllegalArgumentException("don't have an id for MovingBody");
		String id = data.getString("id");
		if (id == null) throw new IllegalArgumentException("id can't be null on MovingBody");
		
		if (!data.has("gid"))
			throw new IllegalArgumentException("don't have an gid for MovingBody");
		String gid = data.getString("gid");
		if (gid == null) throw new IllegalArgumentException("gid can't be null on MovingBody");
		
		if (!data.has("p") || data.getJSONArray("p").length() != 2)
			throw new IllegalArgumentException("don't have p for MovingBody");
		Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
		
		if (!data.has("v") || data.getJSONArray("v").length() != 2)
			throw new IllegalArgumentException("don't have v for MovingBody");
		Vector2D v = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
		
		if (!data.has("m")) throw new IllegalArgumentException("don't have m for MovingBody");
		double m = data.getDouble("m");
		if (m <= 0) 
			throw new IllegalArgumentException("m can't be negative for MovingBody");
		
		
		return new MovingBody(id, gid, p, v, m);
	}

}
