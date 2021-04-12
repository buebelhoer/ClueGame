package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {

	//Name of the room
	//set in constructor
	private String name;
	
	//cell at the middle of the room where the player should be shown
	//set manually from setter function
	private BoardCell centerCell;
	
	//Cell where the name of the room should be displayed
	//Set manually from setter function
	private BoardCell labelCell;
	
	//tracks the cells corresponding with the doorway cells and the secret passage cells
	private Set<BoardCell> exits;
	
	public Room(String name) {
		super();
		this.name = name;
		exits = new HashSet<BoardCell>();
	}
	
	public String getName() {
		return name;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	public void addExit(BoardCell exit) {
		exits.add(exit);
	}

	public Set<BoardCell> getExits() {
		return exits;
	}
			
}
