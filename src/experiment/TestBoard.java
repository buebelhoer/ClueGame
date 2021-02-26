package experiment;

import java.util.*;

public class TestBoard {
	
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets;
	
	public TestBoard() {
		board = new TestBoardCell[4][4];
		for (TestBoardCell[] e:board) {
			for (TestBoardCell a:e) {
				a = new TestBoardCell();
			}
		}
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
