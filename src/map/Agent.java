package map;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Agent {
	// init values will be p = 1/dim*dim, updated as agent traverses grid
	private double[][] belief;
	private double[][] move_belief;
	private Cell current_cell;
	private Cell previous_cell;
	public Map map;
	private String name;
	public ArrayList<Integer> target_report;

	public enum Rule {
		RULE_1, RULE_2
	}

	public Agent(Map map, String name) {
		this.name = name;
		this.map = map;
		int dim = map.dim;
		belief = new double[dim][dim];
		move_belief = new double[dim][dim];
		current_cell = map.grid_cell[0][0];
		map.grid[0][0].setBackground(Color.MAGENTA);
	}

	public Cell move(Cell target) {
		// get a list of random possible moves
		// init array to store random possible moves
		ArrayList<Cell> possible_moves = new ArrayList<>();

		// init vars to store possible x and y
		int x, y;
		x = current_cell.getxCoor();
		y = current_cell.getyCoor();

		int new_x, new_y;
		// up
		new_x = x;
		new_y = y - 1;

		if (new_y < map.grid_cell.length && new_y >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);
		}

		// left
		new_x = x + 1;
		new_y = y;
		if (new_x < map.grid_cell.length && new_x >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);
		}

		// down
		new_x = x;
		new_y = y + 1;
		if (new_y < map.grid_cell.length && new_y >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);
		}

		// right
		new_x = x - 1;
		new_y = y;
		if (new_x < map.grid_cell.length && new_x >= 0) {
			possible_moves.add(map.grid_cell[new_y][new_x]);
		}

		// get best move
		Cell next_move = possible_moves.get(0);
		double min = manhattan(next_move.getxCoor(), next_move.getyCoor(), target.getxCoor(), target.getyCoor());
		for (int i = 0; i < possible_moves.size(); i++) {
			if (manhattan(possible_moves.get(i).getxCoor(), possible_moves.get(i).getyCoor(), target.getxCoor(),
					target.getyCoor()) < min) {
				min = manhattan(possible_moves.get(i).getxCoor(), possible_moves.get(i).getyCoor(), target.getxCoor(),
						target.getyCoor());
				next_move = possible_moves.get(i);
			} else if (manhattan(possible_moves.get(i).getxCoor(), possible_moves.get(i).getyCoor(), target.getxCoor(),
					target.getyCoor()) == min) {
				if (belief[possible_moves.get(i).getyCoor()][possible_moves.get(i)
						.getxCoor()] > belief[next_move.getyCoor()][next_move.getxCoor()]) {
					next_move = possible_moves.get(i);
				}
			}
		}
		return next_move;
	}

	private void target_move_report() {
		ArrayList<Integer> current_report = map.target.move();
		if (target_report == null) {
			target_report = current_report;
		} else {
			int type = 10;
			for (int t : current_report) {
				if (!target_report.contains(t)) {
					type = t;
				}
			}
			if (type == 10) {
				target_report = current_report;
			} else {
				target_report.clear();
				target_report.add(type);
			}

		}
	}

	public void search(Rule rule, Boolean Q4, Boolean target_move) {
		// System.out.println(name + " searching with " + rule);

		// reset belief table and map
		current_cell = map.grid_cell[0][0];
		for (int i = 0; i < map.dim; i++) {
			for (int j = 0; j < map.dim; j++) {
				belief[i][j] = 1.0 / (map.dim * map.dim);
			}
		}
		map.resetMap();

		// init timeout counter to prevent infinite while loop
		int counter = 0;
		Cell[][] grid = map.grid_cell;

		// if target is in current cell, end search, print diagnosis.
		if (current_cell.attempt_search()) {
			// System.out.println("Found target in " + counter + " steps at " +
			// current_cell.coordinateToString());
			return;
		}

		// cell to hold next move
		Cell next_move = current_cell;

		// continuously search for cell until counter times up or until cell is found
		while (!current_cell.attempt_search() && (counter != grid.length * grid.length * grid.length)) {

			// target moves when search attempt fails
			if (target_move) {
				this.target_move_report();
			}

			// search for a list of cells with highest belief probability
			// randomly choose from list
			double max = 0;
			ArrayList<Cell> possible_moves = new ArrayList<Cell>();
			for (int i = 0; i < map.dim; i++) {
				for (int j = 0; j < map.dim; j++) {
					if (target_report != null) {
						if (target_report.contains(map.grid_cell[i][j].type)) {
							if (belief[i][j] > max) {
								possible_moves.clear();
								next_move = map.grid_cell[i][j];
								max = belief[i][j];
								possible_moves.add(next_move);
							} else if (belief[i][j] == max) {
								next_move = map.grid_cell[i][j];
								possible_moves.add(next_move);
							}
						}
					} else {
						if (belief[i][j] > max) {
							possible_moves.clear();
							next_move = map.grid_cell[i][j];
							max = belief[i][j];
							possible_moves.add(next_move);
						} else if (belief[i][j] == max) {
							next_move = map.grid_cell[i][j];
							possible_moves.add(next_move);
						}
					}
				}
			}

			int random_index = (int) (Math.random() * (possible_moves.size() - 1));
			next_move = possible_moves.get(random_index);

			update_state(rule, target_move, next_move);

			if (Q4) {
				// while agent is not at temporary target
				while (!current_cell.equals(next_move)) {

					previous_cell = current_cell;
					// move to neighbour with best manhattan, tie breaks with belief
					current_cell = move(next_move);
					map.object_move(Map.Object.AGENT, previous_cell, current_cell);
					counter++;
					// search the current cell
					if (target_move) {
						if (target_report.contains(current_cell.type)) {
							if (current_cell.attempt_search()) {
								//System.out.println(name + " found target in " + counter + " steps at "
									//	+ current_cell.coordinateToString());
								return;
							} else {
								this.target_move_report();
								if (counter == grid.length * grid.length * grid.length) {
									//System.out.println(name + " Counter timed out.");
									return;
								}
								// update all cells to new belief
								update_state(rule, Q4, next_move);
								if (!target_report.contains(next_move.type)) {
									break;
								}
							}
						}
					} else {
						if (current_cell.attempt_search()) {
							//System.out.println(name + " found target in " + counter + " steps at "
								//	+ current_cell.coordinateToString());
							return;
						} else {
							if (counter == grid.length * grid.length * grid.length) {
								// System.out.println(name + " Counter timed out.");
								return;
							}
							// update all cells to new belief
							update_state(rule, Q4, next_move);
						}
					}
				}
			} else {
				map.object_move(Map.Object.AGENT, current_cell, next_move);
				previous_cell = current_cell;
				current_cell = next_move;
			}
			counter++;
		}

		if (counter == grid.length * grid.length * grid.length) {
	//		System.out.println(name + " Counter timed out.");
		} else {
		//	System.out.println(name + " found target in " + counter + " steps at " + current_cell.coordinateToString());
		}
	}

	public int manhattan(int a, int b, int x, int y) {
		return Math.abs(a - x) + Math.abs(b - y);
	}

	/*
	 * This function is to update the belief state system if we didn't fine the
	 * target in current cell
	 */
	private void update_state(Rule rule, Boolean target_move, Cell q4_cell) {
		// get false negative and norm
		double falseNegP = current_cell.getFalseNegP();
		double norm = 1 - belief[current_cell.getyCoor()][current_cell.getxCoor()]
				+ belief[current_cell.getyCoor()][current_cell.getxCoor()] * falseNegP;

		// get target_move numerator and denominator
		double numerator = 0;

		if (target_move) {
			for (int i = 0; i < 4; i++) {
				if (target_report != null) {
					if (target_report.contains(i)) {
						// flat or cave
						if (i == 0 || i == 3) {
							numerator += (Math.pow(map.dim, 2) * 0.2);
						} else {
							// forest or hill
							numerator += (Math.pow(map.dim, 2) * 0.3);
						}
					}
				}
			}
		}

		// update the belief states
		for (int i = 0; i < map.dim; i++) {
			for (int j = 0; j < map.dim; j++) {

				if (i == current_cell.getyCoor() && j == current_cell.getxCoor()) {
					// get the new belief state for current cell
					belief[i][j] = falseNegP * belief[current_cell.getyCoor()][current_cell.getxCoor()] / norm;
				} else {
					// get the new belief state for rest cell
					belief[i][j] = belief[i][j] / norm;

				}

				// if the current rule being used is rule 2, multiply by false negative
				if (rule == Rule.RULE_2) {
					belief[i][j] *= (1 - map.grid_cell[i][j].getFalseNegP());
				}

				// if using for target is moving
				if (target_move) {
					if (target_report != null) {
						if (target_report.contains(map.grid_cell[i][j].type)) {
							if (belief[i][j] == 0) {
								belief[i][j] = move_belief[i][j];
							}
							belief[i][j] *= numerator / 2500;
						} else {
							if (move_belief[i][j] != 0) {
								move_belief[i][j] = belief[i][j];
							}
							belief[i][j] = 0;
						}
					}
				}
			}
		}
		/*
		 * DecimalFormat df = new DecimalFormat("###.########"); for(int i = 0 ; i <
		 * map.dim ; i ++) { for(int j = 0 ; j < map.dim ; j ++) {
		 * System.out.print(df.format(belief[i][j]) + " "); } System.out.println(); }
		 * System.out.println();
		 */

	}

	public Cell getCurrentCell() {
		return current_cell;
	}
}
