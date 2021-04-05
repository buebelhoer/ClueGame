package GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.ComputerPlayer;
import sun.jvm.hotspot.runtime.aarch64.AARCH64CurrentFrameGuess;

public class GameControlPanel extends JPanel {

	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		
	
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,2));
		
		JPanel guess = new JPanel();
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		JTextField guessField = new JTextField();
		guess.add(guessField);
		
		JPanel guessResult = new JPanel();
		guessResult.setBorder(BorderFactory.createTitledBorder("Guess Result"));
		JTextField guessResultField = new JTextField();
		guessResult.add(guessResultField);
		
		guessPanel.add(guess);
		guessPanel.add(guessResult);
		
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, Color.orange), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}