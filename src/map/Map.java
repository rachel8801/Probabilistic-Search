package map;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map{
	JFrame frame;
	private boolean frame_show;
	public int target_x, target_y;
	public int dim;
	public JPanel grid[][];
	public Cell grid_cell[][];
	public int type;
	public Target target;
	
	public enum Object{
		TARGET, AGENT
	}
	
	public Map(boolean window, int dim) {
		this.dim = dim;
		grid= new JPanel[dim][dim];
		grid_cell= new Cell[dim][dim];
		frame = new JFrame("Map");
		frame.setSize(700, 700);
		frame.setLayout(new GridLayout(dim, dim));
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				grid[i][j] = new JPanel();
		        int type = this.assignType(i, j);
		        //create new cell and puts it into the grid containing the cells
		        Cell c = new Cell(j, i, type, false);
		        grid_cell[i][j] = c;
	            frame.add(grid[i][j]);  
	         }
		}
		
		Random random = new Random();
		        
		target_x = random.nextInt(dim-1);
		target_y = random.nextInt(dim-1);
		target = new Target(target_x, target_y, this);
		grid[target_y][target_x].setBackground(Color.RED);
		//target.getLocation();
    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame_show = window;
	    if(window) {
	    	frame.setVisible(true);
	    }

	}  
	
	//updating java window
	public void object_move(Map.Object type, Cell src, Cell dest) {
		if(frame_show) {
			try {
			    Thread.sleep(0);
			} catch (InterruptedException e) {
			    // recommended because catching InterruptedException clears interrupt flag
			    Thread.currentThread().interrupt();
			    // you probably want to quit if the thread is interrupted
			    return;
			}
			
			int r = src.type;
			if(!target.getCurrentCell().equals(src) && type == Map.Object.AGENT) {
				
				//flat
		        if (r == 0) {
		        	 grid[src.getyCoor()][src.getxCoor()].setBackground(Color.WHITE);
		        }
		        //forest
		        else if(r ==1){
		        	grid[src.getyCoor()][src.getxCoor()].setBackground(Color.GREEN);
		        }
		        //hill
		        else if(r ==2){
		        	grid[src.getyCoor()][src.getxCoor()].setBackground(Color.YELLOW);
		        }
		        //cave
		        else if(r ==3){
		        	grid[src.getyCoor()][src.getxCoor()].setBackground(Color.BLACK);
		        }
			}
			
			if(type == Map.Object.AGENT && target.getCurrentCell().equals(src) ) {
				grid[src.getyCoor()][src.getxCoor()].setBackground(Color.red);
			}
	        grid[dest.getyCoor()][dest.getxCoor()].setBackground(type == Map.Object.AGENT? Color.magenta: Color.red);
		}
		
	}
	
	//assign type based on random prob generated and which section does it fall
	//in the cumulative probability
	public int assignType(int i , int j) {
		double r = Math.random()*1;
		if(r <=0.2) {
			//flat
			 grid[i][j].setBackground(Color.WHITE);
			 return 0;
		}
           //forest
		else if(r > 0.2 && r <= 0.5){
			grid[i][j].setBackground(Color.GREEN);
			return 1;
        }
           //hill
        else if(r > 0.5 && r <= 0.8){
           	grid[i][j].setBackground(Color.YELLOW);
           	return 2;
        }
        //cave
        else{
           	grid[i][j].setBackground(Color.BLACK);
           	return 3;
        }
	}
	
	public void resetMap() {
		if(frame_show) {
			grid[0][0].setBackground(Color.magenta);
			grid[target.getCurrentCell().getyCoor()][target.getCurrentCell().getxCoor()].setBackground(Color.red);
		
		}
	}
}
