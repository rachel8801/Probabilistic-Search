package map;

import java.awt.Color;
import java.util.ArrayList;

public class Agent {
	//init values will be p = 1/dim*dim, updated as agent traverses grid
	private double[][] belief;
	private Cell current_cell;
	private Cell previous_cell;
	public Map map;
	private String name;
	
	public enum Rule{
		RULE_1, RULE_2
	}
	
	public Agent(Map map , String name) {
		this.name = name;
		this.map = map;
		int dim = map.dim;
		belief = new double[dim][dim];
		
		current_cell = map.grid_cell[0][0];
		map.grid[0][0].setBackground(Color.MAGENTA);
	}	
	/*
	public void move(Rule type) {
		//get a list of random possible moves
		//init array to store random possible moves
		ArrayList<Cell> possible_moves= new ArrayList<>();
				
		//init vars to store possible x and y
		int x, y;
		x = current_cell.getxCoor();
		y = current_cell.getyCoor();
		
		int new_x, new_y;
		//up
		new_x = x;
		new_y = y - 1;
				
		if(new_y < Map.grid_cell.length && new_y >= 0) {
			possible_moves.add(Map.grid_cell[new_y][new_x]);		
		}
				
		//left
		new_x = x + 1;
		new_y = y;
		if(new_x < Map.grid_cell.length && new_x >= 0) {
			possible_moves.add(Map.grid_cell[new_y][new_x]);
		}
				
		//down
		new_x = x;
		new_y = y + 1;
		if(new_y < Map.grid_cell.length && new_y >= 0) {
			possible_moves.add(Map.grid_cell[new_y][new_x]);		
		}
				
		//right
		new_x = x - 1;
		new_y = y;
		if(new_x < Map.grid_cell.length && new_x >= 0) {
			possible_moves.add(Map.grid_cell[new_y][new_x]);
		}
				
		if(type == Rule.RULE_1) {
			Collections.sort(possible_moves);
			//if the previous cell is not null, attempt to remove it from the list of possible moves to prevent loops
			if(previous_cell!= null) {
				possible_moves.remove(previous_cell);
			}
			//move on jpanel
			Map.object_move(Map.Object.AGENT, current_cell, possible_moves.get(0));
			previous_cell = current_cell;
			current_cell = possible_moves.get(0);
		}else {


					
			}
		}
		}
		
	}
	*/
	public void search(Rule rule) {
		System.out.println(name + " searching with " + rule);
	
		//reset belief table and map
		current_cell = map.grid_cell[0][0];
		for(int i = 0 ; i < map.dim ; i++) {
			for(int j = 0 ; j < map.dim ; j++) {
				belief[i][j] = 1.0/(map.dim*map.dim);
			}
		}
		map.resetMap();
		
		//init timeout counter to prevent infinite while loop
		int counter = 0;
		Cell[][] grid = map.grid_cell;
				
		//if target is in current cell, end search, print diagnosis.
		if(current_cell.attempt_search()) {
			System.out.println("Found target in " + counter + " steps at " + current_cell.coordinateToString());
			return;
		}
				
		//cell to hold next move
		Cell next_move = current_cell;
			
		//continuously search for cell until counter times up or until cell is found
		while(!current_cell.attempt_search() && (counter != grid.length*grid.length*grid.length)){
			update_state(rule);
					
			//search for a list of cells with highest belief probability
			//randomly choose from list
			double max = belief[0][0];
			ArrayList<Cell> possible_moves = new ArrayList<Cell>();
			for(int i = 0 ; i < map.dim; i++){
				for(int j = 0 ; j < map.dim ; j++){
					if(belief[i][j] > max){
						possible_moves.clear();
						next_move = map.grid_cell[i][j];
						max = belief[i][j];
						possible_moves.add(next_move);
					}else if(belief[i][j] == max){
						next_move = map.grid_cell[i][j];
						possible_moves.add(next_move);
					}
				}
			}
					
			int random_index = (int) (Math.random()*(possible_moves.size()-1));
			next_move = possible_moves.get(random_index);
					
			map.object_move(Map.Object.AGENT, current_cell, next_move);
			previous_cell = current_cell;
			current_cell= next_move;
			counter++;
		}
				
		if(counter == grid.length*grid.length*grid.length) {
			System.out.println(name + " Counter timed out.");
		}else {
			System.out.println(name + " found target in " + counter + " steps at " + current_cell.coordinateToString());
		}
	}
	
	public Cell rule1_q4(int x, int y) {
		
		// we need create a rule such that begin at (0,0), we randomly choose a check point, we calculate the manhattan distance from every cell to this point,
		// and norm the average utility: P(target in cell )* P(target found in cell | target is in cell) / manhattan dist
		//  and choose the maximum utility cell to move.
		// Note; I Haven't finish this function yet, you can continue
		// we apply this rule to rule 2 as well, and separately run it compared to original rule1 and 2
		Cell[][] grid = map.grid_cell;
		//check[0] = x;
	   
		for(int i = 0; i< map.dim; i++) {
			for(int j = 0; j < map.dim; j++) {
				 
				belief[x][y] = belief[i][j]/(manhattan(i,j,x,y) +1);
			}
			double max =0;
			if(belief[x][y] > max)
			{
				current_cell.setxCoor(x);
				current_cell.setyCoor(y);
				max = belief[x][y];
			}
		}
		return grid[x][y];

	}
	
	public int manhattan(int a, int b, int x, int y) {
		return Math.abs(a-x) + Math.abs(b-y);
	}
	
	
	/*
	 * This function is to update the belief state  system if we didn't fine the target in current cell
	 */
	private void update_state(Rule rule) {
		//get false negative and norm
		double falseNegP = current_cell.getFalseNegP();
		double norm = 1-belief[current_cell.getyCoor()][current_cell.getxCoor()]+belief[current_cell.getyCoor()][current_cell.getxCoor()]*falseNegP;
		
		//update the belief states
		for(int i = 0 ; i < map.dim ; i++){
			for(int j = 0 ; j < map.dim ; j++){
				
				if(i ==current_cell.getyCoor() && j == current_cell.getxCoor()){
					//get the new belief state for current cell
					belief[i][j] = falseNegP * belief[current_cell.getyCoor()][current_cell.getxCoor()]/norm ;
				} else {
					// get the new belief state for rest cell
					belief[i][j] = belief[i][j]/norm;
				 
				}
				
				//if the current rule being used is rule 2, multiply by false negative
				if(rule == Rule.RULE_2) {
					belief[i][j] *= (1 - map.grid_cell[i][j].getFalseNegP());
				}
			}
		}	
	}
		
	
	public Cell getCurrentCell(){
		return current_cell;
	}
}
