package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame implements MouseListener{
	
	Player currentPLayer;
	boolean hasMoved;
	boolean hasSuggested;
	
	public ClueGame(String title) {
		super(title);
	}

	public static void main(String[] args) {
		JPanel mainPanel = new JPanel();  // create the panel
		ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 
		Board board;
		
		//test player for known cards
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("handTestweapon", CardType.WEAPON));
		hand.add(new Card("handTestweapon2", CardType.WEAPON));
		
		HashMap<Player, ArrayList<Card>> seenCards = new HashMap<>();
		ArrayList<Card> seen = new ArrayList<>();
		seen.add(new Card("seenTestweapon", CardType.WEAPON));
		seen.add(new Card("seenTestweapon2", CardType.WEAPON));
		seenCards.put(new ComputerPlayer("Test Player", Color.red), seen);
		
		//create singleton board panel
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.calcTargets(board.getCell(19, 23), 5);
		
		board.getPlayerList().get(0).setLocation(2, 2);
		board.getPlayerList().get(1).setLocation(2, 2);
		
		KnownCardsPanel cardsPanel = new KnownCardsPanel(hand, seenCards);
		GameControlPanel controlPanel = new GameControlPanel();
		
		// test filling in the lower panel data
		controlPanel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, Color.orange), 5);
		controlPanel.setGuess( "I have no guess!");
		controlPanel.setGuessResult( "So you have nothing?");
		
		//add components to parent panel
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(cardsPanel, BorderLayout.EAST);
		mainPanel.add(controlPanel, BorderLayout.SOUTH);
		
		//prepare frame
		frame.setSize(750, 180+650);
		frame.setContentPane(mainPanel); // put the panel in the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	//used to determine if the cell clicked was a cell
	@Override
	public void mouseReleased(MouseEvent e) {
		Point clickLocation = new Point(e.getX(), e.getY());
		for (BoardCell b : board.)
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
