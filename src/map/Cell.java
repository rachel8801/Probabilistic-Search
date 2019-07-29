package map;

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
