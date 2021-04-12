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

public class ClueGame extends JFrame implements MouseListener{
	private Player currentPLayer;
	private boolean hasMoved;
	private boolean hasSuggested;
	private Board board;
	private Random rng;
	
	public ClueGame(String title) {
		super(title);
		
		rng = new Random(System.currentTimeMillis());
		
		JPanel mainPanel = new JPanel();  // create the panel
		ClueGame frame = new ClueGame("Mines Mystery");  // create the frame 
		
		//calls standard setup functions
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize(rng);
		
		//player for known cards
		ArrayList<Card> hand = board.getHumanPlayer().getHand();
		HashMap<Player, ArrayList<Card>> seenCards = board.getHumanPlayer().getRevealedCards();
		
		KnownCardsPanel cardsPanel = new KnownCardsPanel(hand, seenCards);
		GameControlPanel controlPanel = new GameControlPanel();
		
		setCurrentPlayer(controlPanel, board.getHumanPlayer());
		
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

	private void setCurrentPlayer(GameControlPanel controlPanel, Player p) {
		controlPanel.setTurn(p, rng.nextInt(6) + 1);
		controlPanel.setGuess("I have no guess!");
		controlPanel.setGuessResult( "So you have nothing?");
	}

	public static void main(String[] args) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	//used to determine if the cell clicked was a cell
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (hasMoved) {
			return;
		}
		
		Point clickLocation = new Point(e.getX(), e.getY());
		BoardCell clickedCell = null;
		for(int row = 0; row < board.getNumRows(); row ++) {
			for (int col = 0; col < board.getNumColumns(); col ++) {
				if (board.getCell(row, col).containsClick(clickLocation)) {
					clickedCell = board.getCell(row, col);
				}
			}
		}
		
		if (clickedCell == null) {
			JOptionPane.showMessageDialog(this, "That is not a cell, plase click a cell to move to it", "Not A Cell", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (board.getTargets().contains(clickLocation)) {
			currentPLayer.setLocation(clickedCell);
		} else {
			JOptionPane.showMessageDialog(this, "That is not a cell you can move to!", "Invalid Move!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		hasMoved = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
