//Authors: Brendan Uebelhoer, Ben Morgan
package experiment;

import java.util.*;

public class TestBoard {
	
	private static final int NUM_ROWS = 4; // number of rows in the board
	private static final int NUM_COLS = 4; // number of coloumn in the board
	
	private TestBoardCell[][] board;//stores the board itself
	private Set<TestBoardCell> targets;//stores the movable cells
	
	public TestBoard() {
		board = new TestBoardCell[NUM_ROWS][NUM_COLS]; //inits the board
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new TestBoardCell(i,j); // fills in the cells with their cords
			}
		}
		targets = new HashSet<TestBoardCell>();
	}

	public void calcTargets( TestBoardCell startCell, int pathlength) {
		//will contain the pathing function
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell( int row, int col ) {
		return board[row][col];
	}
	
}
