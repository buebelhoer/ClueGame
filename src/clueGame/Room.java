package clueGame;

public class Room {

	String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}
	
	
	
}
