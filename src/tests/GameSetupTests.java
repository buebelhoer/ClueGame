package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;

public class GameSetupTests {

	@BeforeEach
	public void setupTests(){
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testPlayersLoaded() {
		assertEquals(6, board.getPlayerList().length());
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
