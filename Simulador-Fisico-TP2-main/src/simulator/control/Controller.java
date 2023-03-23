package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	InputStream input;
	OutputStream output;

	PhysicsSimulator simulator;
	Factory<Body> bodyfactory;
	Factory<ForceLaws> forcelawsfactory;
	
	
	public Controller (PhysicsSimulator simulator, Factory<Body> bodyfactory, Factory<ForceLaws> forcelawsfactory) {
		this.simulator = simulator;
		this.bodyfactory = bodyfactory;
		this.forcelawsfactory = forcelawsfactory;
	}
	
	public void loadData (InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		JSONArray gids = jsonInput.getJSONArray("groups");
		for (int i = 0; i < gids.length(); i++) {
			simulator.addGroup(gids.getString(i));
		}
		
		if (jsonInput.has("laws")) {
			JSONArray ls = jsonInput.getJSONArray("laws");
			for (int i = 0; i < ls.length(); i++) {
				simulator.setForceLaws(ls.getJSONObject(i).getString("id"), forcelawsfactory.createInstance(ls.getJSONObject(i).getJSONObject("laws")));
			}
		}
		
		JSONArray bbs = jsonInput.getJSONArray("bodies");
		for (int i = 0; i < bbs.length(); i++) {
			simulator.addBody(bodyfactory.createInstance(bbs.getJSONObject(i)));
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println("\"states\": [");
		
		JSONObject state1 = simulator.getState();
		p.println(state1 + ",");
		for (int i = 0; i < n; i++) {
			simulator.advance();
			JSONObject state = simulator.getState();
			p.println(state + ",");
		}
		
		p.println("]");
		p.println("}");
	}
}
