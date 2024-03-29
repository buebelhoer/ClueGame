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
		//Test brown has secret pasage
		BoardCell cell = board.getCell(2, 2);
		Set<BoardCell> adjList = cell.getAdjList();
		assertTrue(adjList.contains(board.getCell(2, 5)));
		//secret passage cell
		assertTrue(adjList.contains(board.getCell(22, 21)));
		assertEquals(2, adjList.size());
		
		//Test market
		cell = board.getCell(3, 11);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 2);
		assertTrue(adjList.contains(board.getCell(6, 11)));
		assertTrue(adjList.contains(board.getCell(6, 12)));

		//Test CK, has secret passage
		cell = board.getCell(3, 20);
		adjList = cell.getAdjList();
		assertTrue(adjList.contains(board.getCell(5, 17)));
		assertTrue(adjList.contains(board.getCell(6, 18)));
		//secret passage cell
		assertTrue(adjList.contains(board.getCell(22, 2)));
		assertEquals(3, adjList.size());
	}

	@Test
	public void testDoorwayAdjacencies()
	{
		//13, 3
		BoardCell cell = board.getCell(13, 3);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(adjList.size(), 3);
		assertTrue(adjList.contains(board.getCell(10, 3)));
		assertTrue(adjList.contains(board.getCell(13, 2)));
		assertTrue(adjList.contains(board.getCell(13, 4)));

		//15, 11
		cell = board.getCell(15, 11);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 3);
		assertTrue(adjList.contains(board.getCell(20, 11)));
		assertTrue(adjList.contains(board.getCell(15, 10)));
		assertTrue(adjList.contains(board.getCell(15, 12)));

		//6, 18
		cell = board.getCell(6, 18);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 4);
		assertTrue(adjList.contains(board.getCell(3, 20)));
		assertTrue(adjList.contains(board.getCell(6, 17)));
		assertTrue(adjList.contains(board.getCell(6, 19)));
		assertTrue(adjList.contains(board.getCell(7, 18)));
	}

	@Test
	public void testWalkwayAdjacencies() {
		//5, 0
		BoardCell cell = board.getCell(5, 0);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(1, adjList.size());
		assertTrue(adjList.contains(board.getCell(5, 1)));

		//11, 15
		cell = board.getCell(11, 15);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 3);
		assertTrue(adjList.contains(board.getCell(10, 15)));
		assertTrue(adjList.contains(board.getCell(12, 15)));
		assertTrue(adjList.contains(board.getCell(11, 16)));

		//19, 16
		cell = board.getCell(19, 16);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 4);
		assertTrue(adjList.contains(board.getCell(18, 16)));
		assertTrue(adjList.contains(board.getCell(20, 16)));
		assertTrue(adjList.contains(board.getCell(19, 15)));
		assertTrue(adjList.contains(board.getCell(19, 17)));
		
		//19, 8
		cell = board.getCell(19, 8);
		adjList = cell.getAdjList();
		assertEquals(adjList.size(), 3);
		assertTrue(adjList.contains(board.getCell(19, 7)));
		assertTrue(adjList.contains(board.getCell(18, 8)));
		assertTrue(adjList.contains(board.getCell(20, 8)));
	}
	
	@Test
	public void testNonCenterAdjacencies() {
		//0, 10
		BoardCell cell = board.getCell(0, 10);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(adjList.size(), 0);
	}
	
	@Test
	public void testEdgeRWalkwayAdjacencies() {
		BoardCell cell = board.getCell(7, 23);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(adjList.size(), 1);
		assertTrue(adjList.contains(board.getCell(7, 22)));
	}
	
	@Test
	public void testEdgeBWalkwayAdjacencies() {
		BoardCell cell = board.getCell(23, 7);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(adjList.size(), 1);
		assertTrue(adjList.contains(board.getCell(22, 7)));
	}
	
	@Test
	public void testEdgeTWalkwayAdjacencies() {
		BoardCell cell = board.getCell(0, 6);
		Set<BoardCell> adjList = cell.getAdjList();
		assertEquals(adjList.size(), 3);
		assertTrue(adjList.contains(board.getCell(0, 5)));
		assertTrue(adjList.contains(board.getCell(0, 7)));
		assertTrue(adjList.contains(board.getCell(1, 6)));
	}	
	
	/*
	 * Target Tests:
	 * each function represents testing of targets from a single cell
	 */

	@Test
	public void testTargetsWalkways1(){
		//tests a move of 1
		board.calcTargets(board.getCell(7, 8), 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(7, 7)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(8, 8)));
		assertEquals(4, targets.size());

		//tests a move of 2
		board.calcTargets(board.getCell(7, 8), 2);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(5, 8)));
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(6, 9)));
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertEquals(7, targets.size());

		//tests a move of 4
		board.calcTargets(board.getCell(7, 8), 4);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(4, 7)));
		assertTrue(targets.contains(board.getCell(5, 6)));
		assertTrue(targets.contains(board.getCell(5, 8)));
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(6, 9)));
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(7, 12)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(8, 11)));
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertTrue(targets.contains(board.getCell(10, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));
		assertEquals(15, targets.size());
	}

	//tests a cell at the bottom, somewhat isolated
	@Test
	public void testTargetsWalkways2(){
		//tests a move of 1
		board.calcTargets(board.getCell(23, 16), 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(22, 16)));
		assertEquals(1, targets.size());

		//tests a move of 2
		board.calcTargets(board.getCell(23, 16), 2);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(21, 16)));
		assertEquals(1, targets.size());

		//tests a move of 6
		board.calcTargets(board.getCell(23, 16), 6);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(18, 15)));
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(19, 16)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(20, 15)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertEquals(8, targets.size());
	}

	@Test
	public void testTargetsRoom() {
		//tests a move of 1
		board.calcTargets(board.getCell(13, 20), 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(8, 18)));
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertEquals(2, targets.size());

		//tests a move of 2
		board.calcTargets(board.getCell(13, 20), 2);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(7, 18)));
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertTrue(targets.contains(board.getCell(17, 17)));
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertEquals(4, targets.size());

		//tests a move of 4
		board.calcTargets(board.getCell(13, 20), 4);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(7, 20)));
		assertTrue(targets.contains(board.getCell(19, 19)));
		assertTrue(targets.contains(board.getCell(17, 15)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertEquals(20, targets.size());
	}

	@Test
	public void testTargetsRoomPassage() {
		//tests a move of 1
		board.calcTargets(board.getCell(22, 2), 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(20, 5)));
		//Secret passage room
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertEquals(2, targets.size());

		//tests a move of 3
		board.calcTargets(board.getCell(22, 2), 3);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(18, 5)));
		assertTrue(targets.contains(board.getCell(19, 4)));
		assertTrue(targets.contains(board.getCell(19, 6)));
		assertTrue(targets.contains(board.getCell(20, 7)));
		assertTrue(targets.contains(board.getCell(21, 6)));
		//Secret Passage room
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertEquals(6, targets.size());

	}

	@Test
	public void testTargetsDoorway() {
		//tests a move of 1
		board.calcTargets(board.getCell(19, 19), 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(18, 19)));
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(22, 21)));
		assertEquals(4, targets.size());

		//tests a move of 2
		board.calcTargets(board.getCell(19, 19), 2);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(22, 21)));
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertTrue(targets.contains(board.getCell(18, 20)));
		assertTrue(targets.contains(board.getCell(19, 21)));
		assertEquals(6, targets.size());

		//tests a move of 4
		board.calcTargets(board.getCell(19, 19), 4);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(22, 21)));
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertTrue(targets.contains(board.getCell(19, 23)));
		assertTrue(targets.contains(board.getCell(19, 15)));
		assertEquals(13, targets.size());
	}

	@Test
	public void testTargetsOccupied() {
		board.getCell(2, 17).setOccupied(true);
		board.getCell(4, 16).setOccupied(true);

		//tests move of 2
		board.calcTargets(board.getCell(1, 16), 2);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 17)));
		assertTrue(targets.contains(board.getCell(3, 16)));
		assertEquals(2, targets.size());

		//tests move of 5
		board.calcTargets(board.getCell(1, 16), 5);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(5, 17)));
		assertEquals(1, targets.size());
		
	}

	//this should never produce a valid move
	@Test
	public void testTargetsNoValidMoves() {
		//sets only cell that can be move to to occupied
		board.getCell(19, 22).setOccupied(true);
		//move of 1
		board.calcTargets(board.getCell(22, 23), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(0, targets.size());

		//move of 6
		board.calcTargets(board.getCell(22, 23), 6);
		targets = board.getTargets();
		assertEquals(0, targets.size());
	}
}
