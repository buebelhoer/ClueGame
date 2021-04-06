package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	public ClueGame(String title) {
		super(title);
	}

	public static void main(String[] args) {
		JPanel mainPanel = new JPanel();  // create the panel
		ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 
		Board board;
		
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("handTestweapon", CardType.WEAPON));
		hand.add(new Card("handTestweapon2", CardType.WEAPON));
		
		HashMap<Player, ArrayList<Card>> seenCards = new HashMap<>();
		ArrayList<Card> seen = new ArrayList<>();
		seen.add(new Card("seenTestweapon", CardType.WEAPON));
		seen.add(new Card("seenTestweapon2", CardType.WEAPON));
		seenCards.put(new ComputerPlayer("Test Player", Color.red), seen);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		KnownCardsPanel cardsPanel = new KnownCardsPanel(hand, seenCards);
		GameControlPanel controlPanel = new GameControlPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(cardsPanel, BorderLayout.EAST);
		mainPanel.add(controlPanel, BorderLayout.SOUTH);
		
		frame.setSize(750, 180+650);
		frame.setContentPane(mainPanel); // put the panel in the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
