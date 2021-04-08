package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	//The name of the player
	protected String name;
	
	// The color that represents this player
	protected Color color;
	
	// The current location of the player
	protected int row, column;
	
	// Cards currently held by the player
	protected ArrayList<Card> hand;
	
	//tracks seen cards
	protected Set<Card> seenCards;
	
	//tracks who revealed seen cards
	protected HashMap<Player, ArrayList<Card>> revealedCards;
	
	//stores all of the possible cards, passed in by constructor
	protected ArrayList<Card> roomCards;
	protected ArrayList<Card> personCards;
	protected ArrayList<Card> weaponCards;
	
	//Random number gen from host
	protected Random rng;
	
	public Player(String name, Color color, int row, int column, ArrayList<Card> hand) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		this.hand = hand;
	}



	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
		hand = new ArrayList<>();
	}
	
	public Player(String name, Color color, Random rng, ArrayList<Card> roomCards,  ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super();
		this.name = name;
		this.color = color;
		hand = new ArrayList<>();
		this.roomCards = roomCards;
		this.personCards = personCards;
		this.weaponCards = weaponCards;
		this.rng = rng;
	}



	//used only for testing
	public Player() {
		super();
	}

	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disproveEvidenceList = new ArrayList<>();
		for (Card c: hand) {
			if (c.equals(suggestion.getPerson()) || c.equals(suggestion.getRoom()) || c.equals(suggestion.getWeapon())) {
				disproveEvidenceList.add(c);
			}
		}
		
		if (disproveEvidenceList.isEmpty()) return null;
		return disproveEvidenceList.get(rng.nextInt(disproveEvidenceList.size()));
	}


	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void addCard(Card c) {
		hand.add(c);
	}
	
	public void draw(Graphics g, int width, int height) {
		g.setColor(color);
		g.fillOval(column*width, row*height, width, height);
	}

	/*
	 * all getters below this point are used for testing only
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	
	
	
}
