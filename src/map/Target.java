package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Target {
	private int x;
	private int y;
	private Cell current_cell;
	
	public Target(int x, int y) {
		Map.grid_cell[y][x].setTarget(true);
		current_cell = Map.grid_cell[y][x];
		this.x = x;
		this.y = y;
		
	}
	
	public ArrayList<Integer> move() {
		//get a list of random possible moves
		//init array to store random possible moves
		ArrayList<Cell> possible_moves= new ArrayList<>();
		
		//init vars to store possible x and y
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
			possible_moves.add(Map.grid_cell[new_y][new_x]);		}
		
		//down
		new_x = x;
		new_y = y + 1;
		if(new_y < Map.grid_cell.length && new_y >= 0) {
			possible_moves.add(Map.grid_cell[new_y][new_x]);		}
		
		//right
		new_x = x - 1;
		new_y = y;
		if(new_x < Map.grid_cell.length && new_x >= 0) {
			possible_moves.add(Map.grid_cell[new_y][new_x]);
		}
		
		//compute random math to get random move
		Random r = new Random();
		int r_int = r.nextInt(possible_moves.size());
		Cell r_cell = possible_moves.get(r_int);
		
		//add random move type to report
		ArrayList<Integer> report = new ArrayList<>();
		report.add(current_cell.type);
		report.add(r_cell.type);
		
		//move the target and return the report
		Map.target_move(current_cell, r_cell);
		current_cell.setTarget(false);
		r_cell.setTarget(true);
		current_cell = r_cell;
		this.x = current_cell.getxCoor();
		this.y = current_cell.getyCoor();
		
		//sort in order of type to keep anonimity of the the previous and current position of the target.
		Collections.sort(report);
		return report;
	}
}
