package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	//board used to testing
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		//calls standard setup functions
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	@Test
	public void testRoomAdjacencies()
	{
		//Test brown
		BoardCell cell = board.getCell(2, 2);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(adjList.size(), 1);
		assertTrue(adjList.contains(board.getCell(3, 5)));
		
		//Test market
		cell = board.getCell(3, 11);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 2);
		assertTrue(adjList.contains(board.getCell(6, 11)));
		assertTrue(adjList.contains(board.getCell(6, 12)));
		
		//Test CK
		cell = board.getCell(3, 20);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 2);
		assertTrue(adjList.contains(board.getCell(5, 17)));
		assertTrue(adjList.contains(board.getCell(6, 18)));
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
		//TODO test targets with an inconvenient occupied cell
	}
	
	@Test
	public void testTargetsNoValidMoves() {
		//TODO tests targets where there are no valid moves
	}
}
