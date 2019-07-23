package map;

public class Cell {
	public int xCoor, yCoor;
	static int dim = 50;
	public int type;
	static double prob[][] = new double[dim][dim];
	static int probType[][] = new int[dim][dim];
	static int target;
	
	public Cell(int x, int y) {
		this(x, y, 0, 0);

		
	}
	public Cell(int x, int y, int type, double Init_prob) {
		this.xCoor = x;
		this.yCoor = y;
		
	}
	
	public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}
	public int getType(){
		return type;
		
	}
	public static void main(String[] args) {
		
		

	}

}
