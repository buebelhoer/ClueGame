//Authors: Brendan Uebelhoer, Ben Morgan
package experiment;

import java.util.*;

public class TestBoard {
	
	private static final int NUM_ROWS = 4; // number of rows in the board
	private static final int NUM_COLS = 4; // number of coloumn in the board
	private Set<TestBoardCell> visited;
	
	private TestBoardCell[][] board;//stores the board itself
	private Set<TestBoardCell> targets;//stores the movable cells
	
	public TestBoard() {
		board = new TestBoardCell[NUM_ROWS][NUM_COLS]; //inits the board
		visited = new HashSet<TestBoardCell>();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new TestBoardCell(i,j); // fills in the cells with their cords
			}
		}
		
		generateAdjacecies();
		targets = new HashSet<TestBoardCell>();
	}

	private void generateAdjacecies() {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				if (i > 0) {
					board[i][j].addAdjacency(board[i-1][j]);
				}
				
				if (i < NUM_ROWS - 1) {
					board[i][j].addAdjacency(board[i+1][j]);
				}
				
				if (j > 0) {
					board[i][j].addAdjacency(board[i][j-1]);
				}
				
				if (j < NUM_COLS - 1) {
					board[i][j].addAdjacency(board[i][j+1]);
				}
			}
		}
	}

	public void calcTargets( TestBoardCell startCell, int pathlength) { //fills targets with all possible moves
		System.out.println(startCell.getRow() + " " + startCell.getColumn() + " " + startCell.isRoom());
		
		if (startCell.isOccupied()) { //if cell is occupied, cannot move to it
			return;
		}
		
		if (visited.contains(startCell)) { // if cell is already visited, cannot move to it
			return;
		}
		
		if(pathlength == 0) { // no moves left, add current cell to targets
			targets.add(startCell);
			System.out.println("yay " +startCell.getRow() + " " + startCell.getColumn() + " " + startCell.isRoom());
			
		} else if (startCell.isRoom()) { // if given cell is a room, all moves used up, and added to targets
			targets.add(startCell);
			System.out.println("yay " +startCell.getRow() + " " + startCell.getColumn() + " " + startCell.isRoom());
		} else {
			visited.add(startCell); // add cell to visited
			
			for (TestBoardCell c: startCell.getAdjList()) {
				calcTargets(c, pathlength - 1);
			}

			visited.remove(startCell); // removes cell from visited after all paths forward have been explore
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell( int row, int col ) {
		return board[row][col];
	}
	
}
