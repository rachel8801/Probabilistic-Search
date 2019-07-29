package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Target {
	private int x;
	private int y;
	private Cell current_cell;
	public Map map;
	
	public Target(int x, int y, Map map) {
		this.map = map;
		map.grid_cell[y][x].setTarget(true);
		current_cell = map.grid_cell[y][x];
		this.x = x;
		this.y = y;
		
	}
	
	public Cell getCurrentCell() {
		return current_cell;
	}
	
	public void getLocation() {
		String location ="";
		switch(current_cell.type) {
		case 0:
			location = "flat";
			break;
		case 1:
			location = "forest";
			break;
		case 2:
			location = "hill";
			break;
		case 3:
			location = "cave";
		}
		System.out.println("Target is in " + location + " ," + current_cell.coordinateToString());
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
		
		if(new_y < map.grid_cell.length && new_y >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);		
		}
		
		//left
		new_x = x + 1;
		new_y = y;
		if(new_x < map.grid_cell.length && new_x >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);		
			}
		
		//down
		new_x = x;
		new_y = y + 1;
		if(new_y < map.grid_cell.length && new_y >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);		
			}
		
		//right
		new_x = x - 1;
		new_y = y;
		if(new_x < map.grid_cell.length && new_x >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);
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
		map.object_move(Map.Object.TARGET, current_cell, r_cell);
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
