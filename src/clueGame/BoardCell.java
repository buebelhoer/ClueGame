//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.util.*;

public class BoardCell {
	
	private int row;
	private int column;
	
	
	private Set<BoardCell> adjList;  //list of adjacent cells (including teleports)
	
	
	
	private Room room;
	
	
	private boolean occupied;
	
	// These are optional, determined at setup
	private DoorDirection doorDirection; // what direction the door from this cell goes, can be null
	private boolean isLabel; // if the cell is the label of the room
	private boolean isRoomCenter; // if the cell is the center of the room
	private char secretPassage; // contains symbol of room that there is a secret passage to
	private boolean isRoom;  //indicates if cell is a part of a room rather than a walkspace, etc.
	
	//initializes variables
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		adjList = new HashSet<BoardCell>();
		
		//assume all optionals are false, are overridden in board.loadlayoutconfig if necessary
		doorDirection = DoorDirection.NONE;
		occupied = false;
		isLabel = false;
		isRoom = false;		
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

	public void setRoom(Room r) {
		this.isRoom = true;
		
		room = r;
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
