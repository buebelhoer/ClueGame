package GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class ClueGame extends JFrame {
	public ClueGame(String title) {
		super(title);
	}

	public static void main(String[] args) {
		JPanel mainPanel = new JPanel();  // create the panel
		ClueGame frame = new ClueGame("Mines Clue");  // create the frame 
		
		KnownCardsPanel cardsPanel = new KnownCardsPanel(null, null);
		GameControlPanel 
		
		mainPanel.add(, FlowLayout.RIGHT);
		mainPanel.add(new GameControlPanel(), FlowLayout.LEFT);
		
		frame.setContentPane(mainPanel); // put the panel in the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
