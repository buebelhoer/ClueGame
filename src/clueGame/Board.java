//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.util.*;

import experiment.TestBoardCell;

public class Board {
	
	private static final int NUM_ROWS = 25; // number of rows in the board
	private static final int NUM_COLS = 24; // number of coloumn in the board
	private Set<BoardCell> visited;
	
	private BoardCell[][] board;//stores the board itself
	private Set<BoardCell> targets;//stores the movable cells
	
	private String layoutConfigFile, setupConfigFile;
	
	private static Board instance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return instance;
	}
	
	//initialize board
	public void initialize() {
		board = new BoardCell[NUM_ROWS][NUM_COLS]; //inits the board
		visited = new HashSet<BoardCell>();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new BoardCell(i,j); // fills in the cells with their cords
			}
		}
		
		generateAdjacecies();
		targets = new HashSet<BoardCell>();
	}

	private void generateAdjacecies() {     //adds neighboring cells to list if they are valid
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

	public void calcTargets( BoardCell startCell, int pathlength) { //fills targets with all possible moves
		if (startCell.isOccupied()) { //if cell is occupied, cannot move to it
			return;
		}
		
		if (visited.contains(startCell)) { // if cell is already visited, cannot move to it
			return;
		}
		
		if(pathlength == 0) { // no moves left, add current cell to targets
			targets.add(startCell);	
		} else if (startCell.isRoom()) { // if given cell is a room, all moves used up, and added to targets
			targets.add(startCell);
		} else {
			visited.add(startCell); // add cell to visited
			
			for (BoardCell c: startCell.getAdjList()) {
				calcTargets(c, pathlength - 1);
			}

			visited.remove(startCell); // removes cell from visited after all paths forward have been explore
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public BoardCell getCell( int row, int col ) {
		return board[row][col];
	}
	
	public void setConfigFiles(String layoutPath, String setupPath) {
		layoutConfigFile = layoutPath;
		setupPath = setupConfigFile;
	}
	
	public Room getRoom(BoardCell cell) {
		return null;
	}
	
	public Room getRoom(char c) {
		return null;
	}
	
	public void loadConfigFiles() {
		
	}
	
	public void loadSetupConfig() {
		
	}

	public void loadLayoutConfig() {
	
	}
	
	public int getNumRows() {
		return NUM_ROWS;
	}

	public int getNumColumns() {
		return NUM_COLS;
	}
	
	
}
