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
	JPanel grid[][]= new JPanel[dim][dim];
	static double prob[][] = new double[dim][dim];
	public int type;
	
	
	public Map() {
		frame = new JFrame("Map");
		frame.setSize(700, 700);
		frame.setLayout(new GridLayout(50, 50));
		
		   for (int i = 0; i < dim; i++) {
		        for (int j = 0; j < dim; j++) {
		        	
		        	prob[i][j] = 1/Math.pow(dim, 2);
			        //System.out.println("initial prob is " + prob[i][j]);
		        	int count=0;
		        	Random random = new Random();

		            grid[i][j] = new JPanel();

		        	int r = random.nextInt(4);
	          
		            if (r == 0) {
		            	while(count < (2500 * 0.2)) {
			               grid[i][j].setBackground(Color.WHITE);
			               count++;
		            	}
			               type = r;
		            	//System.out.println("1st prob is " + prob[i][j]+ "count is "+count);
		            }

		            else if(r ==1){
		            	while(count < (2500*0.3)) {
		            	grid[i][j].setBackground(Color.GREEN);
		            	count++;
		            	
		            	}
		            	type = r;
		            }
		            else if(r ==2){
		            	while(count < (2500*0.3)) {
		            	grid[i][j].setBackground(Color.YELLOW);
		            	count++;
		            	}
		            	type = r;
		            	//System.out.println("2nd prob is " + prob[i][j]+ "count is "+count);
		            }
		            else if(r ==3){
		            	while(count < (2500*0.3)) {
		            	grid[i][j].setBackground(Color.BLACK);
		            	count++;
		            	}
		            	type = r;
		            }

		            frame.add(grid[i][j]);  
		        }
		   }
		
		        	Random random = new Random();
		        
			 		target_x = random.nextInt(dim-1);
			   		target_y = random.nextInt(dim-1);
			   		grid[target_x][target_y].setBackground(Color.RED);
  
    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	}
	    public void getTarget(int x, int y) {    	
	    	x = target_x;
	    	y = target_y;
	    }
	    public void getType(int tp) {
	    	tp = type;
	    }

		public static void main(String[] args) {
		    new Map();
		}
		
	

}
