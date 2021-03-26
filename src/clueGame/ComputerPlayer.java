package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	Random random;

	public ComputerPlayer(String name, Color color, Random random) {
		super(name, color);
		this.random = random;
	}
	
	public Solution generateSolution(Card room) {
		//TODO complete function
		return null;
	}
	
	//selects the location of the next move. randomly selects, unless a room center is possible
	public BoardCell selectMove(Set<BoardCell> targets) {
		//cannot move if no targets
		if (targets.isEmpty()) return null;
		
		//checks for room center
		for (BoardCell cell :targets) {
			if(cell.isRoomCenter()) {
				return cell;
			}
		}
		
		//I really hate this code, but its the best way to do this
		//Randomly grabs an element of the targets and returns it
		return (BoardCell)targets.toArray()[random.nextInt(Integer.MAX_VALUE)%targets.size()];
		
	}
	

}
