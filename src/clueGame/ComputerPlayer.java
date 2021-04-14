package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {	

	Queue<Room> lastRooms;


	public ComputerPlayer(String name, Color color, Random random, ArrayList<Card> roomCards,  ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super(name, color, random, roomCards,personCards,weaponCards);
		seenCards = new HashSet<>();
		lastRooms = new LinkedList<Room>();
	}

	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, color, row, col, null);
		lastRooms = new LinkedList<Room>();
	}

	public ComputerPlayer(String name, Color color) {
		this.name = name;
		this.color =color;
		lastRooms = new LinkedList<Room>();
	}

	public Solution createSuggestion(Card roomCard) {
		Card personCard;
		Card weaponCard;


		do {
			personCard = personCards.get(rng.nextInt(Integer.MAX_VALUE)%personCards.size());
		} while (seenCards.contains(personCard) || hand.contains(personCard));

		do {
			weaponCard = weaponCards.get(rng.nextInt(Integer.MAX_VALUE)%weaponCards.size());
		} while (seenCards.contains(weaponCard)|| hand.contains(weaponCard));



		return new Solution(personCard,roomCard,weaponCard);
	}

	//selects the location of the next move. randomly selects, unless a room center is possible
	public BoardCell selectMove(Set<BoardCell> targets) {
		//cannot move if no targets
		if (targets.isEmpty()) return null;

		//checks for room center
		for (BoardCell cell :targets) {
			if(cell.isRoomCenter()) {
				//if the cell is a room that has been visited recently, it is not given priority over other cells to avoid looping
				if (!lastRooms.contains(cell.getRoom())) {
					//adds cell to the recently visited list
					lastRooms.add(cell.getRoom());
					
					//only removes from the list when the size is large enough
					if (lastRooms.size() > 2) {
						lastRooms.remove();
					}
					return cell;
				}
			}
		}

		//I really hate this code, but its the best way to do this
		//Randomly grabs an element of the targets and returns it
		return (BoardCell)targets.toArray()[rng.nextInt(Integer.MAX_VALUE)%targets.size()];

	}

	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}



}
