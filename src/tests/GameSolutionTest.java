package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
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
	public void testDisproveSuggestion() {
		//intentionally unseeded to ensure consistent results with randomness, prevents tests failing with
		//a one in a million unlucky roll
		Player testPlayer = new ComputerPlayer("Test Player", Color.red, new Random(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		Solution testSolution;
		
		//Cards that do not match the solution
		Card personTestCard = new Card("Test Player", CardType.PERSON);
		Card roomTestCard = new Card("Test Room", CardType.ROOM);
		Card weaponTestCard = new Card("Test Weapon", CardType.WEAPON);
		
		//Cards that make up the solution
		Card playerTestSolutionCard = new Card("Solution Player", CardType.PERSON);
		Card roomTestSolutionCard = new Card("Solution Room", CardType.ROOM);
		Card weaponTestSolutionCard = new Card("Solution Weapon", CardType.WEAPON);

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
		assertEquals(null, testPlayer.disproveSuggestion(testSolution));
		
		//Testing person disproves
		testPlayer.setHand(personDisproveHand);
		testSolution = new Solution(personTestCard, roomTestSolutionCard, weaponTestSolutionCard);
		assertEquals(personTestCard, testPlayer.disproveSuggestion(testSolution));
		
		//Testing room disproves
		testPlayer.setHand(roomDisproveHand);
		testSolution = new Solution(playerTestSolutionCard, roomTestCard, weaponTestSolutionCard);
		assertEquals(roomTestCard, testPlayer.disproveSuggestion(testSolution));
		
		//Testing weapon disproves
		testPlayer.setHand(weaponDisproveHand);
		testSolution = new Solution(playerTestSolutionCard, roomTestSolutionCard, weaponTestCard);
		assertEquals(weaponTestCard, testPlayer.disproveSuggestion(testSolution));
		
		//Testing random of two cards disproves
		final int NUM_RUNS = 50;
		int personCount = 0, roomCount = 0, otherCount = 0;
		
		for (int i = 0; i < NUM_RUNS; i++) {
			testPlayer.setHand(twoDisproveHand);
			testSolution = new Solution(personTestCard, roomTestCard, weaponTestSolutionCard);
			if (testPlayer.disproveSuggestion(testSolution).equals(personTestCard)) {
				personCount++;
			} else if (testPlayer.disproveSuggestion(testSolution).equals(roomTestCard)) {
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
		//creates three players and overrides Board's playerlist to be just these three
		ComputerPlayer player1 = new ComputerPlayer("test1",Color.red, new Random(System.currentTimeMillis()), board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
		ComputerPlayer player2 = new ComputerPlayer("test2",Color.red, new Random(System.currentTimeMillis()), board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
		ComputerPlayer player3 = new ComputerPlayer("test3",Color.red, new Random(System.currentTimeMillis()), board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		board.setPlayerList(players);
		board.setPlayerCount(3);
		
		//gets the map of cards
		Map cardMap = board.getCardMap();
		
		//adds a single card to each player
		player1.updateHand((Card)cardMap.get("Blaster"));
		player2.updateHand((Card)cardMap.get("Marvin"));
		player3.updateHand((Card)cardMap.get("PCJ"));
		
		//creates a solution designed to force player 1 to show their card
		Card checkedCard = board.checkSuggestion(new Solution((Card)cardMap.get("Blaster"), new Card(), new Card()));
		assertTrue(checkedCard == (Card)cardMap.get("Blaster"));
		
		//creates a solution designed to force player 2 to show their card
		checkedCard = board.checkSuggestion(new Solution((Card)cardMap.get("Marvin"), new Card(), new Card()));
		assertTrue(checkedCard == (Card)cardMap.get("Marvin"));
		
		//creates a solution designed to force player 3 to show their card
		checkedCard = board.checkSuggestion(new Solution((Card)cardMap.get("PCJ"), new Card(), new Card()));
		assertTrue(checkedCard == (Card)cardMap.get("PCJ"));
		
		//creates a solution where no one can disprove
		checkedCard = board.checkSuggestion(new Solution((Card)cardMap.get("Paone"), new Card(), new Card()));
		assertTrue(checkedCard == null);
		
		//creates a situation where two players both have cards that can disprove
		player2.updateHand((Card)cardMap.get("Laptop"));
		
		//tracks how many times each card was returned
		int personcount= 0;
		int weaponcount = 0;
		
		//checks 50 solutions to test randomly returning one of the two cards
		for (int i = 0; i < 50; i++) {
			checkedCard = board.checkSuggestion(new Solution((Card)cardMap.get("Blaster"), new Card(),(Card)cardMap.get("Laptop")));
			//determines which card was returned
			if (checkedCard == (Card)cardMap.get("Blaster")) personcount++;
			if (checkedCard == (Card)cardMap.get("Laptop")) weaponcount++;
		}
		
		//checks that both cards were returned at least once
		assertTrue(personcount > 1);
		assertTrue(weaponcount > 1);
		
		
	}
}
