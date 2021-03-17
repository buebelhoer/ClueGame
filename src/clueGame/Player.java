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
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

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
	
	
	
	
}
