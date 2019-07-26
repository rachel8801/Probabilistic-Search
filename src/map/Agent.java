package map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Agent {
	//init values will be p = 1/dim*dim, updated as agent traverses grid
	private double[][] belief;
	private Cell current_cell;
	private Cell previous_cell;
	
	private enum Rule{
		RULE_1, RULE_2
	}
	
	public Agent() {
		int dim = Map.dim;
		belief = new double[dim][dim];
		//assigning prior probabilities
		for(double[] arr : belief) {
			for(double c : arr) {
				c = 1.0/(dim*dim);
			}
		}
		current_cell = Map.grid_cell[0][0];
		Map.grid[0][0].setBackground(Color.MAGENTA);
	}
	
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
			//use belief system here
			//sum used to calculate normalization constant for belief state
			double sum=0;
			for(double[] arr : belief) {
			for(double c : arr) {
				if(c==current_cell){
					c=current_cell.type*c
				} //else belief is only effected by normalization constant
				sum=sum+c;
			}
		}
			double norm=1/sum; //calculate normalization constant
			for(double[] arr : belief) {
			for(double c : arr) {
				c=c*(1/norm) //normalize all belief states
					
			}
		}
		}
		
	}
	
	public void searchRule1() {
		int counter = 0;
		Cell[][] grid = Map.grid_cell;
		if(current_cell.attempt_search()) {
			System.out.println("Found target in " + counter + " steps at " + current_cell.coordinateToString());
			return;
		}
		
		while(!current_cell.attempt_search() && (counter != grid.length*grid.length*grid.length)){
			move(Rule.RULE_1);
			counter++;
		}
		
		if(counter == grid.length*grid.length*grid.length) {
			System.out.println("Counter timed out.");
		}else {
			System.out.println("Found target in " + counter + " steps at " + current_cell.coordinateToString());
		}
	}
	
	public boolean searchRule2() {
		
		return false;
	}
	
	public Cell getCurrentCell(){
		return current_cell;
	}
}
