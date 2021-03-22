package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSetupTests {

	private static Board board;
	
	@BeforeEach
	public void setupTests() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayersLoaded() {
		assertEquals(6, board.getPlayerList().size());
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
		
	}
	
	@Test
	public void testSolution() {
		
	}
	
	@Test
	public void testCardsDealt() {
		
	}
	
}
