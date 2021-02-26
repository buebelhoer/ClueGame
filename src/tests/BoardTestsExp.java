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
	public void testCalcsTL() {
		board.calcTargets(board.getCell(0, 0), 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertEquals(3, targets.size());
	}
	
	@Test
	public void testCalcsRB() {
		board.calcTargets(board.getCell(3, 3), 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertEquals(3, targets.size());
	}
	
	@Test
	public void testCalcsMid() {
		board.calcTargets(board.getCell(1, 1), 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertEquals(3, targets.size());
	}
}
