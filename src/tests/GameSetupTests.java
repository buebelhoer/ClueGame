package tests;

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
