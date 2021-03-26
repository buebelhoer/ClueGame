package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
	Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testCheckAccusation() {
		Solution testSolution = board.getSolution();
		assertTrue(board.getSolution().equals(testSolution));
		
		testSolution = new Solution(board.getSolution().getPerson(), board.getSolution().getRoom(), new Card("Wrong Weapon", CardType.WEAPON));
		assertFalse(board.getSolution().equals(testSolution));
		
		testSolution = new Solution(board.getSolution().getPerson(), new Card("Wrong Room", CardType.ROOM), board.getSolution().getWeapon());
		assertFalse(board.getSolution().equals(testSolution));
		
		testSolution = new Solution(new Card("Wrong Player", CardType.PERSON), board.getSolution().getRoom(), board.getSolution().getWeapon());
		assertFalse(board.getSolution().equals(testSolution));
	}
	
	@Test
	public void testCheckSuggestion() {
		//intentionally unseeded to ensure consistent results with randomness, prevents tests failing with
		//a one in a million unlucky roll
		Player testPlayer = new ComputerPlayer("Test Player", Color.red, new Random());
		
		
	}
}
