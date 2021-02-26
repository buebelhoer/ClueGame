package experiment;

import java.util.*;

public class TestBoardCell {
	private int row;
	private int column;
	private boolean isRoom;
	
	private Set<TestBoardCell> adjList;
	
	private boolean occupied;
	
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		adjList = new HashSet<TestBoardCell>();
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

	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}

	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	
}
