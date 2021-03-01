//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.util.*;

public class BoardCell {
	private int row;
	private int column;
	private boolean isRoom;  //indicates if cell is a part of a room rather than a walkspace, etc.
	
	private Set<BoardCell> adjList;  //list of adjacent cells (including teleports)
	
	private boolean occupied;
	
	//initializes variables
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		adjList = new HashSet<BoardCell>();
	}
	
	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public boolean isRoom() {
		return isRoom;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	
}
