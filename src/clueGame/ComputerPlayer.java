package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	Random random;
	
	Set<Card> seenCards;
	
	public ComputerPlayer(String name, Color color, Random random, ArrayList<Card> roomCards,  ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super(name, color);
		this.random = random;
		
	}
	
	public Solution createSuggestion(Card room) {
		Card personCard;
		Card weaponCard;
		
		
		do {
			personCard = personCards.get(random.nextInt(Integer.MAX_VALUE)%personCards.size());
		} while (!seenCards.contains(personCard));
		
		do {
			weaponCard = weaponCards.get(random.nextInt(Integer.MAX_VALUE)%weaponCards.size());
		} while (!seenCards.contains(weaponCard));
		
		
		
		return new Solution();
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

	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}
	
	

}
