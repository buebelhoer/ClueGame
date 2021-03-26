package clueGame;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution(Card player, Card room, Card weapon) {
		super();
		this.person = player;
		this.room = room;
		this.weapon = weapon;
	}

	public Solution() {
		// This is for testing only
		super();
		this.person = new Card();
		this.room = new Card();
		this.weapon = new Card();
		
	}



	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
	
	public boolean equals(Solution s) {
		return s.getPerson().equals(person) && s.getRoom().equals(room) && s.getWeapon().equals(weapon);
	}
}
