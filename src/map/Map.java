package map;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map{
	JFrame frame;
	static int target_x, target_y;
	static int dim = 50;
	static JPanel grid[][]= new JPanel[dim][dim];
	public static Cell grid_cell[][]= new Cell[dim][dim];
	static double prob[][] = new double[dim][dim];
	public int type;
	public static Target target;
	
	public Map() {
		frame = new JFrame("Map");
		frame.setSize(700, 700);
		frame.setLayout(new GridLayout(dim, dim));
		
		   for (int i = 0; i < dim; i++) {
		        for (int j = 0; j < dim; j++) {
		        	
		        	prob[i][j] = 1/Math.pow(dim, 2);
		        	int count=0;
		        	Random random = new Random();

		            grid[i][j] = new JPanel();

		        	int r = random.nextInt(4);
		        	//flat
		            if (r == 0) {
		            	while(count < (2500 * 0.2)) {
			               grid[i][j].setBackground(Color.WHITE);
			               count++;
		            	}
			               type = r;
		            }
		            //forest
		            else if(r ==1){
		            	while(count < (2500*0.3)) {
		            	grid[i][j].setBackground(Color.GREEN);
		            	count++;
		            	
		            	}
		            	type = r;
		            }
		            //hill
		            else if(r ==2){
		            	while(count < (2500*0.3)) {
		            	grid[i][j].setBackground(Color.YELLOW);
		            	count++;
		            	}
		            	type = r;
		            	}
		            //cave
		            else if(r ==3){
		            	while(count < (2500*0.2)) {
		            	grid[i][j].setBackground(Color.BLACK);
		            	count++;
		            	}
		            	type = r;
		            }
		            
		            //create new cell and puts it into the grid containing the cells
		            Cell c = new Cell(j, i, r, false);
		            grid_cell[i][j] = c;
		            
		            frame.add(grid[i][j]);  
		        }
		   }
		
		        	Random random = new Random();
		        
			 		target_x = random.nextInt(dim-1);
			   		target_y = random.nextInt(dim-1);
			   		target = new Target(target_x, target_y);
			   		grid[target_y][target_x].setBackground(Color.RED);
  
    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	}  
	
	//updating java window
	public static void target_move(Cell src, Cell dest) {
		try {
		    Thread.sleep(50);
		} catch (InterruptedException e) {
		    // recommended because catching InterruptedException clears interrupt flag
		    Thread.currentThread().interrupt();
		    // you probably want to quit if the thread is interrupted
		    return;
		}
		
		int r = src.type;
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
        grid[dest.getyCoor()][dest.getxCoor()].setBackground(Color.RED);
		
	}

	public static void main(String[] args) {
		new Map();
		
		//tests for moving target
		for(int i = 0; i < 50 ; i++) {
			target.move();
		}
	}
	
}
