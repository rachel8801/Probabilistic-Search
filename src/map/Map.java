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
	public Map() {
		frame = new JFrame("Map");
		frame.setSize(700, 700);
		
		frame.setLayout(new GridLayout(50, 50));
		   for (int i = 0; i < 50; i++) {
		        for (int j = 0; j < 50; j++) {
		        	
		        	prob[i][j] = 1/(dim*dim);
		        	Random random = new Random();

		            grid[i][j] = new JPanel();
			      
//			 		target_x = random.nextInt(dim-1);
//			   		target_y = random.nextInt(dim-1);
//			   			
//			   		grid[target_x][target_y].setBackground(Color.RED);

		        	int r = random.nextInt(4);
	          
		            if (r == 0) {
			               grid[i][j].setBackground(Color.WHITE);
			               prob[i][j] = 0.2;
		            }

		            else if(r ==1){
		            	grid[i][j].setBackground(Color.GREEN);
		            	prob[i][j] = 0.2;
		            	
		            }
		            else if(r ==2){
		            	grid[i][j].setBackground(Color.YELLOW);
		            	prob[i][j] = 0.3;
		            	
		            }
		            else if(r ==3){
		            	grid[i][j].setBackground(Color.BLACK);
		            	prob[i][j] = 0.3;
		            	
		            }
		

		            frame.add(grid[i][j]);
		        }
	
		}
       	
  
    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}

		public static void main(String[] args) {
		    new Map();
		}
		
	

}
