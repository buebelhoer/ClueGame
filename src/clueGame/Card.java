package clueGame;

public class Card {

	private String cardName;
	private CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}


	public Card() {
		// This is for testing
	}


	public boolean equals(Card target) {
		return target.getCardName().equals(this.cardName);
	}


	public String getCardName() {
		return cardName;
	}


	public CardType getCardType() {
		return cardType;
	}


	@Override
	public String toString() {
		return cardName;
	}
	
	
	
	
}
