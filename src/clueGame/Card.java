package clueGame;

public class Card {

	private String cardName;
	private CardType cardType;
	
	public boolean equals(Card target) {
		return target.getCardName().equals(this.cardName);
	}


	public String getCardName() {
		return cardName;
	}


	public CardType getCardType() {
		return cardType;
	}
	
	
}
