package map;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	private int xCoor, yCoor, dim;

	public Cell(int xCoor, int yCoor, int cellSize) {
		super();
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		dim = cellSize;
	}
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(xCoor * dim, yCoor * dim, dim, dim);
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
	

}
