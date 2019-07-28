package map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Agent {
	//init values will be p = 1/dim*dim, updated as agent traverses grid
	private double[][] belief;
	private Cell current_cell;
	private Cell previous_cell;
	private double updated_prob[][] = new double[Map.dim][Map.dim];
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
/*		//get a list of random possible moves
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
				if(c == current_cell){
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
	*/	
	}
	
	public void searchRule1() {
		double max = 0;

		for(int i = 0 ; i < Map.dim; i++)
		{
			for(int j = 0 ; j < Map.dim ; j++)
			{
				if(belief[i][j] > max)
				{
					current_cell.setxCoor(i);
					current_cell.setyCoor(j);
					max = belief[i][j];
				}
			}
		}
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
	
	public Cell rule1_q4(int x, int y) {
		Cell[][] grid = Map.grid_cell;
	   
		for(int i = 0; i< Map.dim; i++) {
			for(int j = 0; j < Map.dim; j++) {
				double updated_prob = belief[i][j];
				belief[x][y] = updated_prob/(manhattan(i,j,x,y) +1);
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
	
	public void searchRule2() {
		double max =0;
		
		for (int i = 0; i < Map.dim; i ++) {
			for(int j = 0; j < Map.dim; j++) {
				
				if(current_cell.type == 0) {
					updated_prob[i][j] = belief[i][j] * (1-current_cell.getFalseNegP());
				}
				else if(current_cell.type == 1) {
					updated_prob[i][j] = belief[i][j] * (1-current_cell.getFalseNegP());
				}
				else if(current_cell.type == 2) {
					updated_prob[i][j] = belief[i][j] * (1-current_cell.getFalseNegP());
				}
				else if(current_cell.type == 3) {
					updated_prob[i][j] = belief[i][j] * (1-current_cell.getFalseNegP());
				}
				
			}
		}
		for(int i = 0 ; i < Map.dim ; i++)
		{
			for(int j = 0 ; j < Map.dim ; j++)
			{
				if(updated_prob[i][j] > max)
				{
					max = updated_prob[i][j];
					current_cell.setxCoor(i);
					current_cell.setyCoor(j);
					
				}
			}
		}
	}
	/*
	 * This function is to update the belief state  system if we didn't fine the target in current cell
	 */
	public void update_state() {
		
		double falseNegP = current_cell.getFalseNegP();
		double norm = 1-belief[current_cell.getxCoor()][current_cell.getxCoor()]+belief[current_cell.getxCoor()][current_cell.getxCoor()]*falseNegP;
		
		for(int i = 0 ; i < Map.dim ; i++)
		{
			for(int j = 0 ; j < Map.dim ; j++)

			{
				//belief = new double[Map.dim][Map.dim];
				belief[i][j] = 1.0/Math.pow(Map.dim, 2);
				
				if(i == current_cell.getxCoor() && j == current_cell.getxCoor())
				{
					belief[i][j] = falseNegP * belief[current_cell.getxCoor()][current_cell.getxCoor()]/norm ;
				}
				else
				{
					belief[i][j] = belief[i][j]/norm;
						
				}							
				
			}
		}
		
	}
		
	
	public Cell getCurrentCell(){
		return current_cell;
	}
}
