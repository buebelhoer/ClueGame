package clueGame;

public class Solution {
	private Card player;
	private Card room;
	private Card weapon;
	
	public Solution(Card player, Card room, Card weapon) {
		super();
		this.player = player;
		this.room = room;
		this.weapon = weapon;
	}

	public Card getPlayer() {
		return player;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
}
