package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;

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
		
	}
	
	@Test
	public void testCardDeck() {
		
	}
	
	@Test
	public void testSolution() {
		
	}
	
	@Test
	public void testCardsDealt() {
		
	}
	
}
