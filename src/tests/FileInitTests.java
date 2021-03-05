package tests;

/*
 * This program tests that config files are loaded properly.
 */

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;

	//singleton board
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// checks a few rooms, including the first and the last, as well as a walkway
		assertEquals("Elm", board.getRoom('E').getName() );
		assertEquals("Brown", board.getRoom('B').getName() );
		assertEquals("Maple", board.getRoom('P').getName() );
		assertEquals("CoorsTek", board.getRoom('C').getName() );
		assertEquals("Rec Center", board.getRoom('R').getName() );
		assertEquals("Walkway", board.getRoom('W').getName() );
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test most doors and a few non-doors
	@Test
	public void testDoors() {
		BoardCell cell = board.getCell(13, 0);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCell(6, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCell(2, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(20, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(15, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		cell = board.getCell(6, 11);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCell(6, 12);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCell(5, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCell(8, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCell(17, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCell(19, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		// Test that walkways are not doors
		cell = board.getCell(6, 0);
		assertFalse(cell.isDoorway());
	}


	//Count doors, should equal 12 (from layout sheet)
	@Test
	public void testNumberOfDoorways() {
		int count = 0;
		for (int i = 0; i < board.getNumRows(); i++)
			for (int j = 0; j < board.getNumColumns(); j++) {
				BoardCell cell = board.getCell(i, j);
				if (cell.isDoorway())
					count++;
			}
		Assert.assertEquals(14, count);
	}

	// Tests some cell to ensure they have the proper room.
	@Test
	public void testRooms() {
		
		// test a walkway
		BoardCell cell = board.getCell(5, 0);
		Room room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );

		// test a invalid cell
		cell = board.getCell(12, 12);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );

		// checks bottom row
		cell = board.getCell( 22, 23);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Elm" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// tests label cell
		cell = board.getCell(2, 20);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "CoorsTek" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );

		// tests room cell
		cell = board.getCell(20, 11);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Student Center" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );

		// test secret passage
		cell = board.getCell(0, 0);
		room = board.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Brown" ) ;
		assertTrue( cell.getSecretPassage() == 'E' );


	}

}
