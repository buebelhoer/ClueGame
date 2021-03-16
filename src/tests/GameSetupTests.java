package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameSetupTests {

	@BeforeEach
	public void setupTests(){
		
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
