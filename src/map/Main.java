package map;
import javax.swing.JFrame;

import map.Main;
import map.Screen;


public class Main{

	public Main() {
	    
	    JFrame frame = new JFrame();
	    Screen screen = new Screen();
	   
	    frame.add(screen);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("Map");
	    frame.setResizable(false);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);    
	   
	}

	public static void main(String[] args) {
		  new Main();

	}

}
