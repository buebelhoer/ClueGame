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
}
