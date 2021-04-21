package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {
	
	private JTextField nameField;
	private JTextField rollField;
	private JTextField turnField;
	private JTextField guessField;
	private JTextField guessResultField;
	private JButton nextButton;
	private JButton accuseButton;
	
	private Integer turnNumber;

	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel(ClueGame clueGame)  {
		setLayout(new GridLayout(2,1));
		//panel to hold the the turn, rolls, and turn buttons
		JPanel turnPanel = new JPanel(new GridLayout(1, 4));
		
		//panel to hold player turn info
		JPanel playerPanel = new JPanel(new GridLayout(2, 1));
		JLabel whoseLabel = new JLabel("Whose turn?", JLabel.CENTER);
		nameField = new JTextField();
		nameField.setEditable(false);
		
		playerPanel.add(whoseLabel);
		playerPanel.add(nameField);
		
		//panel to display roll result
		JPanel rollPanel = new JPanel(new GridLayout(2, 1));
		
		turnNumber = 1;
		JLabel turnLabel = new JLabel("Turn:");
		turnField = new JTextField(5);
		turnField.setText(turnNumber.toString());
		turnField.setEditable(false);
				
		JLabel rollLabel = new JLabel("Roll:");
		rollField = new JTextField(5);
		rollField.setEditable(false);
		
		rollPanel.add(turnLabel);
		rollPanel.add(turnField);
		rollPanel.add(rollLabel);
		rollPanel.add(rollField);
		
		//turn advance buttons
		accuseButton = new JButton("Make Accusation");
		nextButton = new JButton("NEXT!");
		
		//responsible for the next button logic, just calls clueGame's next turn method
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clueGame.nextTurn();				
			}
		};
		
		nextButton.addActionListener(buttonListener);
		
		//responsible for the accusation button
		ActionListener accusationListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clueGame.makeAccusation();
				
			}
		};
		accuseButton.addActionListener(accusationListener);
		
		//add everything to host element
		turnPanel.add(playerPanel);
		turnPanel.add(rollPanel);
		turnPanel.add(accuseButton);
		turnPanel.add(nextButton);
	
		//sets up the jpanel that holds the bottom half of the control panel
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,2));
		
		//sets up the left side of the bottom of the control panel
		JPanel guess = new JPanel();
		guess.setLayout(new GridLayout(1,1));
		guess.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Guess"));
		
		//sets up test field, inits to blank
		guessField = new JTextField();
		guessField.setEditable(false);
		
		//adds the text field to the panel
		guess.add(guessField);
		
		//sets up the right side of the bottom half of the control panel
		JPanel guessResult = new JPanel();
		guessResult.setLayout(new GridLayout(1,1));
		guessResult.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Guess Result"));
		
		//creates the text field, inits to blank
		guessResultField = new JTextField();
		guessResultField.setEditable(false);
		
		//adds text field
		guessResult.add(guessResultField);
		
		//adds the left and right to the main frame
		guessPanel.add(guess);
		guessPanel.add(guessResult);
		
		//add to main panel
		add(turnPanel);
		add(guessPanel);
	}
	

	
	public void setTurn(Player cp, int roll) {
		nameField.setText(cp.getName());
		nameField.setBackground(cp.getColor());
		
		rollField.setText(((Integer)roll).toString());
	}
	
	public void incrementTurnNumber() {
		turnNumber++;
		turnField.setText(turnNumber.toString());
	}
	
	public void setGuess(String guess) {
		guessField.setText(guess);
	}
	
	public void setGuessResult(String guessResult) {
		guessResultField.setText(guessResult);
	}
	
	//disables the buttons for computer simulation
	public void disableButtons() {
		nextButton.removeActionListener(nextButton.getActionListeners()[0]);
	}
	
	public int getTurnNumber() {
		return turnNumber.intValue();
	}
}