package map;

public class MapDriver {
	//set true or false for window to be shown
	
	public static void main(String[] args) {
		
		Map[] maps = new Map[50];
		for(int i = 0; i < 50 ; i ++) {
			maps[i] = new Map(false, 50);
		}
		
	
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < 50 ; i++) {
			Agent agent = new Agent(maps[i], "Agent 1");
			agent.search(Agent.Rule.RULE_1, false, true);
		}
		long end = System.currentTimeMillis();
		System.out.println("average round = " + (end - start)/50);
		
		long start1 = System.currentTimeMillis();
		for(int i = 0 ; i < 50 ; i++) {
			Agent agent = new Agent(maps[i], "Agent 1");
			agent.search(Agent.Rule.RULE_2, false, true);
		}
		long end1 = System.currentTimeMillis();
		System.out.println("average round = " + (end1 - start1)/50);
		
		long start2 = System.currentTimeMillis();
		for(int i = 0 ; i < 50 ; i++) {
			Agent agent = new Agent(maps[i], "Agent 1");
			agent.search(Agent.Rule.RULE_1, true, true);
		}
		long end2 = System.currentTimeMillis();
		System.out.println("average round = " + (end2 - start2)/50);
		
		long start3 = System.currentTimeMillis();
		for(int i = 0 ; i < 50 ; i++) {
			Agent agent = new Agent(maps[i], "Agent 1");
			agent.search(Agent.Rule.RULE_2, true, true);
		}
		long end3 = System.currentTimeMillis();
		System.out.println("average round = " + (end3 - start3)/50);
		
	}
}
