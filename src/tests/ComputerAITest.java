package tests;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; 

import clueGame.*;


public class ComputerAITest {
	
	Board board;
	
	@BeforeEach
	public void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testMovement() {
		Random random = new Random(System.currentTimeMillis());
		//makes a player to test with
		ComputerPlayer player = new ComputerPlayer("tester", Color.red, random, board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
		
		
		//tests that the cell chosen is in the possible options
		board.calcTargets(board.getCell(7, 13), 1); //generates the targets board
		Set<BoardCell> moves = new HashSet<>(); //stores the moves that SHOULD be picked from
		moves.add(board.getCell(6, 13));
		moves.add(board.getCell(8, 13));
		moves.add(board.getCell(7, 12));
		moves.add(board.getCell(7, 14));
		
		Set<BoardCell> targets = board.getTargets(); //gets the targets
		
		BoardCell chosenCell = player.selectMove(targets); //selects a target to move to
		assertTrue(moves.contains(chosenCell)); // checks if the selected target is in the possible moves
		
		// checks that if a room is possible to be moved to, it is selected
		board.calcTargets(board.getCell(7, 13), 3);//generates the targets board
		targets = board.getTargets();//gets the targets
		chosenCell = player.selectMove(targets);//selects a target to move to
		assertTrue(chosenCell == board.getCell(3, 11)); //the chosen cell should be the room center
		
		// checks that if a room is possible to be moved to, it is selected, max number of rooms
		board.calcTargets(board.getCell(7, 13), 6);//generates the targets board
		targets = board.getTargets();//gets the targets
		chosenCell = player.selectMove(targets);//selects a target to move to
		assertTrue(chosenCell == board.getCell(3, 11));//the chosen cell should be the room center
		
	}
	
	@Test
	public void TestSuggestion() {
		Random random = new Random(System.currentTimeMillis());
		//Makes a player to test with
		ComputerPlayer player = new ComputerPlayer("tester", Color.red, random, board.getRoomCards(), board.getPersonCards(), board.getWeaponCards());
		
		Map<String,Card> cardMap = board.getCardMap();
		
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(cardMap.get("Blaster"));
		hand.add(cardMap.get("Marvin"));
		hand.add(cardMap.get("Laptop"));
		hand.add(cardMap.get("Laser"));
		hand.add(cardMap.get("Brown"));
		hand.add(cardMap.get("CTLM"));
		player.setHand(hand);
		
		
		Solution playerSol = player.createSuggestion(cardMap.get("Brown"));
		assertFalse(hand.contains(playerSol.getPerson()));
		assertFalse(hand.contains(playerSol.getWeapon()));
		board.getRoomCards().contains(playerSol.getRoom());
		board.getPersonCards().contains(playerSol.getPerson());
		board.getWeaponCards().contains(playerSol.getWeapon());
		
		
		
		
	}

}
