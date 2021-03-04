package clueGame;

public class Room {

	String name;
	BoardCell centerCell;
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
