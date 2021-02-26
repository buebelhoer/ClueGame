package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.*;

import experiment.TestBoard;
import experiment.TestBoardCell;
import org.junit.Assert.*;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjecency() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(testList.size(), 2);
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(testList.size(), 2);
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertEquals(testList.size(), 3);
		cell = board.getCell(3, 0);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(testList.size(), 2);
		cell = board.getCell(1, 1);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(testList.size(), 4);
	}
	
	@Test
	public void testCalcsTL() { // starts the player on the top left
		board.calcTargets(board.getCell(0, 0), 2); // 2 movement
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertEquals(3, targets.size()); //should produce 3 moves
	}
	
	@Test
	public void testCalcsRB() { // starts the player on the bottom right
		board.calcTargets(board.getCell(3, 3), 2);// 2 movement
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertEquals(3, targets.size()); //should produce 3 moves
	}
	
	@Test
	public void testCalcsMid() { // starts the player in the middle
		board.calcTargets(board.getCell(1, 1), 3); // starts in the middle, 3 movement
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertEquals(8, targets.size()); //should produce 8 moves
	}
	
	@Test
	public void testCalcsDoor() {
		board.getCell(0, 1).setRoom(true); // sets a door directly right of the starting point
		board.calcTargets(board.getCell(1, 1), 2); //starts top left, 2 movement
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 1))); // should stop at the door
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertEquals(3, targets.size()); //should produce 3 moves
	}
	
	@Test
	public void testCalcOccupied() {
		board.getCell(1, 0).setOccupied(true);//sets the spot directly below the player to occupied
		board.calcTargets(board.getCell(1, 1), 2); //starts top left, 2 movement
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertEquals(2, targets.size()); //should produce 2 moves
	}
	
	@Test
	public void testCalcDoorOccupiedMid() { // biggest test, with a door to the
		board.getCell(3, 1).setOccupied(true);//sets the spot 2 below the player to occupied
		board.getCell(0, 3).setRoom(true); //sets the top right spot to be a door
		board.calcTargets(board.getCell(1, 1), 4); //starts top left, with 4 movement
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(0, 3))); //should stop at the door
		assertEquals(7, targets.size()); //should produce 7 moves
	}
}

