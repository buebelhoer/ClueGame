//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.util.*;

public class BoardCell {
	private int row;
	private int column;
	private boolean isRoom;  //indicates if cell is a part of a room rather than a walkspace, etc.
	
	private Set<BoardCell> adjList;  //list of adjacent cells (including teleports)
	
	private boolean occupied;
	private DoorDirection doorDirection;
	private Room room;
	private boolean isLabel;
	private boolean isRoomCenter;
	private char secretPassage;
	
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
	
	public boolean isDoorway() {
		return false;
	}
	
	public Room getRoom() {
		return room;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public boolean isLabel() {
		return isLabel;
	}

	public void setLabel(boolean isLabel) {
		this.isLabel = isLabel;
	}

	public boolean isRoomCenter() {
		return isRoomCenter;
	}

	public void setRoomCenter(boolean isRoomCenter) {
		this.isRoomCenter = isRoomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
}
