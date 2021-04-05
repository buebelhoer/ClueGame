package GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.ComputerPlayer;

public class GameControlPanel extends JPanel {

	String guessString = "TEST GUESS STRING";
	String guessResultString = "TEST GUESS STRING";
	
	JTextField nameField;
	JTextField rollField;
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		JPanel turnPanel = new JPanel(new GridLayout(1, 4));
		
		JPanel playerPanel = new JPanel(new GridLayout(2, 1));
		JLabel whoseLabel = new JLabel("Whose turn?");
		nameField = new JTextField();
		nameField.setEditable(false);
		
		playerPanel.add(whoseLabel);
		playerPanel.add(nameField);
		
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		rollField = new JTextField();
		rollField.setEditable(false);
		
		rollPanel.add(rollLabel);
		rollPanel.add(rollField);
		
		JButton accuseButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");
		
		turnPanel.add(playerPanel);
		turnPanel.add(rollPanel);
		turnPanel.add(accuseButton);
		turnPanel.add(nextButton);
	
		//sets up the jpanel that holds the bottom half of the control panel
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,2));
		
		//sets up the left side of the bottom of the control panel
		JPanel guess = new JPanel();
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		JTextField guessField = new JTextField(guessString);
		guess.add(guessField);
		
		//sets up the right side of the bottom half of the control panel
		JPanel guessResult = new JPanel();
		guessResult.setBorder(BorderFactory.createTitledBorder("Guess Result"));
		JTextField guessResultField = new JTextField(guessResultString);
		guessResult.add(guessResultField);
		
		//adds the left and right to the main frame
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
	
	private void setTurn(ComputerPlayer cp, int roll) {
		nameField.setText(cp.getName());
		nameField.setBackground(cp.getColor());
		
		rollField.setText(((Integer)roll).toString());
	}
}