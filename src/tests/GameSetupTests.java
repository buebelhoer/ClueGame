package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSetupTests {
	final static int NUM_PLAYERS = 6;
	final static int NUM_ROOMS = 9;
	final static int NUM_WEAPONS = 6;
	
	private static Board board;
	
	@BeforeEach
	public void setupTests() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayersLoaded() {
		assertEquals(NUM_PLAYERS, board.getPlayerList().size());
		assertEquals(board.getPlayerCount(), board.getPlayerList().size());
		assertFalse(board.getPlayerList().contains(null));
	}
	
	@Test
	public void testPlayerType() {
		assertTrue(board.getPlayerList().get(0) instanceof HumanPlayer);
		for (int i = 1; i < board.getPlayerCount(); i++) {
			assertTrue(board.getPlayerList().get(i) instanceof ComputerPlayer);
		}
	}
	
	@Test
	public void testCardDeck() {
		assertEquals(NUM_PLAYERS, board.getPlayerCards().size());
		assertEquals(NUM_ROOMS, board.getRoomCards().size());
		
		assertEquals(NUM_WEAPONS, board.getWeaponCards().size());
		assertTrue(board.getWeaponCards().contains(new Card("Sulfuric Acid", CardType.WEAPON)));
		assertTrue(board.getWeaponCards().contains(new Card("Laptop", CardType.WEAPON)));
		assertTrue(board.getWeaponCards().contains(new Card("Keystone", CardType.WEAPON)));
		
		assertEquals(NUM_PLAYERS+NUM_ROOMS+NUM_WEAPONS, board.getGameCards().size());
	}
	
	@Test
	public void testSolution() {
		assertEquals(board.getSolution().getPlayer().getCardType(), "Player");
		assertEquals(board.getSolution().getRoom().getCardType(), "Room");
		assertEquals(board.getSolution().getWeapon().getCardType(), "Weapon");
	}
	
	@Test
	public void testCardsDealt() {
		int cardTotalTarget = NUM_PLAYERS+NUM_PLAYERS+NUM_WEAPONS;
		int cardTotalSum = 0;
		int cardsPerPlayer = (cardTotalTarget)/NUM_PLAYERS;
		
		for (Player p : board.getPlayerList()) {
			cardTotalSum += p.getHand().size();
			assertTrue(p.getHand().size() == cardsPerPlayer || p.getHand().size() == cardsPerPlayer + 1);
		}
		
		assertEquals(cardTotalTarget, cardTotalSum);
	}
	
}
