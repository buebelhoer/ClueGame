package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;

public class BoardAdjTargetTest {
	//board used to testing
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		//calls stardard setup functions
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	@Test
	public void testRoomAdjacencies()
	{
		//TODO fill in function, test room centers
	}
	
	@Test
	public void testDoorwayAdjacencies()
	{
		//TODO fill in function, tests doorways
	}
	
	@Test
	public void testWalkwayAdjacencies() {
		//TODO fill in function, tests walkways
	}
	
	/*
	 * Target Tests:
	 * each function represents testing of targets from a single cell
	 */
	
	@Test
	public void testTargetsWalkways1(){
		//TODO test targets from a random walkway
	}
	
	@Test
	public void testTargetsWalkways2(){
		//TODO test targets from a random walkway
	}
	
	@Test
	public void testTargetsRoom() {
		//TODO test targets from inside a room
	}
	
	@Test
	public void testTargetsRoomPassage() {
		//TODO test targets inside a room with a secret passage
	}
	
	@Test
	public void testTargetsDoorway() {
		//TODO tests targets inside a doorway
	}
	
	@Test
	public void testTargetsOccuied() {
		//TODO test targets with an inconvinient occupied cell
	}
	
	@Test
	public void testTargetsNoValidMoves() {
		//TODO tests targets where there are no valid moves
	}
}
