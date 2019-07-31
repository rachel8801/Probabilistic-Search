package map;

import java.util.ArrayList;

public class Cell implements Comparable<Cell>{
	private int xCoor, yCoor;
	public int type;
	private double false_neg_p;
	private boolean target;
	public int density;
	
	
	public Cell(int x, int y, int type, boolean target) {
		this.xCoor = x;
		this.yCoor = y;
		this.target = target;
		this.type = type;
		density = 0;
		switch(type) {
			//flat
			case 0:
				false_neg_p = 0.1;
				break;
			//forest	
			case 1:
				false_neg_p = 0.3;
				break;
			//hill
			case 2:
				false_neg_p = 0.7;
				break;
			//cave
			case 3:
				false_neg_p = 0.9;
				break;
			default:
				
		}
		
	}
	
	public void setTarget(boolean t){
		target = t;
	}
	
	public boolean attempt_search() {
		double p = Math.random()*1;
		if(p > false_neg_p) {
			return target;
		}
		density ++;
		if(density == 250) {
			density = density/2;
		}
		return false;
	}
	
	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	public int getxCoor() {
		return xCoor;
	}


	public int getyCoor() {
		return yCoor;
	}
	
	public String coordinateToString() {
		return "["+ xCoor +", "+ yCoor+"]";
	}


	public int getType(){
		return type;
		
	}
	
	public double getFalseNegP(){
		return false_neg_p;
		
	}
	
	public ArrayList<Cell> getNeighbours(Map map){
		//get a list of random possible moves
				//init array to store random possible moves
				ArrayList<Cell> possible_moves= new ArrayList<>();
						
				//init vars to store possible x and y
				int x, y;
				x = this.getxCoor();
				y = this.getyCoor();
				
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
				
				return possible_moves;
	}
	@Override
	public String toString() {
		if(target){
			return "T ";
		}
		return type + " ";
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Cell) {
			Cell c = (Cell)o;
			if(c.getxCoor() == this.getxCoor() && c.getyCoor() == this.getyCoor()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Cell c) {
		
		
		return this.false_neg_p - c.getFalseNegP() > 0 ? 1 : -1 ;
	}
}
