package clueGame;

public class Room {

	//Name of the room
	//set in constructor
	String name;
	
	//cell at the middle of the room where the player should be shown
	//set manually from setter function
	BoardCell centerCell;
	
	//Cell where the name of the room should be displayed
	//Set manually from setter function
	BoardCell labelCell;
	
	public Room(String name) {
		super();
		this.name = name;
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
	
}
