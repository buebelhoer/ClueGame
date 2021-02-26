package experiment;

import java.util.*;

public class TestBoard {
	
	private static final int NUM_ROWS = 4;
	private static final int NUM_COLS = 4;
	
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets;
	
	public TestBoard() {
		board = new TestBoardCell[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new TestBoardCell(i,j);
			}
		}
		targets = new HashSet<TestBoardCell>();
	}

	public void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell( int row, int col ) {
		return board[row][col];
	}
	
}
