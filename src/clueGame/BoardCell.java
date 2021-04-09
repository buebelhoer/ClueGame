//Authors: Brendan Uebelhoer, Ben Morgan
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class BoardCell {
	
	//Constants for colors for painting cells
	private final static Color walkwayColor = Color.yellow;
	private final static Color targetColor = Color.cyan;
	private final static Color roomColors = Color.lightGray;
	private final static Color doorColor = Color.blue;
	private final static Color backgroundColor = Color.black;
	
	//the position of this cell in the board, declared at creation
	private int row;
	private int column;
	
	//list of adjacent cells (including teleports)
	private Set<BoardCell> adjList;
	
	// stores the type of room this cell represents. can be walkway or closet
	private Room room;
	
	//whether or not there is currently a player in this cell
	private boolean occupied;
	
	// These are optional, determined at setup
	private DoorDirection doorDirection; // what direction the door from this cell goes, can be null
	private boolean isLabel; // if the cell is the label of the room
	private boolean isRoomCenter; // if the cell is the center of the room
	private boolean isSecretPassage; //if is secret tunnel
	private char secretPassage; // contains symbol of room that there is a secret passage to
	private boolean isRoom;  //indicates if cell is a part of a room rather than a walkspace, etc.
	
	//initializes variables
	public BoardCell(int row, int column) {
		super();
		//only way to set row and col should be constructor
		this.row = row;
		this.column = column;
		
		// allocates as a Hashset, order should matter
		adjList = new HashSet<BoardCell>();
		
		//assume all optionals are false, are overridden in board.loadlayoutconfig if necessary
		doorDirection = DoorDirection.NONE;
		occupied = false;
		isLabel = false;
		isRoom = false;
		isSecretPassage = false;
	}
	
	public void draw(Graphics g, int x, int y, int width, int height) {
		
		
		if (isRoom) {
			g.setColor(roomColors);
			g.fillRect(x, y, width - 1, height - 1);
		} else if (room.getName().equals("Unused")) {
			g.setColor(backgroundColor);
			g.fillRect(x, y, width - 1, height - 1);
		}else {
			g.setColor(walkwayColor);
			g.fillRect(x, y, width - 1, height - 1);
			g.setColor(backgroundColor);
			g.drawRect(x, y, width - 1, height - 1);
		}


	}
	
	public void DrawDoor(Graphics g, int x, int y, int width, int height) {
		final int DOOR_WIDTH_FRACTION = 7;
		
		g.setColor(doorColor);
		switch (doorDirection) {
		case UP: 
			g.fillRect(x, y - height/DOOR_WIDTH_FRACTION, width, height/DOOR_WIDTH_FRACTION);
			break;
		case DOWN:
			g.fillRect(x, y + height, width, height/DOOR_WIDTH_FRACTION);
			break;
		case RIGHT:
			g.fillRect(x + width, y, width/DOOR_WIDTH_FRACTION, height);
			break;
		case LEFT: 
			g.fillRect(x - width/DOOR_WIDTH_FRACTION, y, width/DOOR_WIDTH_FRACTION, height);
			break;
		}
	}
	
	public void drawTarget(Graphics g, int x, int y, int width, int height) {
		g.setColor(targetColor);
		g.fillRect(x, y, width - 1, height - 1);
		g.setColor(backgroundColor);
		g.drawRect(x, y, width - 1, height - 1);		
	}
	
	public void drawTargetRoom(Graphics g, int x, int y, int width, int height) {
		g.setColor(targetColor);
		g.fillRect(x, y, width - 1, height - 1);		
	}
	
	/*
	 * All functions below this point should be getters/setters
	 */
	
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
		if (!(r.getName().equals("Walkway") || r.getName().equals("Unused"))) this.isRoom = true;
		room = r;
	}
	
	public Room getRoom() {
		return room;
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
		return doorDirection != DoorDirection.NONE;
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
		isSecretPassage = true;
	}

	public boolean isSecretPassage() {
		return isSecretPassage;
	}
}
