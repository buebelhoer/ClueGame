package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	
	
	
	//The name of the player
	private String name;
	
	// The color that represents this player
	private Color color;
	
	// The current location of the player
	private int row, column;
	
	// Cards currently held by the player
	private ArrayList<Card> hand;
	
	//stores all of the possible cards, passed in by constructor
	ArrayList<Card> roomCards;
	ArrayList<Card> personCards;
	ArrayList<Card> weaponCards;
	
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
	
	public Player(String name, Color color, ArrayList<Card> roomCards,  ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super();
		this.name = name;
		this.color = color;
		hand = new ArrayList<>();
		this.roomCards = roomCards;
		this.personCards = personCards;
		this.weaponCards = weaponCards;
	}



	//used only for testing
	public Player() {
		super();
	}

	
	public Card disproveSuggestion(Solution suggestion) {
		//TODO complete function
		return null;
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
