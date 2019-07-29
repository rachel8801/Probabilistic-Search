package map;

public class MapDriver {
	//set true or false for window to be shown
	
	public static void main(String[] args) {
		Map map1 = new Map(true, 5);
		Agent agent = new Agent(map1, "Agent 1");
		agent.search(Agent.Rule.RULE_1, true);
		//agent.search(Agent.Rule.RULE_2, false);
	}
}
