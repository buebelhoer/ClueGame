package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private Board board;
	private Random rng;
	
	private GameControlPanel controlPanel; 
	private KnownCardsPanel cardsPanel;
	public ClueGame(String title) {
		super(title);
		
		rng = new Random(System.currentTimeMillis());
		
		JPanel mainPanel = new JPanel();  // create the panel
		
		//calls standard setup functions
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize(rng);
		
		//player for known cards
		ArrayList<Card> hand = board.getHumanPlayer().getHand();
		HashMap<Player, ArrayList<Card>> seenCards = board.getHumanPlayer().getRevealedCards();
		
		cardsPanel = new KnownCardsPanel(hand, seenCards);
		controlPanel = new GameControlPanel(this);
		
		setCurrentPlayer(board.getHumanPlayer());
		
		//add components to parent panel
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(cardsPanel, BorderLayout.EAST);
		mainPanel.add(controlPanel, BorderLayout.SOUTH);
		
		//prepare frame
		setSize(750, 180+650);
		setContentPane(mainPanel); // put the panel in the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		setVisible(true); // make it visible
		
		JOptionPane.showMessageDialog(this, "You are " + board.getHumanPlayer().getName() + ". \nCan you find the solution\nbefore the Computer players?");
	}

	private void setCurrentPlayer(Player p) {
		int roll = rng.nextInt(6) + 1;
		
		
	
		controlPanel.setTurn(p, roll);
		controlPanel.setGuess("I have no guess!");
		controlPanel.setGuessResult( "So you have nothing?");
		
		board.calcTargets(board.getCell(p.getRow(), p.getColumn()), roll);
		board.setCurrentPlayer(p);
		board.setHasMoved(board.getTargets().isEmpty());
		board.setHasSuggested(false);
		
		board.repaint();
	}
	
	public void nextTurn() {
		if (!board.hasMoved()) {
			JOptionPane.showMessageDialog(this, "You have not moved yet!", "Cannot move to next player", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
//		if (!board.hasSuggested()) {
//			if (board.getCell(board.getCurrentPlayer().getRow(),board.getCurrentPlayer().getColumn()).isRoom()) {
//				JOptionPane.showMessageDialog(this, "You have not made a suggestion yet!", "Cannot move to next player", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//		}
		
		setCurrentPlayer(board.getNextPlayer());
		
		if (board.getCurrentPlayer() instanceof ComputerPlayer) {
			ComputerPlayer player = ((ComputerPlayer)board.getCurrentPlayer());
			BoardCell target = player.selectMove(board.getTargets());
			
			player.setLocation(target);
			board.setHasMoved(true);
			
			
			

		}
	}

	public static void main(String[] args) {
		ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 
	}
	
	


}
