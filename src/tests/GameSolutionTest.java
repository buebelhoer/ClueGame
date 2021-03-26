package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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
		Player testPlayer = new ComputerPlayer("Test Player", Color.red, new Random(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		board.setPlayerCount(1);
		board.setPlayerList(new ArrayList<>(Arrays.asList(testPlayer)));
		Solution testSolution;
		
		//Cards that do not match the solution
		Card personTestCard = new Card("Test Player", CardType.PERSON);
		Card roomTestCard = new Card("Test Room", CardType.ROOM);
		Card weaponTestCard = new Card("Test Weapon", CardType.WEAPON);
		
		//Cards that make up the solution
		Card playerTestSolutionCard = new Card("Test Player", CardType.PERSON);
		Card roomTestSolutionCard = new Card("Test Room", CardType.ROOM);
		Card weaponTestSolutionCard = new Card("Test Weapon", CardType.WEAPON);

		//Hand that has no cards that match solution
		ArrayList<Card> noDisproveHand = new ArrayList<Card>(Arrays.asList(personTestCard, roomTestCard, weaponTestCard));
		
		//Hands that have one card that matches the solution
		ArrayList<Card> personDisproveHand = new ArrayList<Card>(Arrays.asList(playerTestSolutionCard, roomTestCard, weaponTestCard));
		ArrayList<Card> roomDisproveHand = new ArrayList<Card>(Arrays.asList(personTestCard, roomTestSolutionCard, weaponTestCard));
		ArrayList<Card> weaponDisproveHand = new ArrayList<Card>(Arrays.asList(personTestCard, roomTestCard, weaponTestSolutionCard));
		
		//Hand that has two cards that match solution
		ArrayList<Card> twoDisproveHand = new ArrayList<Card>(Arrays.asList(playerTestSolutionCard, roomTestSolutionCard, weaponTestCard));
	
		//Testing no available disprove
		testPlayer.setHand(noDisproveHand);
		testSolution = new Solution(playerTestSolutionCard, roomTestSolutionCard, weaponTestSolutionCard);
		assertEquals(null, board.checkSuggestion(testSolution));
		
		//Testing person disproves
		testPlayer.setHand(personDisproveHand);
		testSolution = new Solution(personTestCard, roomTestSolutionCard, weaponTestSolutionCard);
		assertEquals(personTestCard, board.checkSuggestion(testSolution));
		
		//Testing room disproves
		testPlayer.setHand(roomDisproveHand);
		testSolution = new Solution(playerTestSolutionCard, roomTestCard, weaponTestSolutionCard);
		assertEquals(roomTestCard, board.checkSuggestion(testSolution));
		
		//Testing weapon disproves
		testPlayer.setHand(weaponDisproveHand);
		testSolution = new Solution(playerTestSolutionCard, roomTestSolutionCard, weaponTestCard);
		assertEquals(weaponTestCard, board.checkSuggestion(testSolution));
		
		//Testing random of two cards disproves
		final int NUM_RUNS = 50;
		int personCount = 0, roomCount = 0, otherCount = 0;
		
		for (int i = 0; i < NUM_RUNS; i++) {
			testPlayer.setHand(twoDisproveHand);
			testSolution = new Solution(personTestCard, roomTestCard, weaponTestSolutionCard);
			if (board.checkSuggestion(testSolution).equals(personTestCard)) {
				personCount++;
			} else if (board.checkSuggestion(testSolution).equals(roomTestCard)) {
				roomCount++;
			} else {
				otherCount++;
			}
		}
		
		//Assures semi randomness
		assertTrue(personCount > 15);
		assertTrue(roomCount > 15);
		//Assures only people and rooms were returned
		assertTrue(otherCount == 0);
	}
	
	@Test
	public void testSuggestionHandling() {
		
	}
}
